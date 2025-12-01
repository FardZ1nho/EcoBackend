package upc.edu.pe.ecochips;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class EcoChipsApplication {

    public static void main(String[] args) {
        SpringApplication.run(EcoChipsApplication.class, args);
    }

}
