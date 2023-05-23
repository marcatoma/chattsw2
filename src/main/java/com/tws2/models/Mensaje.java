package com.tws2.models;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "mensaje")
public class Mensaje {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id_mensaje")
	private Long idMensaje;

	@NotNull(message = "el id de destino es requerido")
	@Column(name = "id_user_destino")
	private Long idUserDestino;

	@Column(name = "rol")
	private String rol;

	@NotBlank(message = "el mensaje debe contener caracteres")
	@Column(name = "mensaje")
	private String mensaje;

	@JsonIgnoreProperties({ "hibernateLazyInitializer", "handler" })
	@ManyToOne(fetch = FetchType.LAZY)
	private Usuario usuario;

	@Column(name = "prompts")
	private String prompts;

	@Column(name = "fecha")
	private Date fecha;

	public Long getIdMensaje() {
		return idMensaje;
	}

	public void setIdMensaje(Long idMensaje) {
		this.idMensaje = idMensaje;
	}

	public Long getIdUserDestino() {
		return idUserDestino;
	}

	public void setIdUserDestino(Long idUserDestino) {
		this.idUserDestino = idUserDestino;
	}

	public String getRol() {
		return rol;
	}

	public void setRol(String rol) {
		this.rol = rol;
	}

	public String getMensaje() {
		return mensaje;
	}

	public void setMensaje(String mensaje) {
		this.mensaje = mensaje;
	}

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	public String getPrompts() {
		return prompts;
	}

	public void setPrompts(String prompts) {
		this.prompts = prompts;
	}

	public Date getFecha() {
		return fecha;
	}

	public void setFecha(Date fecha) {
		this.fecha = fecha;
	}

}
