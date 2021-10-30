package tuman.learnspring.server;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class PropertiesTestService {

    @Value("${test.prop1}")
    private String property1;
    @Value("${test.prop2}")
    private String property2;
    @Value("${test.prop3}")
    private String property3;

    public void testProperties() {
        logPropert("prop1", property1);
        logPropert("prop2", property2);
        logPropert("prop3", property3);
    }

    private void logPropert(String name, String value) {
        System.out.printf("---------------- PROPERTY %s: %s\n", name, value);
    }

}
