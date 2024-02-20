package com.github.danilo1337.service;

import java.sql.Connection;
import java.util.Map;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.datasource.DataSourceUtils;
import org.springframework.stereotype.Service;

import com.github.danilo1337.util.JasperUtil;

import net.sf.jasperreports.engine.JREmptyDataSource;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;

@Service
public class RelatorioService {

	@Autowired
	private DataSource dataSource;

	public byte[] imprimirComBancoDeDados(String nomeArquivo, Map<String, Object> parametros) {

		try {

			Connection connection = DataSourceUtils.getConnection(dataSource);

			String path = new JasperUtil().obterCaminhoArquivo(nomeArquivo);

			JasperPrint jasperPrint = JasperFillManager.fillReport(JasperCompileManager.compileReport(path), parametros,
					connection);

			byte[] pdfBytes = JasperExportManager.exportReportToPdf(jasperPrint);

			return pdfBytes;

		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public byte[] imprimirSemBancoDeDados(String nomeArquivo, Map<String, Object> parametros) {

		try {

			String path = new JasperUtil().obterCaminhoArquivo(nomeArquivo);

			JasperPrint jasperPrint = JasperFillManager.fillReport(JasperCompileManager.compileReport(path), parametros,
					new JREmptyDataSource());

			byte[] pdfBytes = JasperExportManager.exportReportToPdf(jasperPrint);

			return pdfBytes;

		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

}
