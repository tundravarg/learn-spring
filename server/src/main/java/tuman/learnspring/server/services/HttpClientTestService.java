package tuman.learnspring.server.services;


import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.net.ssl.*;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.*;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.security.*;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.Arrays;
import java.util.Base64;
import java.util.Map;
import java.util.function.Predicate;

@Service
public class HttpClientTestService {

    @Value("${app.https.cacerts.url}")
    private String cacertsUrl;
    @Value("${app.https.cacerts.password}")
    private String cacertsPassword;


    public void callPing() {
        System.out.println("------------------- Call PING...");
        try {
            System.getProperties().setProperty("jdk.internal.httpclient.disableHostnameVerification", Boolean.TRUE.toString());
            HttpRequest request = HttpRequest.newBuilder()
//                    .uri(new URI("http://localhost:8080/api/ping"))
                    .uri(buildUrl("https", "localhost", "8080", "/api/ping", Map.of("i", 7)))
                    .GET()
//                    .header("Authorization", "Basic " + Base64.getEncoder().encodeToString("test:testpwd".getBytes()))
                    .build();
            HttpClient client = HttpClient.newBuilder()
                    .authenticator(new Authenticator() {
                        @Override
                        protected PasswordAuthentication getPasswordAuthentication() {
                            return new PasswordAuthentication("user", "userpwd".toCharArray());
                        }
                    })
//                    .sslContext(getDummySslContext())
                    .sslContext(getSslContext(cacertsUrl, cacertsPassword))
                    .build();
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            System.out.println("------------------- Call PING: " + response.statusCode() + ": " + response.body());
        } catch (URISyntaxException | IOException | InterruptedException ex) {
            System.err.println("Can't execute request: " + ex.getClass() + ": " + ex.getMessage());
            ex.printStackTrace();
        }
    }


    /**
     * Return dummy SSLContext, which allows all certificates
     * @return Dummy SSLContext
     */
    private static SSLContext getDummySslContext() {
        TrustManager[] trustManagers = {
            new X509TrustManager() {
                @Override
                public void checkClientTrusted(X509Certificate[] x509Certificates, String s) {
                    System.out.println("---- checkClientTrusted: " + s);
                }
                @Override
                public void checkServerTrusted(X509Certificate[] x509Certificates, String s) {
                    System.out.println("---- checkServerTrusted: " + s);
                }
                @Override
                public X509Certificate[] getAcceptedIssuers() {
                    return null;
                }
            }
        };
        try {
            SSLContext sslContext = SSLContext.getInstance("ssl");
            sslContext.init(null, trustManagers, null);
            return sslContext;
        } catch (NoSuchAlgorithmException | KeyManagementException ex) {
            throw new IllegalStateException(ex);
        }
    }


    /**
     * Return SSLContext whic uses certificates stored in cacerts.jks file
     * @param jksUrl Store URL (for example: "classpath:cacerts.jks")
     * @param jksPassword Store password
     * @return Configured SSLContext
     */
    private static SSLContext getSslContext(String jksUrl, String jksPassword) {
        try (InputStream jskStream = new URL(jksUrl).openStream()) {

            KeyStore keyStore = KeyStore.getInstance("JKS");
            keyStore.load(jskStream, jksPassword.toCharArray());

            KeyManagerFactory keyManagerFactory = KeyManagerFactory.getInstance("SunX509");
            keyManagerFactory.init(keyStore, jksPassword.toCharArray());

            TrustManagerFactory trustManagerFactory = TrustManagerFactory.getInstance("SunX509");
            trustManagerFactory.init(keyStore);

            SSLContext sslContext = SSLContext.getInstance("ssl");
            sslContext.init(keyManagerFactory.getKeyManagers(), trustManagerFactory.getTrustManagers(), null);

            return sslContext;

        } catch (NoSuchAlgorithmException | KeyStoreException | CertificateException | IOException |
                 UnrecoverableKeyException | KeyManagementException ex) {
            throw new IllegalStateException(ex);
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
