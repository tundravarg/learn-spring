package tuman.learnspring.server.services;


import org.springframework.stereotype.Service;

@Service
public class HttpClientTestService {

    public void callPing() {
        System.out.println("------------------- Call PING...");
    }

}
