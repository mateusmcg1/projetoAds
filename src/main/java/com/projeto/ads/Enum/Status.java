package com.projeto.ads.Enum;

public enum Status {
	ATIVO("Ativo"),
	CANCELADO("Cancelado"),
	INATIVO("Inativo"),
	TRACANDO("Trancado");
	
	private String status;
	private Status (String status) {
		this.status = status;
	}
	
	public String getStatus() {
		return this.status;
	}
}
