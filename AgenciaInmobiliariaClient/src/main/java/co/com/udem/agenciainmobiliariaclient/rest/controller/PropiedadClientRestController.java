package co.com.udem.agenciainmobiliariaclient.rest.controller;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.loadbalancer.LoadBalancerClient;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import co.com.udem.agenciainmobiliariaclient.domain.PropiedadDTO;
import co.com.udem.agenciainmobiliariaclient.entities.UserToken;
import co.com.udem.agenciainmobiliariaclient.repositories.UserTokenRepository;

@RestController
public class PropiedadClientRestController {

	@Autowired
    RestTemplate restTemplate;
   
    @Autowired
    UserTokenRepository userTokenRepository;
   
    @Autowired
    UserToken userToken;
    
    @Autowired
    private LoadBalancerClient loadBalancer;
    
	@Autowired
	private ModelMapper modelMapper = new ModelMapper();
	
	
	@PostMapping("/agenciainmobiliaria/adicionarPropiedad")
    public Map<String, String> adicionarPropiedad(@RequestHeader("Authorization") String token, @RequestBody PropiedadDTO propiedadDTO) {      
		Map<String, String> response = new HashMap<>();
    	ServiceInstance serviceInstance=loadBalancer.choose("agenciainmobiliaria");
        System.out.println(serviceInstance.getUri());       
        String baseUrl=serviceInstance.getUri().toString();       
        baseUrl=baseUrl+"/agenciainmobiliaria/adicionarPropiedad";
		
        HttpHeaders headers = new HttpHeaders();
	    headers.setContentType(MediaType.APPLICATION_JSON);
	    headers.set("Authorization", token);
	    HttpEntity<PropiedadDTO> entity = new HttpEntity<PropiedadDTO>(propiedadDTO,headers);
        
	    ResponseEntity<Map> postResponse= restTemplate.postForEntity(baseUrl, entity,  Map.class);
		response = postResponse.getBody();
		
		return response;   
    }
	
	
	//Filtro propiedad por 1 campo
	@GetMapping("/agenciainmobiliaria/filtropropiedad/{parametro}")
	public Iterable<PropiedadDTO> filtoPropiedad(@RequestHeader("Authorization") String token,@PathVariable double parametro) {		
		
		Iterable<PropiedadDTO> propiedadDTO = null;
    	ServiceInstance serviceInstance=loadBalancer.choose("agenciainmobiliaria");
        System.out.println(serviceInstance.getUri());       
        String baseUrl=serviceInstance.getUri().toString();       
        baseUrl=baseUrl+"/agenciainmobiliaria/filtropropiedad/"+parametro;
		
        HttpHeaders headers = new HttpHeaders();
	    headers.setContentType(MediaType.APPLICATION_JSON);
	    headers.set("Authorization", token);
	    
	    HttpEntity<String> entity = new HttpEntity<String>(headers);
		
        ResponseEntity<Object[]> response = restTemplate.exchange(baseUrl, HttpMethod.GET, entity, Object[].class);
        Object[] objects = response.getBody();
        propiedadDTO = Arrays.asList(modelMapper.map(objects, PropiedadDTO[].class));
	    
		return propiedadDTO;
	}
						
	//Filtro propiedad por 2 campos
	@GetMapping("/agenciainmobiliaria/filtropropiedad/{parametro1}/{parametro2}")
	public Iterable<PropiedadDTO> filtoPropiedad(@RequestHeader("Authorization") String token, @PathVariable double parametro1,@PathVariable double parametro2) {	

		Iterable<PropiedadDTO> propiedadDTO = null;
    	ServiceInstance serviceInstance=loadBalancer.choose("agenciainmobiliaria");
        System.out.println(serviceInstance.getUri());       
        String baseUrl=serviceInstance.getUri().toString();       
        baseUrl=baseUrl+"/agenciainmobiliaria/filtropropiedad/"+parametro1+"/"+parametro2;
		
        HttpHeaders headers = new HttpHeaders();
	    headers.setContentType(MediaType.APPLICATION_JSON);
	    headers.set("Authorization", token);
	    
	    HttpEntity<String> entity = new HttpEntity<String>(headers);
		
        ResponseEntity<Object[]> response = restTemplate.exchange(baseUrl, HttpMethod.GET, entity, Object[].class);
        Object[] objects = response.getBody();
        propiedadDTO = Arrays.asList(modelMapper.map(objects, PropiedadDTO[].class));
	    
		return propiedadDTO;
	}	
	
	//Filtro propiedad por los 3 campos
	@GetMapping("/agenciainmobiliaria/filtropropiedad/{valor}/{numeroHabitaciones}/{areaMetrosCuadros}")
	public Iterable<PropiedadDTO> filtoPropiedad(@RequestHeader("Authorization") String token,@PathVariable double valor,@PathVariable int numeroHabitaciones, @PathVariable double areaMetrosCuadros) {		
		
		Iterable<PropiedadDTO> propiedadDTO = null;
    	ServiceInstance serviceInstance=loadBalancer.choose("agenciainmobiliaria");
        System.out.println(serviceInstance.getUri());       
        String baseUrl=serviceInstance.getUri().toString();       
        baseUrl=baseUrl+"/agenciainmobiliaria/filtropropiedad/"+valor+"/"+numeroHabitaciones+"/"+areaMetrosCuadros;
		
        HttpHeaders headers = new HttpHeaders();
	    headers.setContentType(MediaType.APPLICATION_JSON);
	    headers.set("Authorization", token);
	    
	    HttpEntity<String> entity = new HttpEntity<String>(headers);
		
        ResponseEntity<Object[]> response = restTemplate.exchange(baseUrl, HttpMethod.GET, entity, Object[].class);
        Object[] objects = response.getBody();
        propiedadDTO = Arrays.asList(modelMapper.map(objects, PropiedadDTO[].class));
	    
		return propiedadDTO;
	}
	
