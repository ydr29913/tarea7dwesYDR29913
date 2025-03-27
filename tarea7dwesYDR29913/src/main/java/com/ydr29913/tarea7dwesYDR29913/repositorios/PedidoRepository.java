package com.ydr29913.tarea7dwesYDR29913.repositorios;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ydr29913.tarea7dwesYDR29913.modelo.Pedido;

@Repository
public interface PedidoRepository extends JpaRepository<Pedido, Long> {

}