package com.ydr29913.tarea7dwesYDR29913.servicios;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ydr29913.tarea7dwesYDR29913.modelo.Persona;
import com.ydr29913.tarea7dwesYDR29913.repositorios.PersonaRepository;

@Service
public class ServiciosPersona {

	@Autowired
	private PersonaRepository personarepo;
	
	
	public void insertarPersona(Persona persona) {
		personarepo.saveAndFlush(persona);
	}
	
	//Sirve para validar que el nombre esta introducido correctamente
	public boolean validarNombre(String nombre) {
        String regex = "^[a-zA-ZáéíóúÁÉÍÓÚüÜñÑ ]+$";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(nombre);
        
        if (!matcher.matches() || nombre.length() < 1 || nombre.length() > 50) {
            return false;
        }

        return personarepo.findByNombre(nombre).isEmpty();
    }
	
	//Sirve para validar que el email esta introducido correctamente
	public boolean validarEmail(String email) {
        String regex = "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(email);

        if (!matcher.matches() || email.isEmpty()) {
            return false;
        }

        return personarepo.findByEmail(email).isEmpty();
	}
}