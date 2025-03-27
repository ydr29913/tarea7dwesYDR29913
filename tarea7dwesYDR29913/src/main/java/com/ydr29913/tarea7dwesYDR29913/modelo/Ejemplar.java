package com.ydr29913.tarea7dwesYDR29913.modelo;

import java.io.Serializable;
import java.util.LinkedList;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table(name="ejemplares")
public class Ejemplar implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column
	private String nombre;
	
	@Column
	private Boolean disponible = true;
	
	@ManyToOne
	@JoinColumn(name="idPlanta")
	private Planta planta;
	
	@OneToMany(cascade = CascadeType.ALL)
	@JoinColumn(name="idEjemplar")
	private List<Mensaje> mensajes = new LinkedList<>();
	
	@ManyToOne
    @JoinColumn(name = "pedidoId")
    private Pedido pedido;
	
	
	//Constructor
	public Ejemplar() {}

	
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
	
	public boolean isDisponible() {
		return disponible;
	}

	public void setDisponible(boolean disponible) {
		this.disponible = disponible;
	}

	public Planta getPlanta() {
		return planta;
	}

	public void setPlanta(Planta planta) {
		this.planta = planta;
	}
	
	public List<Mensaje> getMensajes() {
		return mensajes;
	}

	public void setMensajes(List<Mensaje> mensajes) {
		this.mensajes = mensajes;
	}

	public Pedido getPedido() {
		return pedido;
	}

	public void setPedido(Pedido pedido) {
		this.pedido = pedido;
	}
	
	public Boolean getDisponible() {
		return disponible;
	}

	public void setDisponible(Boolean disponible) {
		this.disponible = disponible;
	}
	
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
}