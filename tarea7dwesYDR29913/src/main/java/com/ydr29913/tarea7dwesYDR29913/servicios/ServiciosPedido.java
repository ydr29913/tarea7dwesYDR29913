package com.ydr29913.tarea7dwesYDR29913.servicios;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ydr29913.tarea7dwesYDR29913.modelo.Pedido;
import com.ydr29913.tarea7dwesYDR29913.repositorios.PedidoRepository;

@Service
public class ServiciosPedido {

	@Autowired
	private PedidoRepository pedidorepo;
	
	
	public void insertarPedido(Pedido pedido) {
		pedidorepo.saveAndFlush(pedido);
	}
}