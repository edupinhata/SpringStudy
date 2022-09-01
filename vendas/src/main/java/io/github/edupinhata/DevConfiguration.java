package io.github.edupinhata;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

@Configuration
@Profile("development")
public class DevConfiguration {

   @Bean(name = "applicationName")
    public String applicationName(){
        return "Application Sales from DevConfiguration";
    }

    @Bean
    public CommandLineRunner execute(){
       return args -> {
           System.out.println("===== Running development configuration.");
       };
    }
}
