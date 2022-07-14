package com.dagurasu.microservices.camelmicroservicea.routes.a;

import java.time.LocalDateTime;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.apache.camel.builder.RouteBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

//@Component
public class myFirstTimerRouter extends RouteBuilder {

	@Autowired
	private GetCurrentTimeBean getCurrentTimeBean;
	
	@Autowired
	private SimpleLoggingProccessingComponent logging;

	@Override
	public void configure() throws Exception {
		
		from("timer:first-timer")
			.log("${body}")
			.transform()
			.constant("My Message Constant")
			.log("${body}")
			.bean(getCurrentTimeBean)
			.log("${body}")
			.bean(logging)
			.log("${body}")
			.process(new SimpleLoggingProccessor())
			.log("${body}")
			.to("log:first-timer");

	}
}

@Component
class GetCurrentTimeBean {
	public String getCurrentTime() {
		return "Time now is: " + LocalDateTime.now();
	}
}

@Component
class SimpleLoggingProccessingComponent {

	private Logger logger = LoggerFactory.getLogger(SimpleLoggingProccessingComponent.class);

	public void process(String message) {
		logger.info("SimpleLoggingProccessingComponent {}", message);

	}
}

@Component
class SimpleLoggingProccessor implements Processor {

	private Logger logger = LoggerFactory.getLogger(SimpleLoggingProccessingComponent.class);

	@Override
	public void process(Exchange exchange) throws Exception {
		logger.info("SimpleLoggingProccessingComponent {}", exchange.getMessage().getBody());
		
	}



}
