package com.ydr29913.tarea7dwesYDR29913.repositorios;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ydr29913.tarea7dwesYDR29913.modelo.Ejemplar;

@Repository
public interface EjemplarRepository extends JpaRepository<Ejemplar, Long> {
	
	List<Ejemplar> findByPlantaId(Long plantaId);
}