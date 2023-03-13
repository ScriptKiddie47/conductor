package com.netflix.conductor.tasks.client;

import java.math.BigInteger;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.oxm.jaxb.Jaxb2Marshaller;
import org.springframework.ws.client.core.support.WebServiceGatewaySupport;
import org.springframework.xml.transform.StringResult;
import org.springframework.xml.transform.StringSource;

import com.NumberConversion.wsdl.NumberToWords;
import com.NumberConversion.wsdl.NumberToWordsResponse;

public class NumberConversionClient extends WebServiceGatewaySupport{
	private static final Logger LOGGER = LoggerFactory.getLogger(NumberConversionClient.class);
	
	@Autowired
	private Jaxb2Marshaller jaxb2Marshaller;
	
	public NumberToWordsResponse getNumberToWords(BigInteger numericValue) {
		
		StringResult result = new StringResult();
		
		
		final String endpoint = "https://www.dataaccess.com/webservicesserver/NumberConversion.wso";
		
		
		NumberToWords request = new NumberToWords();
		request.setUbiNum(numericValue);
		
		jaxb2Marshaller.marshal(request, result);
		LOGGER.info("Request XML : {} " , result.toString());
		
		LOGGER.info("Requesting the NumberToWordsResponse for : " + numericValue);
		
		NumberToWordsResponse response = (NumberToWordsResponse) getWebServiceTemplate()
				.marshalSendAndReceive(endpoint,request);
	
		result = new StringResult();
		jaxb2Marshaller.marshal(response, result);
		LOGGER.info("Response XML : " + result.toString());
		
		
		return response;
	}
	
	public String getNumberToWordsString(Object object) {
		
		
		// TODO : This looks stupid TBH , need to update for better performance
		StringResult result = new StringResult();
			
		final String endpoint = "https://www.dataaccess.com/webservicesserver/NumberConversion.wso";
		NumberToWords request = (NumberToWords) jaxb2Marshaller.unmarshal(new StringSource(returnXMLwithoutSoapTags(object.toString())));
		
		result = new StringResult();
		jaxb2Marshaller.marshal(request, result);
		
		LOGGER.info("Request XML : {} " , result.toString());		
		
		NumberToWordsResponse response = (NumberToWordsResponse) getWebServiceTemplate()
				.marshalSendAndReceive(endpoint,request);
		result = new StringResult();
		jaxb2Marshaller.marshal(response, result);
		
		LOGGER.info("Response XML : " + result.toString());
		
		return result.toString();
	}

	public String returnXMLwithoutSoapTags(String xmlData) {
		
		// TODO : There out to be something better 
		
		int start = xmlData.indexOf("<NumberToWords");
		int end = xmlData.indexOf("</NumberToWords>");
		String finalXml = xmlData.substring(start, end).concat("</NumberToWords>");
		return finalXml;
	}
	
}
