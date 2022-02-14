package com.eventoapp.repository;

import org.springframework.data.repository.CrudRepository;

import com.eventoapp.model.Evento;
import com.eventoapp.model.Usuario;

public interface UsuarioRepository extends CrudRepository<Usuario, String>{
	
	Iterable<Usuario> findByEvento(Evento evento);	
	
	Usuario findById(long id);
}
	