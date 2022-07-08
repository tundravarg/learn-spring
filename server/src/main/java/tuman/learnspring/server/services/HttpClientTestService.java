package tuman.learnspring.server.services;


import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URLEncoder;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Arrays;
import java.util.Collections;
import java.util.Map;
import java.util.function.Predicate;

@Service
public class HttpClientTestService {

    public void callPing() {
        System.out.println("------------------- Call PING...");
        try {
            HttpRequest request = HttpRequest.newBuilder()
//                    .uri(new URI("http://localhost:8080/api/ping"))
                    .uri(buildUrl("http", "localhost", "8080", "/api/ping", Map.of("i", 7)))
                    .GET()
                    .header("Authorization", "basic uname:upwd")
                    .build();
            HttpClient client = HttpClient.newBuilder().build();
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            System.out.println("------------------- Call PING: " + response.statusCode() + ": " + response.body());
        } catch (URISyntaxException | IOException | InterruptedException ex) {
            System.err.println("Can't execute request: " + ex.getClass() + ": " + ex.getMessage());
        }
    }


    private static URI buildUrl(
            String protocol, String address, String port, String path,
            Map<String, Object> urlParams) throws URISyntaxException {
        return new URI(buildUrlString(protocol, address, port, path, urlParams));
    }
    private static String buildUrlString(
            String protocol, String address, String port, String path,
            Map<String, Object> urlParams) {
        StringBuilder sb = new StringBuilder();
        sb.append(protocol).append("://");
        sb.append(address);
        if (port != null) {
            sb.append(":").append(port);
        }
        sb.append(urlEncodePath(path));
        if (urlParams != null && !urlParams.isEmpty()) {
            String paramsStr = urlParams.entrySet().stream()
                    .map(param -> urlEncode(param.getKey()) + "=" + urlEncode(param.getValue()))
                    .reduce(null, (result, param) -> result != null ? result + "&" + param : param);
            sb.append("?").append(paramsStr);
        }
        System.out.println("URL: " + sb.toString());
        return sb.toString();
    }

    private static String urlEncodePath(String path) {
        StringBuilder sb = new StringBuilder();
        Arrays.stream(path.split("/"))
                .filter(Predicate.not(String::isBlank))
                .map(HttpClientTestService::urlEncode)
                .forEach(pathElement -> sb.append("/").append(pathElement));
        return sb.toString();
    }

    private static String urlEncode(Object str) {
        if (str == null) {
            return "";
        }
        try {
            return URLEncoder.encode(str.toString(), "utf-8");
        } catch (UnsupportedEncodingException ex) {
            throw new IllegalStateException(ex);
        }
    }

}
