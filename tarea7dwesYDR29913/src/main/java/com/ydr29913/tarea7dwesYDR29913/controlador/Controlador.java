package com.ydr29913.tarea7dwesYDR29913.controlador;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.ydr29913.tarea7dwesYDR29913.modelo.Credenciales;
import com.ydr29913.tarea7dwesYDR29913.modelo.Ejemplar;
import com.ydr29913.tarea7dwesYDR29913.modelo.Mensaje;
import com.ydr29913.tarea7dwesYDR29913.modelo.Persona;
import com.ydr29913.tarea7dwesYDR29913.modelo.Planta;
import com.ydr29913.tarea7dwesYDR29913.servicios.ServiciosCredenciales;
import com.ydr29913.tarea7dwesYDR29913.servicios.ServiciosEjemplar;
import com.ydr29913.tarea7dwesYDR29913.servicios.ServiciosMensaje;
import com.ydr29913.tarea7dwesYDR29913.servicios.ServiciosPersona;
import com.ydr29913.tarea7dwesYDR29913.servicios.ServiciosPlanta;

@Controller
public class Controlador {
	
	@Autowired
	private ServiciosPlanta servplanta;
	
	@Autowired
	private ServiciosCredenciales servcredenciales;
	
	@Autowired
	private ServiciosPersona servpersona;
	
	@Autowired
	private ServiciosEjemplar servejemplar;
	
	@Autowired
	private ServiciosMensaje servmensaje;
	

	
	//Controlador para ver la pagina del Index
	@GetMapping({"/", "/mostrarIndex"})
	public String mostrarIndex() {
		return "index";
	}
	
	
	//Metodo para ver la pagina de las Plantas
	@GetMapping("/mostrarVerPlantas")
    public String mostrarVerPlantas(Model model) {
        List<Planta> plantas = servplanta.obtenerPlantasOrdenadasAlfabeticamente();
        model.addAttribute("plantas", plantas);
        return "ver-plantas";
    }
	
	
	//Controlador para ver la pagina del Login
	@GetMapping("/mostrarLogin")
	public String mostrarLogin() {
		return "login";
	}
	
	//Metodo para verificar las credenciales y mandarte a una pagina en funcion del perfil del usuario autenticado
	@PostMapping("/login")
    public String login(@RequestParam String usuario, @RequestParam String contraseña, Model model) {
        Credenciales credenciales = servcredenciales.autenticarUsuario(usuario, contraseña);
        if (credenciales != null) {
            if ("Admin".equals(credenciales.getPerfil())) {
                return "redirect:/mostrarMenuAdmin";
            } else if ("Personal".equals(credenciales.getPerfil())) {
                return "redirect:/mostrarMenuPersonal";
            }
        } 
        model.addAttribute("error", "Usuario o contraseña incorrectos.");
        return "login";
    }
	
	
	//Metodo para ver la pagina del Menu del Admin
	@GetMapping("/mostrarMenuAdmin")
    public String mostrarMenuAdmin(Model model) {
        List<Planta> plantas = servplanta.obtenerPlantasOrdenadasAlfabeticamente();
        model.addAttribute("plantas", plantas);
        return "menu-admin";
    }
	
	
	//Metodo para ver la pagina del Menu del Personal
	@GetMapping("/mostrarMenuPersonal")
    public String mostrarMenuPersonal(Model model) {
        List<Planta> plantas = servplanta.obtenerPlantasOrdenadasAlfabeticamente();
        model.addAttribute("plantas", plantas);
        return "menu-personal";
    }
	
	
	//Metodo para ver la pagina del menu de las personas
	@GetMapping("/mostrarMenuPersona")
    public String mostrarMenuPersona(Model model) {
        List<Planta> plantas = servplanta.obtenerPlantasOrdenadasAlfabeticamente();
        model.addAttribute("plantas", plantas);
        return "menu-persona";
    }
	
