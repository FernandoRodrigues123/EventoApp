package com.eventoapp.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.eventoapp.model.Usuario;
import com.eventoapp.repository.UsuarioRepository;

@Controller
public class UsuarioController {

	@Autowired
	private UsuarioRepository usuarioRepository;

	@RequestMapping(value = "/cadastroUser", method = RequestMethod.GET)
	public String Form() {
		return "evento/cadastroUser";
	}

	@RequestMapping(value = "/cadastroUser", method = RequestMethod.POST)
	public String Form(Usuario usuarioASalvar) {

		usuarioRepository.save(usuarioASalvar);

		return "redirect:/";
	}

}
