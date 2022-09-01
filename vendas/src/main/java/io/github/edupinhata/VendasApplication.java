package io.github.edupinhata;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@RestController
public class VendasApplication {

    @Value("${application.name}")
    private String applicationNameProperties;

    @Autowired
    @Qualifier("applicationName")
    private String applicationNameConfiguration;

   @GetMapping("/hello")
    public String HelloWorld(){
       return String.format("applicationNameProperties: %s \n applicationNameConfiguration: %s",
               applicationNameProperties,
               applicationNameConfiguration);
    }

    public static void main(String[] args) {
        SpringApplication.run(VendasApplication.class, args);
    }
}
