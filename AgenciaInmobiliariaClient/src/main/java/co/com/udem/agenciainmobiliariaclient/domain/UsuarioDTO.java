package co.com.udem.agenciainmobiliariaclient.domain;



public class UsuarioDTO {
	
	private Long id;
	private String nombres;
	private String apellidos;
	private String tipoIdentificacion;
	private String numeroIdentificacion;
	private String direccion;
	private int telefono;
	private String email;
	private String password;
	
	public UsuarioDTO(Long id, String nombres, String apellidos, String tipoIdentificacion, String numeroIdentificacion,
			String direccion, int telefono, String email, String password) {
		super();
		this.id = id;
		this.nombres = nombres;
		this.apellidos = apellidos;
		this.tipoIdentificacion = tipoIdentificacion;
		this.numeroIdentificacion = numeroIdentificacion;
		this.direccion = direccion;
		this.telefono = telefono;
		this.email = email;
		this.password = password;
	}

	public UsuarioDTO() {
		super();
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNombres() {
		return nombres;
	}

	public void setNombres(String nombres) {
		this.nombres = nombres;
	}

	public String getAPellidos() {
		return apellidos;
	}

	public void setAPellidos(String apellidos) {
		this.apellidos = apellidos;
	}

	public String getTipoIdentificacion() {
		return tipoIdentificacion;
	}

	public void setTipoIdentificacion(String tipoIdentificacion) {
		this.tipoIdentificacion = tipoIdentificacion;
	}

	public String getNumeroIdentificacion() {
		return numeroIdentificacion;
	}

	public void setNumeroIdentificacion(String numeroIdentificacion) {
		this.numeroIdentificacion = numeroIdentificacion;
	}

	public String getDireccion() {
		return direccion;
	}

	public void setDireccion(String direccion) {
		this.direccion = direccion;
	}

	public int getTelefono() {
		return telefono;
	}

	public void setTelefono(int telefono) {
		this.telefono = telefono;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
	
	
	
	

}
