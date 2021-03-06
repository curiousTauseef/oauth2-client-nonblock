/*
 * Copyright 2002-2019 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package sample.web;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.Map;

import static org.springframework.security.oauth2.client.web.reactive.function.client.ServletOAuth2AuthorizedClientExchangeFilterFunction.clientRegistrationId;

/**
 * @author Joe Grandja
 */
@Controller
public class DefaultController {
	private WebClient webClient;

	@Value("${service-a.uri}")
	private String serviceUrl;

	public DefaultController(WebClient webClient) {
		this.webClient = webClient;
	}

	@GetMapping("/")
	public String index() {
		return "index";
	}

	@GetMapping("/client-a")
	public String clientA(Map<String, Object> model) {
		String[] response = this.webClient
				.get()
				.uri(this.serviceUrl)
				.attributes(clientRegistrationId("client-a"))
				.retrieve()
				.bodyToMono(String[].class)
				.block();

		model.put("response", "Client A response: " + StringUtils.arrayToDelimitedString(response, ", "));

		return "index";
	}

	@GetMapping("/client-b")
	public String clientB(Map<String, Object> model) {
		String[] response = this.webClient
				.get()
				.uri(this.serviceUrl)
				.attributes(clientRegistrationId("client-b"))
				.retrieve()
				.bodyToMono(String[].class)
				.block();

		model.put("response", "Client B response: " + StringUtils.arrayToDelimitedString(response, ", "));

		return "index";
	}
}