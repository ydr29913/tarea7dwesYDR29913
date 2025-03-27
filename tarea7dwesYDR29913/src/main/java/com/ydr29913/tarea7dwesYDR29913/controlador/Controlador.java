package com.ydr29913.tarea7dwesYDR29913.controlador;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.ydr29913.tarea7dwesYDR29913.modelo.Cliente;
import com.ydr29913.tarea7dwesYDR29913.modelo.Credenciales;
import com.ydr29913.tarea7dwesYDR29913.modelo.Ejemplar;
import com.ydr29913.tarea7dwesYDR29913.modelo.Mensaje;
import com.ydr29913.tarea7dwesYDR29913.modelo.Persona;
import com.ydr29913.tarea7dwesYDR29913.modelo.Planta;
import com.ydr29913.tarea7dwesYDR29913.servicios.ServiciosCliente;
import com.ydr29913.tarea7dwesYDR29913.servicios.ServiciosCredenciales;
import com.ydr29913.tarea7dwesYDR29913.servicios.ServiciosEjemplar;
import com.ydr29913.tarea7dwesYDR29913.servicios.ServiciosMensaje;
import com.ydr29913.tarea7dwesYDR29913.servicios.ServiciosPedido;
import com.ydr29913.tarea7dwesYDR29913.servicios.ServiciosPersona;
import com.ydr29913.tarea7dwesYDR29913.servicios.ServiciosPlanta;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

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
	private ServiciosCliente servcliente;
	
	@Autowired
	private ServiciosPedido servpedido;
	
	@Autowired
	private ServiciosMensaje servmensaje;
	private Credenciales usuarioAutenticado;
	
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
	public String login(@RequestParam String usuario, @RequestParam String contraseña, Model model, HttpServletRequest request) {
	    Credenciales credenciales = servcredenciales.autenticarUsuario(usuario, contraseña);
	    if (credenciales != null) {
	        //Session dura 6 horas máximo
	        HttpSession session = request.getSession();
	        session.setMaxInactiveInterval(6 * 60 * 60);
	        session.setAttribute("carrito", new java.util.ArrayList<>());
	        
	        usuarioAutenticado = credenciales;
	        Persona persona = credenciales.getPersona();
	        
	        if ("Admin".equals(credenciales.getPerfil())) {
	            return "redirect:/mostrarMenuAdmin";
	        } if ("Personal".equals(credenciales.getPerfil())) {
	            return "redirect:/mostrarMenuPersonal";
	        } if ("Cliente".equals(credenciales.getPerfil())) {
	            return "redirect:/mostrarMenuCliente";
	        }
	    }
	    model.addAttribute("error", "Usuario o contraseña incorrectos.");
	    return "login";
	}
