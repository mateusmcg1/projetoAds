package com.projeto.ads.util;

import java.util.UUID;

public class Util {

	public static String generateToken() {
		// gere um uuid aleatorio

		UUID uuid = UUID.randomUUID();
		// converta o uuid em uma string removendo os hifens e convertendo para letras
		// minúsculas
		String token = uuid.toString().replaceAll("-", "").toLowerCase();
		return token;
	}
}
