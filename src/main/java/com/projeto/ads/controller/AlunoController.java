package com.projeto.ads.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

import com.projeto.ads.model.Aluno;

@Controller
public class AlunoController {
	@GetMapping("/aluno/inserirAluno")
	public ModelAndView insertAluno() {
		
		ModelAndView mv = new ModelAndView();
		mv.addObject("aluno", new Aluno());
		mv.setViewName("Aluno/inserirAluno");
		return mv;
	}
}
