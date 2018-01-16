package br.ufpr.utils;

import java.text.ParseException;

import javax.swing.text.MaskFormatter;

public class Formatter {

	public static String formatCPF(String cpf) {
		try {
			MaskFormatter mf = new MaskFormatter("##.###.###-##");
			mf.setValueContainsLiteralCharacters(false);
			return mf.valueToString(cpf);
		} catch (ParseException e) {
			return "invalido";
		}
	}

	public static String formatCNPJ(String cnpj) {
		try {
			MaskFormatter mf = new MaskFormatter("##.###.###/####-##");
			mf.setValueContainsLiteralCharacters(false);
			return mf.valueToString(cnpj);
		} catch (ParseException e1) {
			return "invalido";
		}
	}

	public static String formatIdentificacao(String s) {
		return s.length() == 11 ? formatCPF(s) : formatCNPJ(s);
	}

}
