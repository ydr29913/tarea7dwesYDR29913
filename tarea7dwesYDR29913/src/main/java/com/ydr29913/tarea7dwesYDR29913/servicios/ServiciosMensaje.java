package com.ydr29913.tarea7dwesYDR29913.servicios;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ydr29913.tarea7dwesYDR29913.modelo.Ejemplar;
import com.ydr29913.tarea7dwesYDR29913.modelo.Mensaje;
import com.ydr29913.tarea7dwesYDR29913.modelo.Persona;
import com.ydr29913.tarea7dwesYDR29913.modelo.Planta;
import com.ydr29913.tarea7dwesYDR29913.repositorios.MensajeRepository;

@Service
public class ServiciosMensaje {
	
	@Autowired
	private MensajeRepository mensajerepo;
	
	
	public void insertarMensaje(Mensaje mensaje) {
		mensajerepo.saveAndFlush(mensaje);
	}
	
	public List<Mensaje> obtenerTodosLosMensajes() {
		return mensajerepo.findAll();
	}
	
	public List<Mensaje> obtenerMensajesPorPlanta(Long plantaId) {
        return mensajerepo.findByEjemplar_Planta_Id(plantaId);
    }
	
	public List<Mensaje> obtenerMensajesPorPlantaSeleccionada(Planta planta) {
        return mensajerepo.findByPlanta(planta);
    }
	
	public List<Mensaje> obtenerMensajesPorPersonaSeleccionada(Persona persona) {
        return mensajerepo.findByPersona(persona);
    }
	
	public List<Mensaje> obtenerMensajesPorEjemplar(Ejemplar ejemplar) {
        return mensajerepo.findByEjemplar(ejemplar);
    }
	
	public List<Mensaje> obtenerMensajesPorFecha(Date startDate, Date endDate) {
		return mensajerepo.findByFechaHoraBetween(startDate, endDate);
	}
}