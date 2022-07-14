package com.dagurasu.microservices.camelmicroserviceb.route;

import org.apache.camel.builder.RouteBuilder;

public class ActiveMQReceiverRouter extends RouteBuilder{

	@Override
	public void configure() throws Exception {
		
		from("activemq:my-activemq-queue")
		.to("log:received-message-from-active-mq");
	}
}
