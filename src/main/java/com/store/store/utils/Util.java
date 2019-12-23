package com.store.store.utils;

import java.util.Formatter;
import java.util.Locale;

import org.springframework.http.HttpStatus;

public class Util {

	public static HttpStatus validCode(String inputCode) {
		int code = Integer.parseInt(inputCode);
		if (code == 0) {
			return HttpStatus.OK;
		} else if (code == 422) {
			return HttpStatus.UNPROCESSABLE_ENTITY;
		} else {
			return HttpStatus.INTERNAL_SERVER_ERROR;
		}

	}
	
	@SuppressWarnings("resource")
	public static String  formatExecutionTimeApi(Long tempoInicial , Long tempoFinal ){
		 Formatter formatter = null;
	if ((formatter == null)
            || (formatter.locale() != Locale.getDefault()))
            formatter = new Formatter();
	return   formatter.format(Locale.getDefault(), "%.3f ms%n", (tempoFinal - tempoInicial) / 1000d).toString();

	}

}
