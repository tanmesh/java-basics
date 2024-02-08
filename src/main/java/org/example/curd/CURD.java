package org.example.curd;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class CURD {
    public void get(String url) throws Exception {
        //Create an instance of HttpClient
        HttpClient httpClient = HttpClient.newHttpClient();

        // Create an HttpRequest object with the GET method
        HttpRequest request = HttpRequest
                .newBuilder()
                .uri(URI.create(url))
                .GET()
                .build();

        try {
            HttpResponse<String> response = httpClient
                    .send(request, HttpResponse.BodyHandlers.ofString());

            System.out.println("Response Status Code: " + response.statusCode());
            System.out.println("Response Body: " + response.body());
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void post(String url, String requestBody) throws Exception {
        // Create an instance of HttpClient
        HttpClient httpClient = HttpClient.newHttpClient();

        // Create an HttpRequest object with the POST method
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .header("Content-Type", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString(requestBody))
                .build();

        try {
            HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());

            System.out.println("Response Status Code: " + response.statusCode());
            System.out.println("Response Body: " + response.body());
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void del(String url) throws Exception {
        // Create an instance of HttpClient
        HttpClient httpClient = HttpClient.newHttpClient();

        // Create an HttpRequest object with the POST method
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .header("Content-Type", "application/json")
                .DELETE()
                .build();

        try {
            HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());

            System.out.println("Response Status Code: " + response.statusCode());
            System.out.println("Response Body: " + response.body());
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) throws Exception {
        CURD curd = new CURD();
        String url = "http://localhost:4000/data";

        curd.get(url);

        String requestBody;

        requestBody = "{\"message\" : \"Hello Tanmesh!\"}";
        curd.post(url, requestBody);

        requestBody = "{\"message\" : \"Hello World!\"}";
        curd.post(url, requestBody);

        curd.get(url);
    }
}
