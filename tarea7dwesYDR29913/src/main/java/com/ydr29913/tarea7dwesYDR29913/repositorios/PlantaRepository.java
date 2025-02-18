package com.ydr29913.tarea7dwesYDR29913.repositorios;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ydr29913.tarea7dwesYDR29913.modelo.Planta;


@Repository
public interface PlantaRepository extends JpaRepository<Planta, Long> {
	
	Optional<Planta> findById(Long id);
	
	Optional<Planta> findByCodigo(String codigo);
	
	List<Planta> findAllByOrderByNombreComunAsc();
}