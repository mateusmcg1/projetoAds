package com.projeto.ads.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

import com.projeto.ads.model.Aluno;
import com.projeto.ads.repository.AlunoRepository;
import com.projeto.ads.service.AlunoService;

@Controller
public class AlunoController {

	@Autowired
	AlunoService alunoService;
	@Autowired
	AlunoRepository alunoRepository;

	@PostMapping("/aluno/inserir")
	public ModelAndView insertAlunoPOST(Aluno aluno) {
		ModelAndView mv = new ModelAndView();
		String resposta = alunoService.cadastrarAluno(aluno);
		if (resposta != null) {
			mv.addObject("msg", resposta);
			mv.addObject("aluno", new Aluno());
			mv.setViewName("Login/inserirAluno");
		} else {
			mv.addObject("alunos", alunoRepository.findAllOrderedById());
			mv.setViewName("redirect:/aluno/listar");
		}
		return mv;
	}

	@GetMapping("/aluno/listar")
	public ModelAndView listarAlunos() {
		ModelAndView mv = new ModelAndView();
		mv.addObject("alunos", alunoRepository.findAllOrderedById());
		mv.setViewName("Aluno/listarAluno");
		return mv;
	}//

	@GetMapping("/aluno/inserir")
	public ModelAndView insertAlunoGET() {
		ModelAndView mv = new ModelAndView();
		mv.addObject("aluno", new Aluno());
		mv.setViewName("Aluno/inserirAluno");
		return mv;
	}//

	@GetMapping("/aluno/alterar/{id}")
	public ModelAndView alterarAluno(@PathVariable("id") long id) {
		ModelAndView mv = new ModelAndView();
		Aluno aluno = alunoRepository.findById(id).get();
		mv.addObject("aluno", aluno);
		mv.setViewName("Aluno/alterarAluno");
		return mv;
	}//
	
	@GetMapping("/aluno/editar")
	public ModelAndView alterarAluno() {
		ModelAndView mv = new ModelAndView();
		mv.addObject("alunos", alunoRepository.findAllOrderedById());
		mv.setViewName("Aluno/listarAluno");
		return mv;
	}//

	@PostMapping("/aluno/alterar")
	public ModelAndView alterarAluno(Aluno aluno) {
		ModelAndView mv = new ModelAndView();
		String out = alunoService.alterarAluno(aluno, aluno.getId());
		if (out != null) {
			mv.addObject("msg", out);
			mv.addObject("aluno", aluno);
			mv.setViewName("Aluno/alterarAluno");
		} else {
			mv.setViewName("redirect:/aluno/listar");
		}
		return mv;
	}
	@GetMapping("/aluno/excluir/{id}")
	public String excluirAluno(@PathVariable("id") long id) {
		alunoRepository.deleteById(id);
		return "redirect:/aluno/listar";
	}
	
	@GetMapping("/aluno/deletar")
	public ModelAndView excluirAluno() {
		ModelAndView mv = new ModelAndView();
		mv.addObject("alunos", alunoRepository.findAllOrderedById());
		mv.setViewName("Aluno/listarAluno");
		return mv;
	}//

}
