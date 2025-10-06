package com.railconnect.Controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CardController {

	@GetMapping("/demo1")
	public String Demo() {
		System.out.println("Hey");
		return "/Demo";
	}
}
