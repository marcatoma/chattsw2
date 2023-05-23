package com.tws2.resource;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.tws2.models.Usuario;

public interface IUsuarioRepo extends JpaRepository<Usuario, Long> {

	@Query(value = "select * from usuario u where id_usuario !=:id", nativeQuery = true)
	public List<Usuario> obtenerUsarios(Long id);
}
