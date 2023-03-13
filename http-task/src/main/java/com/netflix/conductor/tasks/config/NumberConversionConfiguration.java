/*
 * Copyright 2023 Netflix, Inc.
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except in compliance with
 * the License. You may obtain a copy of the License at
 * <p>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p>
 * Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on
 * an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the
 * specific language governing permissions and limitations under the License.
 */
package com.netflix.conductor.tasks.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.oxm.jaxb.Jaxb2Marshaller;

import com.netflix.conductor.tasks.client.NumberConversionClient;

@Configuration
public class NumberConversionConfiguration {

	@Bean
	public Jaxb2Marshaller marshaller() {
		Jaxb2Marshaller marshaller = new Jaxb2Marshaller();

		// Context path must match gradle value
		marshaller.setContextPath("com.NumberConversion.wsdl");
		return marshaller;
	}

	@Bean
	public NumberConversionClient numberConversionClient(Jaxb2Marshaller marshaller) {
		
		NumberConversionClient client = new NumberConversionClient();	
		
		//https://www.dataaccess.com/webservicesserver/NumberConversion.wso
		
		client.setDefaultUri("https://www.dataaccess.com/webservicesserver/NumberConversion");
		client.setMarshaller(marshaller);
		client.setUnmarshaller(marshaller);
		
		return client;
	}

}
