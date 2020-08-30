package co.com.udem.agenciainmobiliariaclient.rest.controller;

import org.springframework.web.bind.annotation.RestController;

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
import org.springframework.web.client.RestTemplate;
import com.google.gson.Gson;
import co.com.udem.agenciainmobiliariaclient.domain.AutenticationRequestDTO;
import co.com.udem.agenciainmobiliariaclient.domain.AutenticationResponseDTO;
import co.com.udem.agenciainmobiliariaclient.domain.UsuarioDTO;
import co.com.udem.agenciainmobiliariaclient.entities.UserToken;
import co.com.udem.agenciainmobiliariaclient.repositories.UserTokenRepository;


@RestController
public class AgenciaInmobiliariaClientRestController {

	
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
   
    @PostMapping("/autenticar")
    public String autenticar(@RequestBody AutenticationRequestDTO autenticationRequestDTO) {
    	    	
    	ServiceInstance serviceInstance=loadBalancer.choose("agenciainmobiliaria");
        System.out.println(serviceInstance.getUri());       
        String baseUrl=serviceInstance.getUri().toString();       
        baseUrl=baseUrl+"/auth/signin";
        ResponseEntity<String> postResponse = restTemplate.postForEntity(baseUrl, autenticationRequestDTO, String.class);
        System.out.println("Respuesta: "+postResponse.getBody());
        Gson g = new Gson();
        AutenticationResponseDTO autenticationResponseDTO = g.fromJson(postResponse.getBody(), AutenticationResponseDTO.class);
        userToken.setUsername(autenticationResponseDTO.getUsername());
        userToken.setToken(autenticationResponseDTO.getToken());
        userTokenRepository.save(userToken);
        return autenticationResponseDTO.getToken();
    }

    //Consultar Usuarios
    @GetMapping("/agenciainmobiliaria/usuarios")
    public Iterable<UsuarioDTO> consultaUsuarios(@RequestHeader("Authorization") String token) {
    	Iterable<UsuarioDTO> usuarioDTO = null;
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("Authorization", token);
        
    	ServiceInstance serviceInstance=loadBalancer.choose("agenciainmobiliaria");
        System.out.println(serviceInstance.getUri());       
        String baseUrl=serviceInstance.getUri().toString();       
        baseUrl=baseUrl+"/agenciainmobiliaria/usuarios";
        
        HttpEntity<String> entity = new HttpEntity<String>(headers);
        ResponseEntity<Object[]> response = restTemplate.exchange(baseUrl, HttpMethod.GET, entity, Object[].class);
        Object[] objects = response.getBody();
        usuarioDTO = Arrays.asList(modelMapper.map(objects, UsuarioDTO[].class));
        
        return usuarioDTO;       
    }
    
    //Buscar por Id usuario
    @GetMapping("/agenciainmobiliaria/usuario/{id}")
    public UsuarioDTO buscarUsuario(@RequestHeader("Authorization") String token, @PathVariable Long id) {
    	UsuarioDTO usuarioDTO = null;
    	ServiceInstance serviceInstance=loadBalancer.choose("agenciainmobiliaria");
        System.out.println(serviceInstance.getUri());       
        String baseUrl=serviceInstance.getUri().toString();       
        baseUrl=baseUrl+"/agenciainmobiliaria/usuario/"+id;
    	
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("Authorization", token);
        HttpEntity<String> entity = new HttpEntity<String>(headers);
        
        ResponseEntity<Object[]> response = restTemplate.exchange(baseUrl, HttpMethod.GET, entity, Object[].class);
       
        Object[] objects = response.getBody();
        usuarioDTO = modelMapper.map(objects, UsuarioDTO.class);
        
        return usuarioDTO;       
    }
    
    //Adicionar usuario
    @PostMapping("/agenciainmobiliaria/adicionarUsuario")
    public  Object[] adicionarUsuario(@RequestBody UsuarioDTO usuarioDTO) {
    	
    	ServiceInstance serviceInstance=loadBalancer.choose("agenciainmobiliaria");
        System.out.println(serviceInstance.getUri());       
        String baseUrl=serviceInstance.getUri().toString();       
        baseUrl=baseUrl+"/agenciainmobiliaria/adicionarUsuario";

    	ResponseEntity<Object[]> postResponse = restTemplate.postForEntity(baseUrl, usuarioDTO, Object[].class);
    	Object[] objects = postResponse.getBody();
    	 
    	 return objects;
    }
    
    //Actualizar Usuario
	@PutMapping("/agenciainmobiliaria/usuario/{id}")
	public ResponseEntity<Object> updateUsuario(@RequestHeader("Authorization") String token, @RequestBody UsuarioDTO newUser, @PathVariable Long id){
        HttpHeaders headers = new HttpHeaders();
        ServiceInstance serviceInstance=loadBalancer.choose("agenciainmobiliaria");
        System.out.println(serviceInstance.getUri());       
        String baseUrl=serviceInstance.getUri().toString();       
        baseUrl=baseUrl+"/agenciainmobiliaria/usuario/{id}";
        
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("Authorization", token);
        HttpEntity<UsuarioDTO> entity = new HttpEntity<UsuarioDTO>(newUser,headers);
        
        Map<String,Long> parametros = new HashMap<String, Long>();
        parametros.put("id", id);
        
        restTemplate.put(baseUrl,entity,parametros);
        
        return ResponseEntity.ok("Se actualizó exitosamente");
	}
    
	//Eliminar usuario
	@DeleteMapping("/agenciainmobiliaria/usuario/{id}")
    public Object[] eliminarUsuario(@RequestHeader("Authorization") String token, @PathVariable Long id) {

		 HttpHeaders headers = new HttpHeaders();
	     ServiceInstance serviceInstance=loadBalancer.choose("agenciainmobiliaria");
	     System.out.println(serviceInstance.getUri());       
	     String baseUrl=serviceInstance.getUri().toString();       
	     baseUrl=baseUrl+"/agenciainmobiliaria/usuario/"+id;
	        
	     headers.setContentType(MediaType.APPLICATION_JSON);
	     headers.set("Authorization", token);
	     HttpEntity<String> entity = new HttpEntity<String>(headers);
         
         ResponseEntity<Object[]> response = restTemplate.exchange(baseUrl, HttpMethod.DELETE, entity, Object[].class);
         Object[] objects = response.getBody();
    	 return objects;
    }
	
}
