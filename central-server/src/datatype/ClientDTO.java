package datatype;

import java.time.LocalDate;

public class ClientDTO extends UserDTO {
	
	private String apellido;
	private LocalDate nacimiento;
	private String nacionalidad;
	private DocumentType tipoDocumento;
	private String numDocumento;
	
	
	

	public ClientDTO(String nickname, String nombre, String email, String pass, String apellido, LocalDate nacimiento,
			String nacionalidad, DocumentType tipo_documento, String num_documento, String imagen) {
		super(nickname, nombre, email, pass, imagen);
		this.setApellido(apellido);
		this.setNacimiento(nacimiento);
		this.setNacionalidad(nacionalidad);
		this.setTipoDocumento(tipo_documento);
		this.setNumDocumento(num_documento);
	}




	public String getApellido() {
		return apellido;
	}




	public void setApellido(String apellido) {
		this.apellido = apellido;
	}




	public LocalDate getNacimiento() {
		return nacimiento;
	}




	public void setNacimiento(LocalDate nacimiento) {
		this.nacimiento = nacimiento;
	}




	public String getNacionalidad() {
		return nacionalidad;
	}




	public void setNacionalidad(String nacionalidad) {
		this.nacionalidad = nacionalidad;
	}




	public DocumentType getTipoDocumento() {
		return tipoDocumento;
	}




	public void setTipoDocumento(DocumentType tipoDocumento) {
		this.tipoDocumento = tipoDocumento;
	}




	public String getNumDocumento() {
		return numDocumento;
	}




	public void setNumDocumento(String numDocumento) {
		this.numDocumento = numDocumento;
	}


}
