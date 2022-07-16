package com.dagurasu.microservices.camelmicroserviceb.routes;

import java.io.IOException;
import java.security.Key;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.UnrecoverableKeyException;
import java.security.cert.CertificateException;

import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.converter.crypto.CryptoDataFormat;
import org.apache.camel.model.dataformat.JsonLibrary;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.dagurasu.microservices.camelmicroserviceb.CurrencyExchange;

@Component
public class ActiveMQReceiverRouter extends RouteBuilder {

	//@Autowired
	//private MyCurrencyExchangeProcessor myCurrencyExchangeProcessor;

	//@Autowired
	//private MyCurrencyExchangeTransformer myCurrencyExchangeTransformer;

	@Override
	public void configure() throws Exception {

		from("activemq:my-activemq-queue")
			.unmarshal(createEncryptor())
				// .unmarshal()
				// .json(JsonLibrary.Jackson, CurrencyExchange.class)
				// .bean(myCurrencyExchangeProcessor)
				// .bean(myCurrencyExchangeTransformer)
				.to("log:received-message-from-active-mq");

		// from("activemq:my-activemq-xml-queue")
		// .unmarshal()
		// .jacksonXml(CurrencyExchange.class)
		// .bean(myCurrencyExchangeProcessor)
		// .bean(myCurrencyExchangeTransformer)
		// .to("log:received-message-from-active-mq");

		/*
		 * from("activemq:split-queue") .to("log:received-message-from-active-mq");
		 */

	}

	private CryptoDataFormat createEncryptor() throws KeyStoreException, IOException, NoSuchAlgorithmException,
			CertificateException, UnrecoverableKeyException {
		KeyStore keyStore = KeyStore.getInstance("JCEKS");
		ClassLoader classLoader = getClass().getClassLoader();
		keyStore.load(classLoader.getResourceAsStream("myDesKey.jceks"), "someKeystorePassword".toCharArray());
		Key sharedKey = keyStore.getKey("myDesKey", "someKeyPassword".toCharArray());

		CryptoDataFormat sharedKeyCrypto = new CryptoDataFormat("DES", sharedKey);
		return sharedKeyCrypto;

	}
}