package com.ydr29913.tarea7dwesYDR29913.servicios;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ydr29913.tarea7dwesYDR29913.modelo.Credenciales;
import com.ydr29913.tarea7dwesYDR29913.repositorios.CredencialesRepository;

@Service
public class ServiciosCredenciales {

	@Autowired
	private CredencialesRepository credencialesrepo;
	
	
	//Sirve para añadir las credenciales a la base de datos
	public void insertarCredenciales(Credenciales credenciales) {
        if (credencialesrepo.findByUsuario(credenciales.getUsuario()) == null) {
            credencialesrepo.saveAndFlush(credenciales);
        }
    }
	
	//Sirve para autenticar al usuario que quiere iniciar la sesion
	public Credenciales autenticarUsuario(String usuario, String password) {
		Credenciales credenciales = credencialesrepo.findByUsuario(usuario);
		
		if (credenciales != null && credenciales.getPassword().equals(password)) {
			return credenciales;
		}
		return null;
	}
	
	//Sirve para validar que el usuario esta introducido correctamente
	public boolean validarUsuario(String usuario) {
	    if (usuario == null || usuario.contains(" ")) {
	        return false;
	    }
	    return credencialesrepo.findByUsuario(usuario) == null;
	}

	//Sirve para validar que la contraseña esta introducida correctamente
	public boolean validarPassword(String password) {
	    if (password == null || password.contains(" ")) {
	        return false;
	    }
	    return credencialesrepo.findByUsuario(password) == null;
	}
}