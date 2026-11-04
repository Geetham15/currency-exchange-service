package com.microservices.currency_exchange_service;

import java.math.BigDecimal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CurrencyExchangeController {

	@Autowired
	private CurrencyExchangeRepository repository;
	@Autowired
	private Environment environment;
	
	@GetMapping("/currency-exchange/from/{from}/to/{to}")
	public CurrencyExchange retrieveExchangeValue(
			@PathVariable  String from,
			@PathVariable String to) {
		CurrencyExchange currencyExchange = repository.findByFromAndTo(from, to);
		if(currencyExchange == null) {
			throw new RuntimeException
				("Unable to Find data for " + from + " to " + to);
		}
		//CurrencyExchange currencyExchange = new CurrencyExchange(1000L, from, to, BigDecimal.valueOf(50));
		String port = environment.getProperty("local.server.port");
		currencyExchange.setEnvironment(port);
		return currencyExchange;
	}
	
	//To run two instances-> go to Run Configurations Name as CurrencyExchangeServiceApplication8000
	//Go to JavaApplication-> Right click on application and Duplicate. Name it as CurrencyExchangeServiceApplication8001
	// Go to Argument tab -> Under VM Arguments section-> -Dserver.port=8001 and Run.
	// the second instances will run with the port number 8001	
}
