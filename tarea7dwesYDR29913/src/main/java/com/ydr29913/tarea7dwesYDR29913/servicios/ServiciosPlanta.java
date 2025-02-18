package com.ydr29913.tarea7dwesYDR29913.servicios;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ydr29913.tarea7dwesYDR29913.modelo.Planta;
import com.ydr29913.tarea7dwesYDR29913.repositorios.PlantaRepository;

@Service
public class ServiciosPlanta {
	
	@Autowired
	private PlantaRepository plantarepo;
	
	
	public void insertarPlanta(Planta planta) {
		plantarepo.saveAndFlush(planta);
	}
	
	public void modificarPlanta(Planta planta) {
    	plantarepo.save(planta);
    }
	
	public List<Planta> obtenerPlantasOrdenadasAlfabeticamente() {
        return plantarepo.findAllByOrderByNombreComunAsc();
    }
	
	public boolean existePlantaPorCodigo(String codigo) {
        return plantarepo.findByCodigo(codigo).isPresent();
    }
	
	public Planta obtenerPlantaPorCodigo(String codigo) {
        Optional<Planta> planta = plantarepo.findByCodigo(codigo);
        return planta.orElse(null);
    }
	
	public Planta obtenerPlantaPorId(Long id) {
        Optional<Planta> planta = plantarepo.findById(id);
        return planta.orElse(null);
    }
}