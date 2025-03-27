package com.ydr29913.tarea7dwesYDR29913.modelo;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

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
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;

@Entity
@Table(name="pedidos")
public class Pedido {

	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column
	@Temporal(TemporalType.DATE)
	private Date fecha;
	
	@ManyToOne
    @JoinColumn(name = "clienteId")
    private Cliente cliente;

    @OneToMany(mappedBy = "pedido", cascade = CascadeType.ALL)
    private Set<Ejemplar> ejemplares = new HashSet<Ejemplar>();
    //private List<Ejemplar> ejemplares = new ArrayList<>();
	
	
	//Constructor
	public Pedido() {}

	
	//Getters y Setters
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Date getFecha() {
		return fecha;
	}

	public void setFecha(Date fecha) {
		this.fecha = fecha;
	}

	public Cliente getCliente() {
		return cliente;
	}

	public void setCliente(Cliente cliente) {
		this.cliente = cliente;
	}

	public Set<Ejemplar> getEjemplares() {
		return ejemplares;
	}

	public void setEjemplares(Set<Ejemplar> ejemplares) {
		this.ejemplares = ejemplares;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	


	//Metodos extra
	public void agregarEjemplar(Ejemplar ejemplar) {
        this.ejemplares.add(ejemplar);
    }
}