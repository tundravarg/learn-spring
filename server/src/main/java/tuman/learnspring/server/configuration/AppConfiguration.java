package tuman.learnspring.server.configuration;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Configuration
@PropertySource("classpath:props.properties")
@PropertySource(value = "classpath:props.yml", factory = YamlPropertSourceFactory.class)
public class AppConfiguration {
}
