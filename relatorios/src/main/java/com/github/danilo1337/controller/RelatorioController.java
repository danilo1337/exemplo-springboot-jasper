package com.github.danilo1337.controller;

import java.time.Instant;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.github.danilo1337.service.RelatorioService;

@RestController
@RequestMapping("relatorios")
public class RelatorioController {

	@Autowired
	private RelatorioService relatorioService;

	@GetMapping("inventarios/{id}")
	public ResponseEntity<?> gerarRelatorioInventarios(
			@RequestParam(required = false, defaultValue = "false") boolean download, @PathVariable Integer id)
			throws Exception {

		Map<String, Object> parametros = new HashMap<>();
		parametros.put("p_inventory_id", id);

		byte[] pdfBytes = relatorioService.imprimirComBancoDeDados("com-banco-e-parametro.jrxml", parametros);

		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_PDF);

		if (download) {
			headers.setContentDispositionFormData("attachment", Instant.now().toEpochMilli() + ".pdf");
		}

		return new ResponseEntity<>(pdfBytes, headers, HttpStatus.OK);

	}

	@GetMapping("inventarios")
	public ResponseEntity<?> gerarRelatorioInventarios(
			@RequestParam(required = false, defaultValue = "false") boolean download) throws Exception {

		Map<String, Object> parametros = new HashMap<>();

		byte[] pdfBytes = relatorioService.imprimirComBancoDeDados("com-banco.jrxml", parametros);

		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_PDF);

		if (download) {
			headers.setContentDispositionFormData("attachment", Instant.now().toEpochMilli() + ".pdf");
		}

		return new ResponseEntity<>(pdfBytes, headers, HttpStatus.OK);

	}

	@GetMapping("sem-banco")
	public ResponseEntity<?> gerarRelatorioSemBanco(
			@RequestParam(required = false, defaultValue = "false") boolean download) throws Exception {

		Map<String, Object> parametros = new HashMap<>();

		byte[] pdfBytes = relatorioService.imprimirSemBancoDeDados("sem-banco.jrxml", parametros);

		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_PDF);

		if (download) {
			headers.setContentDispositionFormData("attachment", Instant.now().toEpochMilli() + ".pdf");
		}

		return new ResponseEntity<>(pdfBytes, headers, HttpStatus.OK);
	}

}
