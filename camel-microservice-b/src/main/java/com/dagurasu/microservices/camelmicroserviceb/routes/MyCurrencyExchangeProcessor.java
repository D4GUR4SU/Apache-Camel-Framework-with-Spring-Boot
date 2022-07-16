package com.dagurasu.microservices.camelmicroserviceb.routes;

import java.math.BigDecimal;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.dagurasu.microservices.camelmicroserviceb.CurrencyExchange;

//@Component
public class MyCurrencyExchangeProcessor {

	private Logger logger = LoggerFactory.getLogger(MyCurrencyExchangeProcessor.class);

	public void processMessage(CurrencyExchange currencyExchange) {
		logger.info("Do some processing with currencyExchange.getConversionMultiple() value which is {}",
				currencyExchange.getConversionMultiple());
	}
}

@Component
class MyCurrencyExchangeTransformer {

	public CurrencyExchange processMessage(CurrencyExchange currencyExchange) {
		currencyExchange.setConversionMultiple(				
				currencyExchange.getConversionMultiple().multiply(BigDecimal.TEN));
		
		return currencyExchange;
	}
}