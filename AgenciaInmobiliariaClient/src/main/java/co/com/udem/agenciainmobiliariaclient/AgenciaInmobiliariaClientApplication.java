package co.com.udem.agenciainmobiliariaclient;

import org.modelmapper.ModelMapper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

import co.com.udem.agenciainmobiliariaclient.entities.UserToken;

@SpringBootApplication
public class AgenciaInmobiliariaClientApplication {

	public static void main(String[] args) {
		SpringApplication.run(AgenciaInmobiliariaClientApplication.class, args);
	}


	@Bean
    RestTemplate restTemplate() {
        return new RestTemplate();
    }
   
    @Bean
    UserToken userToken() {
        return new UserToken();
    }
    
	@Bean
	public ModelMapper modelMapper() {
		
		return new ModelMapper();
	}
}
