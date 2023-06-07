package io.github.edupinhata;

import io.github.edupinhata.domain.entity.Cliente;
import io.github.edupinhata.domain.repository.Clientes;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@SpringBootApplication
@RestController
public class VendasApplication {

    @Value("${application.name}")
    private String applicationNameProperties;

    @Autowired
    @Qualifier("applicationName")
    private String applicationNameConfiguration;

    @Bean
    public CommandLineRunner init(@Autowired Clientes clientes){
       return args -> {
           clientes.save(new Cliente("Eduardo"));
           clientes.save(new Cliente("Esther"));
           clientes.save(new Cliente("Fulano"));

           List<Cliente> allClientes = clientes.getAll();
           allClientes.forEach(System.out::println);

           System.out.println("======  UPDATING TEST");
           List<Cliente> cList = clientes.getByName("Fulano");

           for (Cliente cliente : cList) {
               System.out.printf("cliente: %s | id: %d\n", cliente.getNome(), cliente.getId());
           }

           Cliente newC = new Cliente( cList.get(0).getId(), "Ciclano");
           System.out.printf("newC: %s | id: %d\n", cList.get(0).getNome(), cList.get(0).getId());
           clientes.update(newC);

           allClientes = clientes.getAll();
           allClientes.forEach(System.out::println);

           System.out.println("====== DELETE TEST");
           clientes.delete(cList.get(0));

           allClientes = clientes.getAll();
           allClientes.forEach(System.out::println);
       };
    }

   @GetMapping("/hello")
    public String HelloWorld(){
       return String.format("applicationNameProperties: %s <br> applicationNameConfiguration: %s",
               applicationNameProperties,
               applicationNameConfiguration);
    }

    public static void main(String[] args) {
        SpringApplication.run(VendasApplication.class, args);
    }
}
