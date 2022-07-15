package com.dagurasu.microservices.camelmicroservicea.routes.patterns;

import java.util.ArrayList;
import java.util.List;

import org.apache.camel.AggregationStrategy;
import org.apache.camel.Exchange;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.model.dataformat.JsonLibrary;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class EipPatternsRouter extends RouteBuilder {

	@Autowired
	private SplitterComponent splitterComponent;

	@Override
	public void configure() throws Exception {

		// from("timer:multicast?period=10000")
		// .multicast()
		// .to("log:something1", "log:something2", "log:something3");

		// from("file:files/csv")
		// .unmarshal().csv()
		// .split(body())
		// .to("activemq:split-queue");

		// from("file:files/csv")
		// .convertBodyTo(String.class)
		// .split(body(), ",")
		// .split(method(splitterComponent))
		// .to("activemq:split-queue");

		from("file:files/aggregate-json").unmarshal().json(JsonLibrary.Jackson, CurrencyExchange.class)
				.aggregate(simple("${body.to}"), new ArrayListAggregationStrategy()).completionSize(3)
				// .completionTimeout(HIGHEST)
				.to("log:aggregate-json");
	}
}

@Component
class SplitterComponent {
	public List<String> splitInput(String body) {
		return List.of("ABC", "DEF", "GHI");
	}
}

class ArrayListAggregationStrategy implements AggregationStrategy {

	@Override
	public Exchange aggregate(Exchange oldExchange, Exchange newExchange) {
		 Object newBody = newExchange.getIn().getBody();
	        ArrayList<Object> list = null;
	        if (oldExchange == null) {
	            list = new ArrayList<Object>();
	            list.add(newBody);
	            newExchange.getIn().setBody(list);
	            return newExchange;
	        } else {
	            list = oldExchange.getIn().getBody(ArrayList.class);
	            list.add(newBody);
	            return oldExchange;
	        }
		}
	}