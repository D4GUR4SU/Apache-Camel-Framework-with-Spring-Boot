package com.dagurasu.microservices.camelmicroserviceb.routes;

import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.model.dataformat.JsonLibrary;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.dagurasu.microservices.camelmicroserviceb.CurrencyExchange;

@Component
public class ActiveMQReceiverRouter extends RouteBuilder{
	
	@Autowired
	private MyCurrencyExchangeProcessor myCurrencyExchangeProcessor;
	
	@Autowired
	private MyCurrencyExchangeTransformer myCurrencyExchangeTransformer;
	
	@Override
	public void configure() throws Exception {
		
		/*
		 * from("activemq:my-activemq-queue") .unmarshal() .json(JsonLibrary.Jackson,
		 * CurrencyExchange.class) .bean(myCurrencyExchangeProcessor)
		 * .bean(myCurrencyExchangeTransformer)
		 * .to("log:received-message-from-active-mq");
		 */
		
		from("activemq:my-activemq-xml-queue")
			.unmarshal()
			.jacksonXml(CurrencyExchange.class)
			//.bean(myCurrencyExchangeProcessor)
			//.bean(myCurrencyExchangeTransformer)
			.to("log:received-message-from-active-mq");
		
	}
}