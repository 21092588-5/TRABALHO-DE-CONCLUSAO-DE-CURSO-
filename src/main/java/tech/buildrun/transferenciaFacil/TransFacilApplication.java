package tech.buildrun.transferenciaFacil;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@EnableFeignClients
@SpringBootApplication
public class TransFacilApplication {

	public static void main(String[] args) {
		SpringApplication.run(TransFacilApplication.class, args);
	}

}
