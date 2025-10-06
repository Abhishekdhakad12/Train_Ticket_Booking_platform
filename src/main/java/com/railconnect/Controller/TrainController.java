package com.railconnect.Controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.railconnect.DTO.TrainSearchResultDto;
import com.railconnect.Serviceimp.TrainSearchServiceimp;
import com.railconnect.Serviceimp.TrainSeriviceimp;
import com.railconnect.entities.Train;
import com.railconnect.exception.AuthHelper;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/trains")
public class TrainController {

	@Autowired
	private TrainSeriviceimp trainSeriviceimp;

	@Autowired
	private AuthHelper authHelper;

	@Autowired
	private TrainSearchServiceimp trainSearchServiceimp;

	@PostMapping("/add")
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<?> addNewTrain(@Valid @RequestBody Train train, HttpServletRequest httpServletRequest) {
		String userEmail = authHelper.getUserEmail(httpServletRequest);
		Train savedTrain = this.trainSeriviceimp.addNewtrain(train, userEmail);
		return new ResponseEntity<>(savedTrain, HttpStatus.CREATED);
	}

	@GetMapping("/getAlltrains")
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<List<Train>> getAllTrains() {
		List<Train> allTrain = this.trainSeriviceimp.getAllTrains();
		return ResponseEntity.ok(allTrain);
	}

	@GetMapping("/getTrain")
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<Train> getTrainByIdORTrainNumber(@RequestParam(required = false) Long id,
			@RequestParam(required = false) String trainNumber) {

		if (id == null && trainNumber == null) {
			throw new IllegalArgumentException("Either id or trainNumber must be provided");
		}

		if (id != null && trainNumber != null) {
			throw new IllegalArgumentException("Provide only one: either id OR trainNumber, not both");
		}

		Train train = null;
		if (id != null) {
			train = this.trainSeriviceimp.gettrainByIdORTrainNumber(id, trainNumber);

		} else {

			train = this.trainSeriviceimp.gettrainByIdORTrainNumber(id, trainNumber);
		}
		return ResponseEntity.ok(train);
	}

	@DeleteMapping("/deleteTrain")
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<Map<String, Object>> TrainDeleteByIdORTrainNumber(@RequestParam(required = false) Long id,
			@RequestParam(required = false) String trainNumber) {

		if (id == null && trainNumber == null) {
			throw new IllegalArgumentException("Either id or trainNumber must be provided");
		}

		if (id != null && trainNumber != null) {
			throw new IllegalArgumentException("Provide only one: either id OR trainNumber, not both");
		}

		Train train = null;
		if (id != null) {
			train = this.trainSeriviceimp.gettrainByIdORTrainNumber(id, trainNumber);

		} else {

			train = this.trainSeriviceimp.gettrainByIdORTrainNumber(id, trainNumber);
		}

		Map<String, Object> response = new HashMap<>();
		response.put("status", "success");
		response.put("Deleted Train :- ", train);
		response.put("message", "Train deleted successfully");

		return ResponseEntity.ok(response);

	}

	@PatchMapping("/update")
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<Map<String, Object>> updateTrain(@RequestBody Train updates,
			@RequestParam(required = false) Long id, 
			@RequestParam(required = false) String trainNumber, HttpServletRequest httpRequest) {

		if (id == null && trainNumber == null) {
			throw new IllegalArgumentException("Either id or trainNumber must be provided");
		}

		if (id != null && trainNumber != null) {
			throw new IllegalArgumentException("Provide only one: either id OR trainNumber, not both");
		}

		Train train = null;
		if (id != null) {
			train = this.trainSeriviceimp.gettrainByIdORTrainNumber(id, trainNumber);

		} else {

			train = this.trainSeriviceimp.gettrainByIdORTrainNumber(id, trainNumber);
		}

		String updatedByEmail = authHelper.getUserEmail(httpRequest);
		train = this.trainSeriviceimp.updateTrain(updates, id, updatedByEmail, trainNumber);

		Map<String, Object> response = new HashMap<>();
		response.put("status", "success");
		response.put("updatedTrain :- ", train);
		response.put("message", "Train updated successfully");

		return ResponseEntity.ok(response);

	}

	@GetMapping("/search")
	@PreAuthorize("hasAnyRole('ADMIN', 'USER')")
	public ResponseEntity<List<TrainSearchResultDto>> trainSearchResult(@RequestParam String from,
			@RequestParam String to) {

		List<TrainSearchResultDto> trainSearchResultDto = this.trainSearchServiceimp.trainSearchResultDto(from, to);
		return ResponseEntity.ok(trainSearchResultDto);

	}
}
