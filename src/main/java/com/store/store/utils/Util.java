package com.store.store.utils;

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

}
