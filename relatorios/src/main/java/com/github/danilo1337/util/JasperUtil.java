package com.github.danilo1337.util;

import java.io.File;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;

import org.springframework.util.ResourceUtils;

public class JasperUtil {

	public String obterCaminhoArquivo(String nomeArquivo) {
		try {
			InputStream inputStream;
			String path;

			if (ResourceUtils.isUrl("classpath:jasper/" + nomeArquivo)) {
				// Ambiente de desenvolvimento
				inputStream = getClass().getClassLoader().getResourceAsStream("jasper/" + nomeArquivo);
				Path tempFile = Files.createTempFile("temp-jasper-", ".jrxml");
				Files.copy(inputStream, tempFile, StandardCopyOption.REPLACE_EXISTING);
				path = tempFile.toAbsolutePath().toString();
			} else {
				// Ambiente de produção (arquivo JAR)
				File file = ResourceUtils.getFile("classpath:jasper/" + nomeArquivo);
				path = file.getAbsolutePath();
			}

			return path;

		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
}
