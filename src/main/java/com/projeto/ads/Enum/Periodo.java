package com.projeto.ads.Enum;

public enum Periodo {
	Primeiro("1"),
	Segunda("2"),
	Terceiro("3"),
	Quarto("4");
	
	private String periodo;
	private Periodo(String periodo) {
		this.periodo = periodo;
	}
	
	public String getPeriodo() {
		return this.periodo;
	}
}
