package co.com.udem.agenciainmobiliariaclient.domain;

public class AutenticationResponseDTO {

	
	private String username;
    private String token;
	public AutenticationResponseDTO(String username, String token) {
		super();
		this.username = username;
		this.token = token;
	}
	public AutenticationResponseDTO() {
		super();
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getToken() {
		return token;
	}
	public void setToken(String token) {
		this.token = token;
	}
    
    
    
}
