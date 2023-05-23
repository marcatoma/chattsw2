package com.tws2.validator;

import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;

@Service
public class ObtenerErrores {

	public Map<String, String> Errores(BindingResult result) {
		return result.getFieldErrors().stream()
				.collect(Collectors.toMap(err -> err.getField(), err -> err.getDefaultMessage()));

	}

}
