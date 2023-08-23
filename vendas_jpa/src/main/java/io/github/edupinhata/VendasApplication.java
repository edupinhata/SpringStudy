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
           System.out.println("Saving clients");
           clientes.salvar(new Cliente("Eduardo"));
           clientes.salvar(new Cliente("Esther"));

           List<Cliente> allClientes = clientes.getAll();
            allClientes.forEach(System.out::println);

           System.out.println("");
            Cliente eduardoAtualizado = new Cliente("Eduardo Pinhata");
            eduardoAtualizado.setId(allClientes.get(0).getId());
            clientes.update(eduardoAtualizado);
            allClientes = clientes.getAll();
            allClientes.forEach(System.out::println);

           System.out.println("");
            clientes.deletar(allClientes.get(0).getId());
            allClientes = clientes.getAll();
            allClientes.forEach(System.out::println);

           System.out.println("");
            clientes.salvar(eduardoAtualizado);
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
