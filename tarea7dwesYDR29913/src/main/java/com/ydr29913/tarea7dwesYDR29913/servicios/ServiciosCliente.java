package com.ydr29913.tarea7dwesYDR29913.servicios;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ydr29913.tarea7dwesYDR29913.modelo.Cliente;
import com.ydr29913.tarea7dwesYDR29913.repositorios.ClienteRepository;

@Service
public class ServiciosCliente {

	@Autowired
	private ClienteRepository clienterepo;
	
	
	public void insertarCliente(Cliente cliente) {
		clienterepo.saveAndFlush(cliente);
	}
	
	public Optional<Cliente> findById(Long id) {
		return clienterepo.findById(id);
	}
	
	public List<Cliente> obtenerClientesPorPersonaId(Long personaId) {
		return clienterepo.findByPersonaId(personaId);
	}
	
	
	//Sirve para validar que el nombre esta introducido correctamente
	public boolean validarNombre(String nombre) {
        String regex = "^[a-zA-ZáéíóúÁÉÍÓÚüÜñÑ ]+$";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(nombre);
        
        if (!matcher.matches() || nombre.length() < 1 || nombre.length() > 50) {
            return false;
        }

        return clienterepo.findByNombre(nombre).isEmpty();
    }
	
	//Sirve para validar que la fecha de nacimiento esta introducida correctamente
	public boolean validarFechaNacimiento(Date fechanac) {
		if (fechanac.after(new Date())) {
			return false;
		}
		long edad = new Date().getTime() - fechanac.getTime();
	    long edadEnAños = edad / (1000L * 60 * 60 * 24 * 365);
	    return edadEnAños >= 1 || edadEnAños <= 100;
	}
	
	//Sirve para validar que el nif/nie esta introducido correctamente
	public boolean validarNifNie(String nifnie) {
		String regex = "^(\\d{8}[A-HJ-NP-TV-Z]|[XYZ]\\d{7}[A-HJ-NP-TV-Z])$";
	    Pattern pattern = Pattern.compile(regex, Pattern.CASE_INSENSITIVE);
	    Matcher matcher = pattern.matcher(nifnie);
	    
	    if (!matcher.matches()) {
	        return false;
	    }
	    
	    //Sirve para convertir un NIE a NIF para comprobar la letra
	    if (nifnie.charAt(0) == 'X') {
	    	nifnie = "0" + nifnie.substring(1);
	    } else if (nifnie.charAt(0) == 'Y') {
	    	nifnie = "1" + nifnie.substring(1);
	    } else if (nifnie.charAt(0) == 'Z') {
	    	nifnie = "2" + nifnie.substring(1);
	    }
	    
	    //Sirve para calcular la letra
	    String letras = "TRWAGMYFPDXBNJZSQVHLCKE";
	    int numero = Integer.parseInt(nifnie.substring(0, 8));
	    char letraCalculada = letras.charAt(numero % 23);
	    char letraIntroducida = nifnie.charAt(8);
	    
	    return letraCalculada == letraIntroducida;
	}
	
	//Sirve para validar que la direccion esta introducida correctamente
	public boolean validarDireccion(String direccion) {
		return direccion != null && direccion.length() > 2 && direccion.length() < 100;
	}
	
	//Sirve para validar que el email esta introducido correctamente
	public boolean validarEmail(String email) {
        String regex = "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(email);

        if (!matcher.matches() || email.isEmpty()) {
            return false;
        }

        return clienterepo.findByEmail(email).isEmpty();
	}
	
	//Sirve para validar que el telefono esta introducido correctamente
	public boolean validarTelefono(String telefono) {
		String regex = "^\\d{9}$";
	    Pattern pattern = Pattern.compile(regex);
	    Matcher matcher = pattern.matcher(telefono);

	    if (!matcher.matches()) {
	        return false;
	    }

	    return clienterepo.findByTelefono(telefono).isEmpty();
	}
}