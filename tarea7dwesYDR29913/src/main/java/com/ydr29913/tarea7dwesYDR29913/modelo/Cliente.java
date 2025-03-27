package com.ydr29913.tarea7dwesYDR29913.modelo;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;

@Entity
@Table(name="clientes")
public class Cliente implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column
	private String nombre;
	
	@Column
	@Temporal(TemporalType.DATE)
	private Date fechanac;
	
	@Column(unique = true)
	private String nifnie;
	
	@Column
	private String direccion;
	
	@Column
	private String email;
	
	@Column
	private String telefono;
	
	@OneToMany(mappedBy = "cliente", cascade = CascadeType.ALL)
    private List<Pedido> pedidos;
	
	@OneToOne
	@JoinColumn(name = "persona_id", referencedColumnName = "id")
	private Persona persona;
	
	
	//Constructor
	public Cliente() {}


	//Getters y Setters
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public Date getFechanac() {
		return fechanac;
	}

	public void setFechanac(Date fechanac) {
		this.fechanac = fechanac;
	}

	public String getNifnie() {
		return nifnie;
	}

	public void setNifnie(String nifnie) {
		this.nifnie = nifnie;
	}

	public String getDireccion() {
		return direccion;
	}

	public void setDireccion(String direccion) {
		this.direccion = direccion;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getTelefono() {
		return telefono;
	}

	public void setTelefono(String telefono) {
		this.telefono = telefono;
	}

	public List<Pedido> getPedidos() {
		return pedidos;
	}

	public void setPedidos(List<Pedido> pedidos) {
		this.pedidos = pedidos;
	}

	public Persona getPersona() {
		return persona;
	}

	public void setPersona(Persona persona) {
		this.persona = persona;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}
}