	//Buscar por el Id de una propiedad
	@GetMapping("/agenciainmobiliaria/propiedad/{id}")
	public PropiedadDTO buscarPropiedad(@RequestHeader("Authorization") String token,@PathVariable Long id) {		
		
		PropiedadDTO propiedadDTO = null;
    	ServiceInstance serviceInstance=loadBalancer.choose("agenciainmobiliaria");
        System.out.println(serviceInstance.getUri());       
        String baseUrl=serviceInstance.getUri().toString();       
        baseUrl=baseUrl+"/agenciainmobiliaria/propiedad/"+id;
    	
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("Authorization", token);
        HttpEntity<String> entity = new HttpEntity<String>(headers);
        
        ResponseEntity<PropiedadDTO> response = restTemplate.exchange(baseUrl, HttpMethod.GET, entity, PropiedadDTO.class);
       
        propiedadDTO = modelMapper.map(response.getBody(), PropiedadDTO.class);
        
        return propiedadDTO;     		
	}	
	
	
	//Listar todas las propiedades del usuario que se autentico
	@GetMapping("/agenciainmobiliaria/propiedadesUsuario")
	public Iterable<PropiedadDTO> listarPropiedadesPorUsuario(@RequestHeader("Authorization") String token){
		
		Iterable<PropiedadDTO> propiedadDTO = null;
    	ServiceInstance serviceInstance=loadBalancer.choose("agenciainmobiliaria");
        System.out.println(serviceInstance.getUri());       
        String baseUrl=serviceInstance.getUri().toString();       
        baseUrl=baseUrl+"/agenciainmobiliaria/propiedadesUsuario";
		
        HttpHeaders headers = new HttpHeaders();
	    headers.setContentType(MediaType.APPLICATION_JSON);
	    headers.set("Authorization", token);
	    
	    HttpEntity<String> entity = new HttpEntity<String>(headers);
		
        ResponseEntity<Object[]> response = restTemplate.exchange(baseUrl, HttpMethod.GET, entity, Object[].class);
        Object[] objects = response.getBody();
        propiedadDTO = Arrays.asList(modelMapper.map(objects, PropiedadDTO[].class));
			
		return propiedadDTO;			
	}	
	
	//Listar todas las propiedades a nivel general
	@GetMapping("/agenciainmobiliaria/propiedades")
	public Iterable<PropiedadDTO> listarPropiedades(@RequestHeader("Authorization") String token){
		
		Iterable<PropiedadDTO> propiedadDTO = null;
    	ServiceInstance serviceInstance=loadBalancer.choose("agenciainmobiliaria");
        System.out.println(serviceInstance.getUri());       
        String baseUrl=serviceInstance.getUri().toString();       
        baseUrl=baseUrl+"/agenciainmobiliaria/propiedades";
		
        HttpHeaders headers = new HttpHeaders();
	    headers.setContentType(MediaType.APPLICATION_JSON);
	    headers.set("Authorization", token);
	    
	    HttpEntity<String> entity = new HttpEntity<String>(headers);
		
        ResponseEntity<Object[]> response = restTemplate.exchange(baseUrl, HttpMethod.GET, entity, Object[].class);
        Object[] objects = response.getBody();
        propiedadDTO = Arrays.asList(modelMapper.map(objects, PropiedadDTO[].class));
		
		return propiedadDTO;			
	}
	
	
	//Modificar una propiedad
	@PutMapping("/agenciainmobiliaria/propiedad/{id}")
	public ResponseEntity<Object> updatePropiedad(@RequestHeader("Authorization") String token,@RequestBody PropiedadDTO newPropiedad, @PathVariable Long id){
		 
		 ServiceInstance serviceInstance=loadBalancer.choose("agenciainmobiliaria");
         System.out.println(serviceInstance.getUri());       
         String baseUrl=serviceInstance.getUri().toString();       
         baseUrl=baseUrl+"/agenciainmobiliaria/propiedad/{id}";
		 HttpHeaders headers = new HttpHeaders();
	     headers.setContentType(MediaType.APPLICATION_JSON);
	     headers.set("Authorization", token);
	     HttpEntity<PropiedadDTO> entity = new HttpEntity<PropiedadDTO>(newPropiedad,headers);
	        
	     Map<String,Long> parametros = new HashMap<String, Long>();
	     parametros.put("id", id);
	        
	     restTemplate.put(baseUrl,entity,parametros);
	        
	     return ResponseEntity.ok("Se actualizó exitosamente");
	}	
	
	//Eliminar una propiedad
	@DeleteMapping("/agenciainmobiliaria/propiedad/{id}")
    public Object[] eliminarPropiedad(@RequestHeader("Authorization") String token, @PathVariable Long id) {

		 ServiceInstance serviceInstance=loadBalancer.choose("agenciainmobiliaria");
         System.out.println(serviceInstance.getUri());       
         String baseUrl=serviceInstance.getUri().toString();       
         baseUrl=baseUrl+"/agenciainmobiliaria/propiedad/"+id;
         
		 HttpHeaders headers = new HttpHeaders();
	     headers.setContentType(MediaType.APPLICATION_JSON);
	     headers.set("Authorization", token);
	     HttpEntity<String> entity = new HttpEntity<String>(headers);
   	
         ResponseEntity<Object[]> response = restTemplate.exchange(baseUrl, HttpMethod.DELETE, entity, Object[].class);
         Object[] objects = response.getBody();
   	     return objects;
    }
	
}
