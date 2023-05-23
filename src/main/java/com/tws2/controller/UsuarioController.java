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

import com.tws2.models.Usuario;
import com.tws2.service.UsuarioService;
import com.tws2.validator.ObtenerErrores;

@RestController
@CrossOrigin("*")
@RequestMapping("/usuario/")
public class UsuarioController {

	@Autowired
	ObtenerErrores obtenerErrores;

	@Autowired
	UsuarioService usuarioService;

	@PostMapping("registrar")
	public ResponseEntity<?> RegistrarUsuario(@Valid @RequestBody Usuario usuario, BindingResult result) {
		Map<String, Object> response = new HashMap<>();

		if (result.hasErrors()) {
			response.put("mensaje", "Existen uno o varios campos con errores.");
			response.put("error", obtenerErrores.Errores(result));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_ACCEPTABLE);
		}
		try {
			usuarioService.RegistrarUsuario(usuario);
			response.put("mensaje", "usuario registrado");
			return new ResponseEntity<Map<String, Object>>(HttpStatus.CREATED);
		} catch (DataAccessException e) {
			response.put("mensaje", "no registrado");
			response.put("error", e.getMostSpecificCause().getMessage());
			return new ResponseEntity<Map<String, Object>>(HttpStatus.BAD_REQUEST);
		}

	}

	@GetMapping("obtener-usuario/{userId}")
	public ResponseEntity<?> obtenerUsuarioByID(@PathVariable Long userId) {
		Map<String, Object> response = new HashMap<>();
		try {
			Usuario usuario = usuarioService.ObtenerUsuarioById(userId);
			if (usuario == null) {
				response.put("mensaje", "usuario no encontrado");
				response.put("content", usuario);
				return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);
			}
			response.put("mensaje", "usuario obtenido");
			response.put("content", usuario);
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.OK);
		} catch (DataAccessException e) {
			response.put("mensaje", "usuario no obtenido");
			response.put("error", e.getMostSpecificCause().getMessage());
			response.put("content", new ArrayList<>());
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.BAD_REQUEST);
		}
	}

	@GetMapping("obtener-usuarios/{id}")
	public ResponseEntity<?> obtenerUsuarios(@PathVariable Long id) {
		Map<String, Object> response = new HashMap<>();
		try {
			List<Usuario> usuarios = usuarioService.ListaUsuarios(id);
			response.put("mensaje", "Lista obtenida");
			response.put("content", usuarios);
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.OK);
		} catch (DataAccessException e) {
			response.put("mensaje", "lista no obtenida");
			response.put("error", e.getMostSpecificCause().getMessage());
			response.put("content", new ArrayList<>());
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.BAD_REQUEST);
		}
	}

	@PutMapping("actualizar")
	public ResponseEntity<?> actualizarUsuario(@Valid @RequestBody Usuario usuario, BindingResult result) {
		Map<String, Object> response = new HashMap<>();

		if (result.hasErrors()) {
			response.put("mensaje", "Existen uno o varios campos con errores.");
			response.put("error", obtenerErrores.Errores(result));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_ACCEPTABLE);
		}
		try {
			Usuario user=usuarioService.ActualizarUsuario(usuario);
			response.put("mensaje", "usuario actualizado");
			response.put("content", user);
			return new ResponseEntity<Map<String, Object>>(HttpStatus.CREATED);
		} catch (DataAccessException e) {
			response.put("mensaje", "no actualizado");
			response.put("error", e.getMostSpecificCause().getMessage());
			return new ResponseEntity<Map<String, Object>>(HttpStatus.BAD_REQUEST);
		}
	}

	@DeleteMapping("eliminar-usuario/{userId}")
	public ResponseEntity<?> eliminarUsuario(@PathVariable Long userId) {
		Map<String, Object> response = new HashMap<>();
		try {
			usuarioService.EliminarUsuario(userId);
			response.put("mensaje", "Usuario eliminado");
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.OK);
		} catch (DataAccessException e) {
			response.put("mensaje", "usuario no eliminado");
			response.put("error", e.getMostSpecificCause().getMessage());
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.BAD_REQUEST);
		}
	}

}
