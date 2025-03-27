package com.ydr29913.tarea7dwesYDR29913.repositorios;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ydr29913.tarea7dwesYDR29913.modelo.Credenciales;

@Repository
public interface CredencialesRepository extends JpaRepository<Credenciales, Long>{

	Credenciales findByUsuario(String usuario);
	
	Credenciales findByPassword(String password);
	
	List<Credenciales> findByPersonaId(Long idPersona);
}