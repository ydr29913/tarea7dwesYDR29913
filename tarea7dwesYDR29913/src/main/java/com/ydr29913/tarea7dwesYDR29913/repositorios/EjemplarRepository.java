package com.ydr29913.tarea7dwesYDR29913.repositorios;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ydr29913.tarea7dwesYDR29913.modelo.Ejemplar;
import com.ydr29913.tarea7dwesYDR29913.modelo.Planta;

@Repository
public interface EjemplarRepository extends JpaRepository<Ejemplar, Long> {
	
	List<Ejemplar> findByPlantaId(Long plantaId);
	
	default Long ultimoIdEjemplarByPlanta(Planta planta) {
		List<Ejemplar> lista = findAll();
		if (!lista.isEmpty()) {
			long ret = 0;
			for (Ejemplar e: lista)
				if (e.getPlanta().getId().equals(planta.getId()))
					ret++;
			return ret;
		}
		return 0L;
	}
}