package com.ydr29913.tarea7dwesYDR29913.repositorios;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ydr29913.tarea7dwesYDR29913.modelo.Ejemplar;
import com.ydr29913.tarea7dwesYDR29913.modelo.Mensaje;
import com.ydr29913.tarea7dwesYDR29913.modelo.Persona;
import com.ydr29913.tarea7dwesYDR29913.modelo.Planta;

@Repository
public interface MensajeRepository extends JpaRepository<Mensaje, Long>{

	List<Mensaje> findAll();
	
	List<Mensaje> findByEjemplar_Planta_Id(Long plantaId);
	
	List<Mensaje> findByPlanta(Planta planta);
	
	List<Mensaje> findByPersona(Persona persona);
	
	List<Mensaje> findByEjemplar(Ejemplar ejemplar);
	
	List<Mensaje> findByFechaHoraBetween(Date startDate, Date endDate);
}