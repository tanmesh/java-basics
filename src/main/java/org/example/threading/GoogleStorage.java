package org.example.threading;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/*
https://storage.googleapis.com/server-success-rate/hosts/host{0..999}/status

{
    "requests_count": 66278,
    "application": "WebApp1",
    "version": "2.0",
    "success_count": 66278,
    "error_count": 0
}

Calculate the overall success rate for each application-<version>

Example output:
  WebApp1-1.0: 94.3%
  WebApp1-2.0: 99.1%

#Calculate the overall success rate for each application-<version>

success_rate = (success_count/requests_count)*100

api = https://storage.googleapis.com/server-success-rate/hosts/host{0..999}/status
api = https://storage.googleapis.com/server-success-rate/hosts/host0/status
api = https://storage.googleapis.com/server-success-rate/hosts/host999/status

                                       success_count   requests_count
host0 - WebApp1-1.0          a                        d
host3 - WebApp1-1.0          b                        e
host5 - WebApp1-1.0          c                         f

success_rate = ((a+b+c) / (d+e+f)) * 100
 */
public class GoogleStorage {
    private static final int NUM_THREADS = 10;

    public static Map<String, Integer[]> map = new ConcurrentHashMap<>(); // Use ConcurrentHashMap

    public static void main(String[] args) {
        ExecutorService executor = Executors.newFixedThreadPool(NUM_THREADS);

        for (int i = 0; i < 1000; ++i) {
            final int index = i;
            executor.execute(() -> getStatusOf(index));
        }
        executor.shutdown();

        try {
            executor.awaitTermination(Long.MAX_VALUE, TimeUnit.NANOSECONDS);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        for (Map.Entry<String, Integer[]> entry : map.entrySet()) {
            int success_rate = entry.getValue()[0] * 100 / entry.getValue()[1];
            System.out.println(entry.getKey() + " " + success_rate + "%");
        }
    }

    private static void getStatusOf(int i) {
        String url = String.format("https://storage.googleapis.com/server-success-rate/hosts/host%d/status", i);

        HttpClient httpClient = HttpClient.newHttpClient();

        HttpRequest request = HttpRequest
                .newBuilder()
                .uri(URI.create(url))
                .header("Content-Type", "application/json")
                .GET()
                .build();

        try {
            HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());

            ObjectMapper mapper = new ObjectMapper();
            JsonNode rootNode = mapper.readTree(response.body());

            System.out.println(rootNode);
            String key = rootNode.get("application").toString() + rootNode.get("version").toString();
            String[] keyTmp = key.split("\"");
            key = keyTmp[1] + "-" + keyTmp[3];

            int success_count = rootNode.get("success_count").asInt();
            int requests_count = rootNode.get("requests_count").asInt();

            Integer[] tmp = map.get(key);
            if (tmp == null) {
                tmp = new Integer[2];
                tmp[0] = 0;
                tmp[1] = 0;
            }
            tmp[0] += success_count;
            tmp[1] += requests_count;

            map.put(key, tmp);
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }
}
