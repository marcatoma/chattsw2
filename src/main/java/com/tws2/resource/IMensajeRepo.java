package com.tws2.resource;

import java.util.List;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.tws2.models.Mensaje;
import com.tws2.models.Usuario;

public interface IMensajeRepo extends JpaRepository<Mensaje, Long>{

	public List<Mensaje> findByIdUserDestinoAndUsuario(Long idUserDestino, Usuario usuario);
	
	@Query(value="select * from mensaje where usuario_id_usuario =:emitente and id_user_destino=:destino or usuario_id_usuario =:destino  and id_user_destino=:emitente", nativeQuery = true)
	public List<Mensaje> FindMensajes(Long destino, Long emitente);
	
}
