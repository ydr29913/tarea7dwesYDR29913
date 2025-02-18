package com.ydr29913.tarea7dwesYDR29913.repositorios;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ydr29913.tarea7dwesYDR29913.modelo.Ejemplar;
import com.ydr29913.tarea7dwesYDR29913.modelo.Mensaje;

@Repository
public interface MensajeRepository extends JpaRepository<Mensaje, Long>{

	List<Mensaje> findByEjemplar_Planta_Id(Long plantaId);
	
	List<Mensaje> findByEjemplar(Ejemplar ejemplar);
}