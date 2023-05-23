package com.tws2.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tws2.models.Mensaje;
import com.tws2.models.Usuario;
import com.tws2.resource.IMensajeRepo;

@Service
public class MensajeService {

	@Autowired
	IMensajeRepo iConversacionRepo;

	public List<Mensaje> ListarMensajesByIdentifiacdor(Long idUserDestino, Long iduserRemitente) {
		return iConversacionRepo.FindMensajes(idUserDestino, iduserRemitente);
	}

	public void RegistrarMensaje(Mensaje conver) {
		iConversacionRepo.save(conver);
	}

	public Mensaje ObtenerConversacionById(Long id) {
		return iConversacionRepo.findById(id).orElse(null);
	}

	public void ActualizarMensaje(Mensaje conver) {
		iConversacionRepo.save(conver);
	}

	public void EliminarMensaje(Long id) {
		iConversacionRepo.deleteById(id);
	}
}
