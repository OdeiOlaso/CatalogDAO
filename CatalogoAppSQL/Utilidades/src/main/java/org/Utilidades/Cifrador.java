package org.Utilidades;

import java.nio.charset.StandardCharsets;
import java.util.Base64;

public class Cifrador {

	public static void main(String[] args) {
		String hi = "Hhhhhola";
		System.out.println(hi);
		hi = cifrar(hi);
		System.out.println(hi);

		System.out.println(cifrar("Hhhhhola"));
	}

	public static String cifrar(String clave) {
		String result = "";
		char c, d;
		int e;
		byte[] a = null;

		for (int i = 0; i < clave.length(); i++) {

			c = clave.charAt(i);
			d = clave.charAt(clave.length() - i - 1);

			e = c * (clave.length() - i) * c * c * (c * d / 2) / (d * d * (i + 1));

			System.out.println(e);

			result = e + result;
		}
		System.out.println(result);

		a = result.getBytes(StandardCharsets.UTF_8);

		final String vuelta = Base64.getEncoder().encodeToString(a);

		System.out.println(vuelta);
		return vuelta;
	}
}