//	@PostMapping("/login")
//    public String login(@RequestParam String usuario, @RequestParam String contraseña, Model model) {
//        Credenciales credenciales = servcredenciales.autenticarUsuario(usuario, contraseña);
//        if (credenciales != null) {
//        	usuarioAutenticado = credenciales;
//			Persona persona = credenciales.getPersona();
//			
//            if ("Admin".equals(credenciales.getPerfil())) {
//                return "redirect:/mostrarMenuAdmin";
//            } if ("Personal".equals(credenciales.getPerfil())) {
//                return "redirect:/mostrarMenuPersonal";
//            } if ("Cliente".equals(credenciales.getPerfil())) {
//	            return "redirect:/mostrarMenuCliente";
//	        }
//        } 
//        model.addAttribute("error", "Usuario o contraseña incorrectos.");
//        return "login";
//    }
	
	@GetMapping("/logout")
	public String logout(HttpServletRequest request) {
	    HttpSession session = request.getSession(false);
	    if (session != null) {
	        session.invalidate();
	    }
	    return "redirect:/mostrarIndex";
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
            return "menu-persona";
        }
		
		if (!servpersona.validarEmail(correo)) {
            model.addAttribute("errorEmail", "El correo ya está registrado o es incorrecto.");
            return "menu-persona";
        }
        
        if (!servcredenciales.validarUsuario(usuario)) {
            model.addAttribute("errorUsuario", "El nombre de usuario no está disponible.");
            return "menu-persona";
        }
        
        if (!servcredenciales.validarPassword(contraseña)) {
            model.addAttribute("errorPassword", "La contraseña no es válida.");
            return "menu-persona";
        }

        Persona persona = new Persona();
        persona.setNombre(nombre);
        persona.setEmail(correo);
        servpersona.insertarPersona(persona);

        Credenciales credenciales = new Credenciales();
        credenciales.setUsuario(usuario);
        credenciales.setPassword(contraseña);
        credenciales.setPerfil("Personal");
        credenciales.setPersona(persona);
        servcredenciales.insertarCredenciales(credenciales);

        model.addAttribute("mensajeExito", "Persona registrada correctamente.");
        return "redirect:/mostrarMenuPersona";
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
	        return "menu-planta";
	    }
	    if (!servplanta.validarCodigo(codigo)) {
	        model.addAttribute("error", "El código está introducido incorrectamente. No debe contener espacios.");
	        return "menu-planta";
	    }
	    
	    Planta nuevaPlanta = new Planta();
	    nuevaPlanta.setCodigo(codigo);
	    nuevaPlanta.setNombreComun(nombreComun);
	    nuevaPlanta.setNombreCientifico(nombreCientifico);
	    servplanta.insertarPlanta(nuevaPlanta);
	    
	    model.addAttribute("mensajeExito", "Planta registrada correctamente.");
	    return "redirect:/mostrarMenuPlanta";
	}
	
	
	//Metodo para modificar una planta de la base de datos
	@PostMapping("/modificarPlanta")
    public String modificarPlanta(@RequestParam String codigo, @RequestParam(required = false) String nuevoNombreComun, @RequestParam(required = false) String nuevoNombreCientifico, Model model) {
		List<Planta> plantas = servplanta.obtenerPlantasOrdenadasAlfabeticamente();
	    model.addAttribute("plantas", plantas);
		
		Planta planta = servplanta.obtenerPlantaPorCodigo(codigo);
        if (planta == null) {
            model.addAttribute("error", "No se encontró una planta con ese código.");
            return "menu-planta";
        }
        if (nuevoNombreComun != null && !nuevoNombreComun.trim().isEmpty()) {
            planta.setNombreComun(nuevoNombreComun);
        }
        if (nuevoNombreCientifico != null && !nuevoNombreCientifico.trim().isEmpty()) {
            planta.setNombreCientifico(nuevoNombreCientifico);
        }
        
        servplanta.modificarPlanta(planta);
        model.addAttribute("mensajeExito", "Planta modificada correctamente.");
        return "redirect:/mostrarMenuPlanta";
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
	public String insertarEjemplar(@RequestParam Long id, Long persona_id, Model model) {
	    Planta planta = servplanta.obtenerPlantaPorId(id);
	    if (planta == null) {
	        model.addAttribute("error", "Planta no encontrada.");
	        return "gestion-ejemplares";
	    }

	    //Pruebas
	    if (usuarioAutenticado == null || usuarioAutenticado.getPersona() == null) {
	        System.out.println("Error: No hay un usuario autenticado.");
	    }

	    Persona persona = usuarioAutenticado.getPersona();
	    
	    
	    long contadorEjemplares = servejemplar.ultimoIdEjemplarByPlanta(planta) + 1;
	    String nombreEjemplar = planta.getCodigo() + "-" + contadorEjemplares;

	    Ejemplar ejemplar = new Ejemplar();
	    ejemplar.setNombre(nombreEjemplar);
	    ejemplar.setPlanta(planta);
	    servejemplar.insertarEjemplar(ejemplar);    
	    
	    Mensaje nuevoMensaje = new Mensaje();
	    nuevoMensaje.setEjemplar(ejemplar);
	    nuevoMensaje.setPlanta(planta);
	    nuevoMensaje.setPersona(persona);
	    nuevoMensaje.setMensaje("Añadido ejemplar " + nombreEjemplar);

	    SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
	    try {
	        Date fecha = sdf.parse(sdf.format(new Date()));
	        nuevoMensaje.setFechaHora(fecha);
	    } catch (ParseException e) {
	        e.printStackTrace();
	    }
	    
	    servmensaje.insertarMensaje(nuevoMensaje);

	    model.addAttribute("mensajeExito", "Ejemplar registrado correctamente.");
	    return "redirect:/mostrarGestionEjemplares";
	}

	
	@PostMapping("/filtrarEjemplares")
	public String filtrarEjemplares(@RequestParam("id") Long plantaId, Model model) {
	    List<Planta> plantas = servplanta.obtenerPlantasOrdenadasAlfabeticamente();
	    model.addAttribute("plantas", plantas);
	    
	    Planta planta = servplanta.obtenerPlantaPorId(plantaId);
	    if (planta == null) {
	        model.addAttribute("error", "Planta no encontrada.");
	        model.addAttribute("ejemplares", null);
	    } else {
	        List<Ejemplar> ejemplares = servejemplar.obtenerEjemplaresPorPlanta(plantaId);
	        model.addAttribute("ejemplares", ejemplares);
	        model.addAttribute("plantaSeleccionada", planta);
	    }
	    
	    return "gestion-ejemplares";
	}
	
//	@PostMapping("/filtrarEjemplares")
//	public String filtrarEjemplares(@RequestParam("id[]") List<Long> plantaIds, Model model) {
//	    List<Planta> plantas = servplanta.obtenerPlantasOrdenadasAlfabeticamente();
//	    model.addAttribute("plantas", plantas);
//
//	    if (plantaIds == null || plantaIds.isEmpty()) {
//	        model.addAttribute("error", "Debe seleccionar al menos una planta.");
//	        model.addAttribute("ejemplares", null);
//	    } else {
//	        List<Ejemplar> ejemplaresFiltrados = new ArrayList<>();
//	        for (Long plantaId : plantaIds) {
//	            List<Ejemplar> ejemplares = servejemplar.obtenerEjemplaresPorPlanta(plantaId);
//	            ejemplaresFiltrados.addAll(ejemplares);
//	        }
//	        model.addAttribute("ejemplares", ejemplaresFiltrados);
//	    }
//
//	    return "gestion-ejemplares";
//	}
	
	
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
	
	//Metodo para filtrar los mensajes de el ejemplar seleccionado
	@PostMapping("/filtrarMensajes")
    public String filtrarMensajes(@RequestParam("filtroEjemplar") Long idEjemplar, Model model) {
        List<Ejemplar> ejemplares = servejemplar.obtenerTodosLosEjemplares();
        model.addAttribute("ejemplares", ejemplares);
        
        Ejemplar ejemplar = servejemplar.obtenerEjemplarPorId(idEjemplar);
        if (ejemplar == null) {
            model.addAttribute("error", "Ejemplar no encontrado.");
            model.addAttribute("mensajes", null);
        } else {
            List<Mensaje> mensajes = servmensaje.obtenerMensajesPorEjemplar(ejemplar);
            model.addAttribute("mensajes", mensajes);
            model.addAttribute("ejemplar", ejemplar);
        }
        
        return "ver-mensajes-seguimiento";
    }
	
	
	
	//Metodo para mostrar la pagina de la gestion de los mensajes
	@GetMapping("/mostrarGestionMensajes")
	public String mostrarGestionMensajes(Model model) {
		List<Ejemplar> ejemplares = servejemplar.obtenerTodosLosEjemplares();
	    model.addAttribute("ejemplares", ejemplares);
	    List<Mensaje> mensajes = servmensaje.obtenerTodosLosMensajes();
	    model.addAttribute("mensajes", mensajes);
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
       
        //Prueba
        if (usuarioAutenticado == null || usuarioAutenticado.getPersona() == null) {
	        System.out.println("Error: No hay un usuario autenticado.");
	    }

	    Persona persona = usuarioAutenticado.getPersona();
       
        
        Planta planta = servejemplar.obtenerEjemplarPorId(ejemplarId).getPlanta();
        
        Mensaje nuevoMensaje = new Mensaje();
        nuevoMensaje.setEjemplar(ejemplar);
        nuevoMensaje.setMensaje(mensaje);
        nuevoMensaje.setPlanta(planta);
        nuevoMensaje.setPersona(persona);
        
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
	    try {
	        Date fecha = sdf.parse(sdf.format(new Date()));
	        nuevoMensaje.setFechaHora(fecha);
	    } catch (ParseException e) {
	        e.printStackTrace();
	    }
        
        servmensaje.insertarMensaje(nuevoMensaje);

        model.addAttribute("mensajeExito", "Mensaje añadido correctamente.");
        return "redirect:/mostrarGestionMensajes";
    }
	
	
	//Metodo para mostrar la pagina para filtrar los mensajes
	@GetMapping("/mostrarMenuFiltrarMensajes")
	public String mostrarMenuFiltrarMensajes(Model model) {
		List<Planta> plantas = servplanta.obtenerPlantasOrdenadasAlfabeticamente();
		model.addAttribute("plantas", plantas);
		List<Ejemplar> ejemplares = servejemplar.obtenerTodosLosEjemplares();
	    model.addAttribute("ejemplares", ejemplares);
	    List<Mensaje> mensajes = servmensaje.obtenerTodosLosMensajes();
	    model.addAttribute("mensajes", mensajes);
		return "menu-filtrar-mensajes";
	}
	
	@GetMapping("/mostrarVerFiltrarPlanta")
	public String mostrarVerFiltrarPlanta(Model model) {
		List<Planta> plantas = servplanta.obtenerPlantasOrdenadasAlfabeticamente();
		model.addAttribute("plantas", plantas);
		List<Ejemplar> ejemplares = servejemplar.obtenerTodosLosEjemplares();
	    model.addAttribute("ejemplares", ejemplares);
	    List<Mensaje> mensajes = servmensaje.obtenerTodosLosMensajes();
	    model.addAttribute("mensajes", mensajes);
		return "ver-filtrar-planta";
	}
	
	@GetMapping("/mostrarVerFiltrarFecha")
	public String mostrarVerFiltrarFecha(Model model) {
		List<Planta> plantas = servplanta.obtenerPlantasOrdenadasAlfabeticamente();
		model.addAttribute("plantas", plantas);
		List<Ejemplar> ejemplares = servejemplar.obtenerTodosLosEjemplares();
	    model.addAttribute("ejemplares", ejemplares);
	    List<Mensaje> mensajes = servmensaje.obtenerTodosLosMensajes();
	    model.addAttribute("mensajes", mensajes);
		return "ver-filtrar-fecha";
	}
	
	@GetMapping("/mostrarVerFiltrarPersona")
	public String mostrarVerFiltrarPersona(Model model) {
		List<Planta> plantas = servplanta.obtenerPlantasOrdenadasAlfabeticamente();
		model.addAttribute("plantas", plantas);
		List<Ejemplar> ejemplares = servejemplar.obtenerTodosLosEjemplares();
	    model.addAttribute("ejemplares", ejemplares);
	    List<Persona> personas = servpersona.obtenerTodasPersonas();
	    model.addAttribute("personas", personas);
	    List<Mensaje> mensajes = servmensaje.obtenerTodosLosMensajes();
	    model.addAttribute("mensajes", mensajes);
		return "ver-filtrar-persona";
	}
	

	@PostMapping("/filtrarMensajesPorFecha")
	public String filtrarMensajesPorFecha(@RequestParam("fechaInicio") String fechaInicioStr, @RequestParam("fechaFin") String fechaFinStr, Model model) {
	    SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
	    Date fechaInicio = null;
	    Date fechaFin = null;
	    
	    try {
	        fechaInicio = sdf.parse(fechaInicioStr);
	        fechaFin = sdf.parse(fechaFinStr);
	    } catch (ParseException e) {
	        model.addAttribute("error", "Formato de fecha inválido. Por favor, usa el formato dd/MM/yyyy.");
	        return "ver-filtrar-fecha";
	    }

	    List<Mensaje> mensajes = servmensaje.obtenerMensajesPorFecha(fechaInicio, fechaFin);

	    if (fechaInicio == null) {
	        model.addAttribute("error", "Fecha no encontrada.");
	        model.addAttribute("mensajes", null);
	    } else {
	        model.addAttribute("mensajes", mensajes);
	    }
	    return "ver-filtrar-fecha";
	}
	
	
	@PostMapping("/filtrarMensajesPorPlanta")
	public String filtrarMensajesPorPlanta(@RequestParam("filtroPlanta") Long idPlanta, Model model) {
	    List<Planta> plantas = servplanta.obtenerPlantasOrdenadasAlfabeticamente();
	    model.addAttribute("plantas", plantas);

	    Planta planta = servplanta.obtenerPlantaPorId(idPlanta);
	    if (planta == null) {
	        model.addAttribute("error", "Planta no encontrada.");
	        model.addAttribute("mensajes", null);
	    } else {
	        List<Mensaje> mensajes = servmensaje.obtenerMensajesPorPlantaSeleccionada(planta);
	        model.addAttribute("mensajes", mensajes);
	        model.addAttribute("planta", planta);
	    }

	    return "ver-filtrar-planta";
	}
	
	
	@PostMapping("/filtrarMensajesPorPersona")
	public String filtrarMensajesPorPersona(@RequestParam("filtroPersona") Long persona_id, Model model) {
	    List<Persona> personas = servpersona.obtenerTodasPersonas();
	    model.addAttribute("personas", personas);

	    Persona persona = servpersona.obtenerPersonaPorId(persona_id);
	    if (persona == null) {
	    	model.addAttribute("error", "Persona no encontrada");
	    	model.addAttribute("mensajes", null);
	    } else {
	        List<Mensaje> mensajes = servmensaje.obtenerMensajesPorPersonaSeleccionada(persona);
	        model.addAttribute("mensajes", mensajes);
	        model.addAttribute("persona", persona);
	    }

	    return "ver-filtrar-persona";
	}
	
	
	
	
	//Metodo para ver la pagina de registro de clientes
	@GetMapping("/mostrarRegistrarCliente")
	public String mostrarRegistrarCliente(Model model) {
		return "registrar-cliente";
	}
	
	
	//Metodo para registrar un cliente con el perfil de "Cliente"
	@PostMapping("/registrarCliente")
	public String registrarCliente(@RequestParam String nombre, @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") Date fechanac, @RequestParam String nifnie, @RequestParam String direccion, @RequestParam String telefono, @RequestParam String email, @RequestParam String usuario, @RequestParam String contraseña, Model model) {
	    
		if (!servcliente.validarNombre(nombre)) {
	        model.addAttribute("errorNombre", "El nombre ya está registrado o es incorrecto.");
	        return "registrar-cliente";
	    }
	    
	    if (!servcliente.validarFechaNacimiento(fechanac)) {
	        model.addAttribute("errorFecha", "La fecha de nacimiento es incorrecta.");
	        return "registrar-cliente";
	    }

	    /*if (!servcliente.validarNifNie(nifnie)) {
	        model.addAttribute("errorNifNie", "El NIF/NIE es incorrecto.");
	        return "registrar-cliente";
	    }*/
	    
	    if (!servcliente.validarDireccion(direccion)) {
	        model.addAttribute("errorDireccion", "La direccion es incorrecta.");
	        return "registrar-cliente";
	    }
	    
	    if (!servcliente.validarEmail(email)) {
	        model.addAttribute("errorEmail", "El correo ya está registrado o es incorrecto.");
	        return "registrar-cliente";
	    }
	    
	    if (!servcredenciales.validarUsuario(usuario)) {
	        model.addAttribute("errorUsuario", "El nombre de usuario no está disponible.");
	        return "registrar-cliente";
	    }
	    
	    if (!servcredenciales.validarPassword(contraseña)) {
	        model.addAttribute("errorPassword", "La contraseña no es válida.");
	        return "registrar-cliente";
	    }
	    
	    Persona persona = new Persona();
	    persona.setNombre(nombre);
	    persona.setEmail(email);
	    servpersona.insertarPersona(persona);
	    
	    Cliente cliente = new Cliente();
	    cliente.setNombre(nombre);
	    cliente.setFechanac(fechanac);
	    cliente.setNifnie(nifnie);
	    cliente.setDireccion(direccion);
	    cliente.setTelefono(telefono);
	    cliente.setEmail(email);
	    cliente.setPersona(persona);
	    servcliente.insertarCliente(cliente);

	    Credenciales credenciales = new Credenciales();
	    credenciales.setUsuario(usuario);
	    credenciales.setPassword(contraseña);
	    credenciales.setPerfil("Cliente");
	    credenciales.setPersona(persona);
	    credenciales.setCliente(cliente);
	    servcredenciales.insertarCredenciales(credenciales);

	    model.addAttribute("mensajeExito", "Cliente registrado correctamente.");
	    return "redirect:/mostrarIndex";
	}
	
	
	//Metodo para ver la pagina del Menu de los clientes
	@GetMapping("/mostrarMenuCliente")
	public String mostrarMenuCliente(Model model) {

		return "menu-cliente";
	}
	
	
	@GetMapping("/mostrarRealizarPedido")
    public String mostrarRealizarPedido(Model model, HttpSession session) {
        List<Planta> plantas = servplanta.obtenerPlantasOrdenadasAlfabeticamente();
        model.addAttribute("plantas", plantas);
        List<Ejemplar> ejemplares = servejemplar.obtenerTodosLosEjemplares();
        model.addAttribute("ejemplares", ejemplares);
        //model.addAttribute("carrito", servcarrito.obtenerCarrito(session));
        return "realizar-pedido";
    }
}