	//Metodo para registrar una nueva persona con el perfil de "Personal"
	@PostMapping("/registrarPersona")
    public String registrarPersona(@RequestParam String nombre, @RequestParam String correo, @RequestParam String usuario, @RequestParam String contraseña, Model model) {
        
		if (!servpersona.validarNombre(nombre)) {
            model.addAttribute("errorNombre", "El nombre ya está registrado o es incorrecto.");
            return "registrar-persona";
        }
		
		if (!servpersona.validarEmail(correo)) {
            model.addAttribute("errorEmail", "El correo ya está registrado o es incorrecto.");
            return "registrar-persona";
        }
        
        if (!servcredenciales.validarUsuario(usuario)) {
            model.addAttribute("errorUsuario", "El nombre de usuario no está disponible.");
            return "registrar-persona";
        }
        
        if (!servcredenciales.validarPassword(contraseña)) {
            model.addAttribute("errorPassword", "La contraseña no es válida.");
            return "registrar-persona";
        }

        Persona persona = new Persona();
        persona.setNombre(nombre);
        persona.setEmail(correo);
        servpersona.insertarPersona(persona);

        Credenciales credenciales = new Credenciales();
        credenciales.setUsuario(usuario);
        credenciales.setPassword(contraseña);
        credenciales.setPerfil("Personal");
        servcredenciales.insertarCredenciales(credenciales);

        model.addAttribute("mensajeExito", "Persona registrada correctamente.");
        return "redirect:/mostrarMenuAdmin";
    }
	
	
	
	//Metodo para  ver la pagina del menu de las plantas
	@GetMapping("/mostrarMenuPlanta")
    public String mostrarMenuPlanta(Model model) {
        List<Planta> plantas = servplanta.obtenerPlantasOrdenadasAlfabeticamente();
        model.addAttribute("plantas", plantas);
        return "menu-planta";
    }
	
	//Metodo para insertar una nueva planta en la base de datos
	@PostMapping("/insertarPlanta")
	public String insertarPlanta(@RequestParam String codigo, @RequestParam String nombreComun, @RequestParam String nombreCientifico, Model model) {
	    if (servplanta.existePlantaPorCodigo(codigo)) {
	        model.addAttribute("error", "Ya existe una planta con ese código.");
	        return "menu-admin";
	    }
	    
	    Planta nuevaPlanta = new Planta();
	    nuevaPlanta.setCodigo(codigo);
	    nuevaPlanta.setNombreComun(nombreComun);
	    nuevaPlanta.setNombreCientifico(nombreCientifico);
	    servplanta.insertarPlanta(nuevaPlanta);
	    
	    model.addAttribute("mensajeExito", "Planta registrada correctamente.");
	    return "redirect:/mostrarMenuAdmin";
	}
	
	
	//Metodo para modificar una planta de la base de datos
	@PostMapping("/modificarPlanta")
    public String modificarPlanta(@RequestParam String codigo, @RequestParam(required = false) String nuevoNombreComun, @RequestParam(required = false) String nuevoNombreCientifico, Model model) {
        Planta planta = servplanta.obtenerPlantaPorCodigo(codigo);
        if (planta == null) {
            model.addAttribute("error", "No se encontró una planta con ese código.");
            return "menu-admin";
        }
        if (nuevoNombreComun != null && !nuevoNombreComun.trim().isEmpty()) {
            planta.setNombreComun(nuevoNombreComun);
        }
        if (nuevoNombreCientifico != null && !nuevoNombreCientifico.trim().isEmpty()) {
            planta.setNombreCientifico(nuevoNombreCientifico);
        }
        
        servplanta.modificarPlanta(planta);
        model.addAttribute("mensajeExito", "Planta modificada correctamente.");
        return "redirect:/mostrarMenuAdmin";
    }
	
	
	
	//Metodo para ver la pagina de la gestion de ejemplares
	@GetMapping("/mostrarGestionEjemplares")
    public String mostrarGestionEjemplares(Model model) {
        List<Planta> plantas = servplanta.obtenerPlantasOrdenadasAlfabeticamente();
        List<Ejemplar> ejemplares = servejemplar.obtenerTodosLosEjemplares();
        List<Mensaje> mensajes = servmensaje.obtenerTodosLosMensajes();
        
        model.addAttribute("plantas", plantas);
        model.addAttribute("ejemplares", ejemplares);
        model.addAttribute("mensajes", mensajes);
        
        return "gestion-ejemplares";
    }
	
