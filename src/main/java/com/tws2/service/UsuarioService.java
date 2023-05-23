package com.tws2.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tws2.models.Usuario;
import com.tws2.resource.IUsuarioRepo;

@Service
public class UsuarioService {

	
	@Autowired IUsuarioRepo usuarioRepo;
	
	public List<Usuario> ListaUsuarios(Long id){
		return usuarioRepo.obtenerUsarios(id);
	}
	
	public void RegistrarUsuario(Usuario usuario) {
		 usuarioRepo.save(usuario);
	}
	
	public Usuario ObtenerUsuarioById(Long id) {
		return usuarioRepo.findById(id).orElse(null);
	}
	
	public Usuario ActualizarUsuario(Usuario usuario) {
		return usuarioRepo.save(usuario);
	}
	public void EliminarUsuario(Long id) {
		usuarioRepo.deleteById(id);
	}
	
}
