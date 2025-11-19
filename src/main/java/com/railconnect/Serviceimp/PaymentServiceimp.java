package com.railconnect.Serviceimp;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

//import java.net.http.HttpHeaders;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
//import org.springframework.http.HttpMethod;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.railconnect.entities.Booking;
import com.railconnect.entities.Payment;
import com.railconnect.entities.PaymentStatus;
import com.railconnect.repo.BookingRepo;
import com.railconnect.repo.PaymentRepo;
import com.railconnect.service.PaymentService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class PaymentServiceimp implements PaymentService {

	@Autowired
	private final BookingRepo bookingRepo;

	@Autowired
	private final RestTemplate restTemplate;
	

	private final ObjectMapper mapper = new ObjectMapper();

	@Autowired
	private PaymentRepo paymentRepo;

	@Value("${payment.base.url}")
	private String baseUrl;

	@Value("${payment.client.id}")
	private String clientId;

	@Value("${payment.client.secret}")
	private String clientSecret;

//	 Step 1: Generate Access Token
	private String getAccessToken() throws Exception {
//		String auth = Base64.getEncoder().encodeToString((clientId + ":" + clientSecret).getBytes());

		HttpHeaders headers = new HttpHeaders();
		headers.setBasicAuth(clientId, clientSecret);
		headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
		HttpEntity<String> entity = new HttpEntity<>("grant_type=client_credentials", headers);

		ResponseEntity<String> response = restTemplate.exchange(baseUrl + "/v1/oauth2/token", HttpMethod.POST, entity,
				String.class);

		JsonNode node = mapper.readTree(response.getBody());
		return node.get("access_token").asText();
	}

	// Step 2: Create PayPal Order
	@Override
	public JsonNode createOrder(Long bookingId) throws Exception {
		Booking booking = bookingRepo.findById(bookingId)
				.orElseThrow(() -> new RuntimeException("Booking not found"));

		String token = getAccessToken();

		Map<String, Object> orderRequest = new HashMap<>();
		orderRequest.put("intent", "CAPTURE");
		orderRequest.put("purchase_units",
				List.of(Map.of("amount", Map.of("currency_code", "USD", "value", booking.getTotalFare().toString()))));

		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		headers.setBearerAuth(token);

		HttpEntity<Map<String, Object>> entity = new HttpEntity<>(orderRequest, headers);

		ResponseEntity<String> response = restTemplate.exchange(baseUrl + "/v2/checkout/orders", HttpMethod.POST,
				entity, String.class);

		JsonNode json = mapper.readTree(response.getBody());
		Payment payment = new Payment();
		payment.setPaypalOrderId(json.get("id").asText());
		payment.setBooking(booking);
		payment.setTotalamount(booking.getTotalFare());
		payment.setPaymentMethod("UPI");
		payment.setStatus(PaymentStatus.PENDING);
		paymentRepo.save(payment);
		

		return json;
	}

	@Override
	public JsonNode captureOrder(String orderId) throws Exception {
             String token = getAccessToken();
             
             HttpHeaders headers = new HttpHeaders();
             headers.setBearerAuth(token);
             headers.setContentType(MediaType.APPLICATION_JSON);
             
             HttpEntity<String> entity = new HttpEntity<>("", headers);
             
             ResponseEntity<String> response = restTemplate.exchange(baseUrl + "/v2/checkout/orders/" + orderId + "/capture", HttpMethod.POST, entity, String.class);
		
         	JsonNode json = mapper.readTree(response.getBody());

    		if (json.get("status").asText().equals("COMPLETED")) {
    			Payment payment = paymentRepo.findByPaypalOrderId(orderId)
    					.orElseThrow(() -> new RuntimeException("Booking not found for orderId: " + orderId));

    			payment.setStatus(PaymentStatus.SUCCESS);
    			paymentRepo.save(payment);
    		}

    		return json;
	}


}
