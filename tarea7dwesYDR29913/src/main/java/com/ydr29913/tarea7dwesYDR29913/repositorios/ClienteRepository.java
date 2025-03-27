package com.ydr29913.tarea7dwesYDR29913.repositorios;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ydr29913.tarea7dwesYDR29913.modelo.Cliente;

@Repository
public interface ClienteRepository extends JpaRepository<Cliente, Long> {

	Optional<Cliente> findById(Long id);
	
	Optional<Cliente> findByNombre(String nombre);
	
	Optional<Cliente> findByEmail(String email);
	
	Optional<Cliente> findByTelefono(String telefono);
	
	List<Cliente> findByPersonaId(Long personaId);
}