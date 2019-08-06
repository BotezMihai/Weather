package com.example.demo.commonfunctions;

import com.google.gson.JsonObject;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

public class SharedVariables {
    private RestTemplate restTemplate;
    private ResponseEntity<String> response;
    private String body;
    private JsonObject jsonObject;
    private String responseCode;
    private static int count = 0;

    public SharedVariables(RestTemplate restTemplate, String url) {
        JsonOperations jsonOperations = new JsonOperations();
        this.restTemplate = restTemplate;
        this.response = restTemplate.getForEntity(url, String.class);
        this.body = response.getBody();
        this.jsonObject = jsonOperations.getJsonObject(body);
        this.responseCode = jsonObject.get("cod").getAsString();
    }

    public SharedVariables() {

    }

    public RestTemplate getRestTemplate() {
        return restTemplate;
    }

    public ResponseEntity<String> getResponse() {
        return response;
    }

    public String getBody() {
        return body;
    }

    public JsonObject getJsonObject() {
        return jsonObject;
    }

    public String getResponseCode() {
        return responseCode;
    }

    public static int getCount() {
        return count;
    }

    public static void setCount(int count) {
        SharedVariables.count = count;
    }
}
