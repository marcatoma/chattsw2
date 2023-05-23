package com.tws2.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.tws2.models.Mensaje;
import com.tws2.models.Usuario;
import com.tws2.service.MensajeService;
import com.tws2.service.UsuarioService;
import com.tws2.validator.ObtenerErrores;

@RestController
@CrossOrigin("*")
@RequestMapping("/conversacion/")
public class TConversacionController {

	@Autowired
	ObtenerErrores obtenerErrores;

	@Autowired

	MensajeService mensajeService;

	@Autowired
	UsuarioService usuarioService;

	@PostMapping("registrar-mensaje")
	public ResponseEntity<?> RegistrarMensaje(@Valid @RequestBody Mensaje mensaje, BindingResult result) {
		Map<String, Object> response = new HashMap<>();
		// validar si existen errores 
		if (result.hasErrors()) {
			response.put("mensaje", "Existen uno o varios campos con errores.");
			response.put("error", obtenerErrores.Errores(result));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_ACCEPTABLE);
		}
		// pasar por un filtro de error al hacer el proceso de guardado
		try {
			mensajeService.RegistrarMensaje(mensaje);
			response.put("mensaje", "mensaje registrado");
			return new ResponseEntity<Map<String, Object>>(HttpStatus.CREATED);
		} catch (DataAccessException e) {
			//retornar excepcion en caso de error
			response.put("mensaje", "no registrado");
			response.put("error", e.getMostSpecificCause().getMessage());
			return new ResponseEntity<Map<String, Object>>(HttpStatus.BAD_REQUEST);
		}

	}
	// metodo para filtrar mensajes de los usuarios 
	@GetMapping("obtener-mensajes/{userIdDestino}/id-origen/{idUserOrigen}")
	public ResponseEntity<?> obtenerMensajesByID(@PathVariable Long userIdDestino, @PathVariable Long idUserOrigen) {
		Map<String, Object> response = new HashMap<>();
		// verificar existencia de los usuarios consultados
		try {
			Usuario userOrigen = usuarioService.ObtenerUsuarioById(idUserOrigen);
			Usuario userDestino = usuarioService.ObtenerUsuarioById(userIdDestino);
			if (userOrigen == null || userDestino == null) {
				response.put("mensaje", "mensajes no encontrados");
				response.put("content", null);
				return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);
			}
			//filtrar mensajes consulktando al servicio
			List<Mensaje> mensajes = mensajeService.ListarMensajesByIdentifiacdor(userIdDestino, idUserOrigen);
			if (mensajes == null) {
				response.put("mensaje", "usuario no encontrado");
				response.put("content", new ArrayList<>());
				return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);
			}
			response.put("mensaje", "mensajes obtenidos");
			response.put("content", mensajes);
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.OK);
		} catch (DataAccessException e) {
			//retornar eerores en caso de existir
			response.put("mensaje", "mensajes no obtenidos");
			response.put("error", e.getMostSpecificCause().getMessage());
			response.put("content", new ArrayList<>());
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.BAD_REQUEST);
		}
	}

	@PutMapping("actualizar")
	public ResponseEntity<?> actualizarMensaje(@Valid @RequestBody Mensaje mensaje, BindingResult result) {
		return RegistrarMensaje(mensaje, result);
	}

	@DeleteMapping("eliminar-mensaje/{mensajeId}")
	public ResponseEntity<?> eliminarUsuario(@PathVariable Long mensajeId) {
		Map<String, Object> response = new HashMap<>();
		try {
			mensajeService.EliminarMensaje(mensajeId);
			response.put("mensaje", "mensaje eliminado");
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.OK);
		} catch (DataAccessException e) {
			response.put("mensaje", "mensaje no eliminado");
			response.put("error", e.getMostSpecificCause().getMessage());
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.BAD_REQUEST);
		}
	}

}