	//Metodo para registrar un nuevo ejemplar en la base de datos
	@PostMapping("/insertarEjemplar")
    public String insertarEjemplar(@RequestParam Long id, @RequestParam String nombre, @RequestParam String mensaje, Model model) {
        Planta planta = servplanta.obtenerPlantaPorId(id);
        if (planta == null) {
            model.addAttribute("error", "Planta no encontrada.");
            return "gestion-ejemplares";
        }
        
        Ejemplar ejemplar = new Ejemplar();
        ejemplar.setNombre(nombre);
        ejemplar.setPlanta(planta);
        servejemplar.insertarEjemplar(ejemplar);
        
        Mensaje nuevoMensaje = new Mensaje();
        nuevoMensaje.setEjemplar(ejemplar);
        nuevoMensaje.setMensaje("Ejemplar registrado con mensaje: " + mensaje);
        servmensaje.insertarMensaje(nuevoMensaje);
        
        model.addAttribute("mensajeExito", "Ejemplar registrado correctamente.");
        return "redirect:/mostrarGestionEjemplares";
    }
	
	//Metodo para filtrar los ejemplares registrados en la base de datos
	@PostMapping("/filtrarEjemplares")
    public String filtrarEjemplares(@RequestParam Long plantaId, Model model) {
        List<Ejemplar> ejemplares = servejemplar.obtenerEjemplaresPorPlanta(plantaId);
        List<Planta> plantas = servplanta.obtenerPlantasOrdenadasAlfabeticamente();
        List<Mensaje> mensajes = servmensaje.obtenerMensajesPorPlanta(plantaId);
        
        model.addAttribute("plantas", plantas);
        model.addAttribute("ejemplares", ejemplares);
        model.addAttribute("mensajes", mensajes);
        
        return "gestion-ejemplares";
    }
	
	//Metodo para mostrar la pagina de los mensajes de seguimiento
	@GetMapping("/mostrarMenuMensajesSeguimiento")
    public String mostrarMenuMensajesSeguimiento(Model model) {
		List<Planta> plantas = servplanta.obtenerPlantasOrdenadasAlfabeticamente();
        List<Ejemplar> ejemplares = servejemplar.obtenerTodosLosEjemplares();
        List<Mensaje> mensajes = servmensaje.obtenerTodosLosMensajes();
        
        model.addAttribute("plantas", plantas);
        model.addAttribute("ejemplares", ejemplares);
        model.addAttribute("mensajes", mensajes);
        
        return "ver-mensajes-seguimiento";
    }
	
	
	
	//Metodo para mostrar la pagina de la gestion de los mensajes
	@GetMapping("/mostrarGestionMensajes")
	public String mostrarGestionMensajes(Model model) {
		List<Ejemplar> ejemplares = servejemplar.obtenerTodosLosEjemplares();
	    model.addAttribute("ejemplares", ejemplares);
		return "gestion-mensajes";
	}
	
	//Metodo para registrar un mensaje en la base de datos
	@PostMapping("/añadirMensaje")
    public String añadirMensaje(@RequestParam Long ejemplarId, @RequestParam String mensaje, Model model) {
        Ejemplar ejemplar = servejemplar.obtenerEjemplarPorId(ejemplarId);
        
        if (ejemplar == null) {
            model.addAttribute("error", "El ejemplar seleccionado no existe.");
            return "gestion-mensajes";
        }

        Mensaje nuevoMensaje = new Mensaje();
        nuevoMensaje.setEjemplar(ejemplar);
        nuevoMensaje.setMensaje(mensaje);
        servmensaje.insertarMensaje(nuevoMensaje);

        model.addAttribute("mensajeExito", "Mensaje añadido correctamente.");
        return "redirect:/mostrarGestionMensajes";
    }
	
	//Metodo para filtrar los mensajes de el ejemplar seleccionado
	@PostMapping("/filtrarMensajes")
	public String filtrarMensajes(@RequestParam Long idEjemplar, Model model) {
		Ejemplar ejemplar = servejemplar.obtenerEjemplarPorId(idEjemplar);
		if (ejemplar == null) {
		    model.addAttribute("error", "Ejemplar no encontrado.");
		    return "gestion-mensajes";
		}
	    
	    List<Mensaje> mensajes = servmensaje.obtenerMensajesPorEjemplar(ejemplar);
	    
	    model.addAttribute("mensajes", mensajes);
	    model.addAttribute("ejemplar", ejemplar);
	    return "redirect:/mostrarGestionMensajes";
	}
}