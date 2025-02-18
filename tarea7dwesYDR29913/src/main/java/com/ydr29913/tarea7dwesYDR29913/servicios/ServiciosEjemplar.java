package com.ydr29913.tarea7dwesYDR29913.servicios;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ydr29913.tarea7dwesYDR29913.modelo.Ejemplar;
import com.ydr29913.tarea7dwesYDR29913.repositorios.EjemplarRepository;

@Service
public class ServiciosEjemplar {

	@Autowired
	private EjemplarRepository ejemplarrepo;
	
	
	public void insertarEjemplar(Ejemplar ejemplar) {
		ejemplarrepo.saveAndFlush(ejemplar);
	}
	
	public List<Ejemplar> obtenerTodosLosEjemplares() {
	    return ejemplarrepo.findAll();
	}
	
	public List<Ejemplar> obtenerEjemplaresPorPlanta(Long plantaId) {
        return ejemplarrepo.findByPlantaId(plantaId);
    }
	
	public Ejemplar obtenerEjemplarPorId(Long id) {
        Optional<Ejemplar> ejemplar = ejemplarrepo.findById(id);
        return ejemplar.orElse(null);
    }
}