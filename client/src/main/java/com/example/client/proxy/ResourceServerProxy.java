package com.example.client.proxy;

import com.example.client.managers.TokenManager;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class ResourceServerProxy {

  public static final String AUTHORIZATION = "Authorization";

  @Value("${resource.server.url}")
  private String resourceServerURL;

  private final TokenManager tokenManager;

  private final RestTemplate restTemplate;

  public ResourceServerProxy(TokenManager tokenManager, RestTemplate restTemplate) {
    this.tokenManager = tokenManager;
    this.restTemplate = restTemplate;
  }

  public String callDemo() {
    String token = tokenManager.getAccessToken();

    String url = resourceServerURL + "/demo";

    HttpHeaders headers = new HttpHeaders();
    headers.add(AUTHORIZATION, "Bearer " + token);
    HttpEntity<Void> request = new HttpEntity<>(headers);

    var response = restTemplate.exchange(url, HttpMethod.GET, request, String.class);

    return response.getBody();
  }
}
