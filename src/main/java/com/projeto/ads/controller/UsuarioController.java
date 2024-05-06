package com.projeto.ads.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;
import com.projeto.ads.model.Usuario;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.Authentication;

import com.projeto.ads.repository.RoleRepository;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestParam;
import com.projeto.ads.repository.UserRepository;
import com.projeto.ads.service.UserService;
import com.projeto.ads.model.Role;

import org.springframework.web.bind.annotation.PostMapping;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;


@Controller
public class UsuarioController {
	@Autowired
	RoleRepository roleRepository;
	@Autowired
	private UserRepository userRepository;
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	@Autowired
	private UserService userService;

	@PostMapping("/usuario/inserir")
	public ModelAndView salvarUsuario(@ModelAttribute Usuario usuario,
				@RequestParam("confirmPassword") String confirmPassword,
				@RequestParam("roleUser") String roleString,
				 @RequestParam("dataNasc") String dataNascimentoString)
	{		
			ModelAndView mv = new ModelAndView();
			//TODAS VALIDAÇÕES VAO VIR AQUI
			String error= userService.validaErros(usuario, confirmPassword, dataNascimentoString);
			if (error != null) {
				mv.addObject("error", error);
				mv.setViewName("Login/cadastro");
				return mv;
			}
			Date dataFormatada = null;
	try {
	// Define o formato de entrada da data
	SimpleDateFormat formatoEntrada = new SimpleDateFormat("yyyy-MM-dd");
	// Converte a string para um objeto Date
	dataFormatada = formatoEntrada.parse(dataNascimentoString);
	} catch (ParseException e) {
	e.printStackTrace();
	}
	usuario.setDataNascimento(dataFormatada);
	// Buscar a Role correspondente ao nome recebido
	Role role = roleRepository.findByNome(roleString);
	// Setar o objeto Role no usuário
	usuario.setRole(role);
	// Codificar a senha antes de salvar no banco de dados
	usuario.setPassword(passwordEncoder.encode(usuario.getPassword()));
	usuario.setUsername(usuario.getEmail());
	// Salvar o usuário no banco de dados
	//userRepository.save(usuario);
	// Redirecionar para a página de login após salvar o usuário
	mv.setViewName("redirect:/login");
	return mv;
		}

	@GetMapping("/usuario/inserir")
	public ModelAndView cadastro() {
		ModelAndView mv= new ModelAndView();
		mv.addObject("usuario", new Usuario());
		mv.addObject("roles", roleRepository.findAll());
		mv.setViewName("Login/cadastro");
		return mv;
	}
	@GetMapping("/login")
	public ModelAndView login() {
		ModelAndView mv = new ModelAndView();
		mv.addObject("usuario", new Usuario());
		mv.setViewName("Login/login");
		return mv;
	}
	@GetMapping("/dashboard")
	public ModelAndView dashBoard(Authentication authentication) {
		ModelAndView mv = new ModelAndView();
		UserDetails userDetalhe= (UserDetails) authentication.getPrincipal();
		String username= userDetalhe.getUsername();
		int indice = username.indexOf('@');
		String nome= indice !=-1 ? username.substring(0, indice): username;
		mv.addObject("nomeUsuario", nome);
		String papel= authentication.getAuthorities().iterator().next().getAuthority();
		if(papel.equals("ROLE_ADMIN"))
		{
			mv.setViewName("Login/index");
		}
		else if(papel.equals("ROLE_USER")) {
			mv.setViewName("Login/indexUsuario");
		}
		
		return mv;
	}//fim dashboard
	
	
}
