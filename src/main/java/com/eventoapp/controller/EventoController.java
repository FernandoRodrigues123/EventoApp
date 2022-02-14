package com.eventoapp.controller;



import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.eventoapp.model.Evento;
import com.eventoapp.model.Usuario;
import com.eventoapp.repository.EventoRepository;
import com.eventoapp.repository.UsuarioRepository;

@Controller
public class EventoController {
	
	@Autowired
	private EventoRepository eventoRepository;

	@Autowired
	private UsuarioRepository usuarioRepository;
	
	@RequestMapping(value = "/cadastroEvento", method = RequestMethod.GET )
	public String Form() {
		return "evento/formEvento";
	}
	
	@RequestMapping(value = "/cadastroEvento", method = RequestMethod.POST )
	public String Form(Evento evento) {
		
		eventoRepository.save(evento);
		
		return "redirect:/cadastroEvento";
	}
	
	@RequestMapping(value = "/eventos")
	public  ModelAndView listaEventos() {
		ModelAndView mv = new ModelAndView("evento/eventos");
		Iterable<Evento> eventos = eventoRepository.findAll();
		mv.addObject("eventos", eventos);
		return mv;
		
	}
	
	@RequestMapping(value="/{id}", method=RequestMethod.GET)
	public ModelAndView detalhesEvento(@PathVariable("id") long id){
		Evento evento = eventoRepository.findById(id);
		ModelAndView mv = new ModelAndView("evento/detalhesEvento");
		mv.addObject("evento",evento);
		
		Iterable<Usuario> usuarios = usuarioRepository.findByEvento(evento);
		mv.addObject("usuarios", usuarios);
		
		return mv;
	}
	
	@RequestMapping(value="/{id}", method=RequestMethod.POST)
	public String detalhesEventoPost(@PathVariable("id") long id, @Valid Usuario usuario, BindingResult result, RedirectAttributes attributes ){
		if(result.hasErrors()) {
			attributes.addFlashAttribute("mensagem", "verifique os campos!");
			return "redirect:/{id}";
		}
		
		
		Evento evento = eventoRepository.findById(id);
		usuario.setEvento(evento);
		usuarioRepository.save(usuario);
		attributes.addFlashAttribute("mensagem", "registro salvo!");
		return "redirect:/{id}";
	}
	
	
	@RequestMapping("/deletar")
	public String deletarEvento(long id){
	
		Evento evento = eventoRepository.findById(id);
		eventoRepository.delete(evento);
		return "redirect:/eventos";
	}
	
	@RequestMapping("/deletarUsuario")
	public String deletarUsuario(long id){
		Usuario usuario = usuarioRepository.findById(id);
		usuarioRepository.delete(usuario);
		
		Evento evento =	usuario.getEvento();
		long idUser = evento.getId();
		
		String idString = "" + idUser;

		return "redirect:/" + idString;
	}

}
