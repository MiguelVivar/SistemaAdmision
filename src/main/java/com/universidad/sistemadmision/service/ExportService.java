package com.universidad.sistemadmision.service;

import com.itextpdf.text.*;
import com.itextpdf.text.Font; // Explicitly import iText Font
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import com.universidad.sistemadmision.model.Resultado;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

public class ExportService {

    /**
     * Exporta los resultados a un archivo PDF
     * @param resultados Lista de resultados a exportar
     * @param rutaArchivo Ruta donde se guardará el archivo PDF
     * @throws DocumentException Si ocurre un error al generar el PDF
     * @throws IOException Si ocurre un error de E/S
     */
    public void exportarAPDF(List<Resultado> resultados, String rutaArchivo) throws DocumentException, IOException {
        // Asegurar que el directorio exista
        File archivo = new File(rutaArchivo);
        File directorio = archivo.getParentFile();
        if (directorio != null && !directorio.exists()) {
            if (!directorio.mkdirs()) {
                throw new IOException("No se pudo crear el directorio: " + directorio.getAbsolutePath());
            }
        }
        
        Document documento = new Document(PageSize.A4.rotate());
        PdfWriter.getInstance(documento, new FileOutputStream(rutaArchivo));
        
        documento.open();
        
        // Agregar título
        Font fuenteTitulo = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 18, BaseColor.BLACK);
        Paragraph titulo = new Paragraph("Resultados del Sistema de Admisión Universitaria", fuenteTitulo);
        titulo.setAlignment(Element.ALIGN_CENTER);
        documento.add(titulo);
        documento.add(new Paragraph(" "));
        
        // Crear tabla
        PdfPTable tabla = new PdfPTable(8); // 8 columnas
        tabla.setWidthPercentage(100);
        
        // Agregar encabezados
        Font fuenteEncabezado = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 12, BaseColor.WHITE);
        BaseColor colorEncabezado = new BaseColor(204, 122, 41); // #cc7a29
        
        agregarCeldaEncabezado(tabla, "Orden Mérito", fuenteEncabezado, colorEncabezado);
        agregarCeldaEncabezado(tabla, "Código", fuenteEncabezado, colorEncabezado);
        agregarCeldaEncabezado(tabla, "Tema", fuenteEncabezado, colorEncabezado);
        agregarCeldaEncabezado(tabla, "Correctas", fuenteEncabezado, colorEncabezado);
        agregarCeldaEncabezado(tabla, "Incorrectas", fuenteEncabezado, colorEncabezado);
        agregarCeldaEncabezado(tabla, "Vacías", fuenteEncabezado, colorEncabezado);
        agregarCeldaEncabezado(tabla, "Puntaje", fuenteEncabezado, colorEncabezado);
        agregarCeldaEncabezado(tabla, "Condición", fuenteEncabezado, colorEncabezado);
        
        // Agregar datos
        Font fuenteNormal = FontFactory.getFont(FontFactory.HELVETICA, 10, BaseColor.BLACK);
        BaseColor colorFilaPar = new BaseColor(249, 241, 232); // #f9f1e8
        BaseColor colorFilaImpar = BaseColor.WHITE;
        
        boolean esPar = false;
        for (Resultado resultado : resultados) {
            BaseColor colorFila = esPar ? colorFilaPar : colorFilaImpar;
            
            agregarCelda(tabla, String.valueOf(resultado.getOrdenMerito()), fuenteNormal, colorFila);
            agregarCelda(tabla, resultado.getCodigo(), fuenteNormal, colorFila);
            agregarCelda(tabla, resultado.getTema(), fuenteNormal, colorFila);
            agregarCelda(tabla, String.valueOf(resultado.getCorrectas()), fuenteNormal, colorFila);
            agregarCelda(tabla, String.valueOf(resultado.getIncorrectas()), fuenteNormal, colorFila);
            agregarCelda(tabla, String.valueOf(resultado.getVacias()), fuenteNormal, colorFila);
            agregarCelda(tabla, String.format("%.2f", resultado.getPuntaje()), fuenteNormal, colorFila);
            agregarCelda(tabla, resultado.getCondicion(), fuenteNormal, colorFila);
            
            esPar = !esPar;
        }
        
        documento.add(tabla);
        
        // Agregar pie de página
        Paragraph pie = new Paragraph("Documento generado por el Sistema de Admisión Universitaria", 
                FontFactory.getFont(FontFactory.HELVETICA, 8, Font.ITALIC, BaseColor.GRAY));
        pie.setAlignment(Element.ALIGN_CENTER);
        documento.add(pie);
        
        documento.close();
    }
    
    private void agregarCeldaEncabezado(PdfPTable tabla, String texto, Font fuente, BaseColor color) {
        PdfPCell celda = new PdfPCell(new Phrase(texto, fuente));
        celda.setBackgroundColor(color);
        celda.setHorizontalAlignment(Element.ALIGN_CENTER);
        celda.setVerticalAlignment(Element.ALIGN_MIDDLE);
        celda.setPadding(8);
        tabla.addCell(celda);
    }
    
    private void agregarCelda(PdfPTable tabla, String texto, Font fuente, BaseColor color) {
        PdfPCell celda = new PdfPCell(new Phrase(texto, fuente));
        celda.setBackgroundColor(color);
        celda.setHorizontalAlignment(Element.ALIGN_CENTER);
        celda.setVerticalAlignment(Element.ALIGN_MIDDLE);
        celda.setPadding(6);
        tabla.addCell(celda);
    }
    
    /**
     * Exporta los resultados a un archivo Excel
     * @param resultados Lista de resultados a exportar
     * @param rutaArchivo Ruta donde se guardará el archivo Excel
     * @throws IOException Si ocurre un error de E/S
     */
    public void exportarAExcel(List<Resultado> resultados, String rutaArchivo) throws IOException {
        // Asegurar que el directorio exista
        File archivo = new File(rutaArchivo);
        File directorio = archivo.getParentFile();
        if (directorio != null && !directorio.exists()) {
            if (!directorio.mkdirs()) {
                throw new IOException("No se pudo crear el directorio: " + directorio.getAbsolutePath());
            }
        }
        
        Workbook workbook = new XSSFWorkbook();
        Sheet sheet = workbook.createSheet("Resultados");
        
        // Crear estilos
        CellStyle estiloEncabezado = workbook.createCellStyle();
        estiloEncabezado.setFillForegroundColor(IndexedColors.ORANGE.getIndex());
        estiloEncabezado.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        estiloEncabezado.setAlignment(HorizontalAlignment.CENTER);
        estiloEncabezado.setBorderBottom(BorderStyle.THIN);
        estiloEncabezado.setBorderLeft(BorderStyle.THIN);
        estiloEncabezado.setBorderRight(BorderStyle.THIN);
        estiloEncabezado.setBorderTop(BorderStyle.THIN);
        
        org.apache.poi.ss.usermodel.Font fuenteEncabezado = workbook.createFont();
        fuenteEncabezado.setBold(true);
        fuenteEncabezado.setColor(IndexedColors.WHITE.getIndex());
        estiloEncabezado.setFont(fuenteEncabezado);
        
        CellStyle estiloCelda = workbook.createCellStyle();
        estiloCelda.setAlignment(HorizontalAlignment.CENTER);
        estiloCelda.setBorderBottom(BorderStyle.THIN);
        estiloCelda.setBorderLeft(BorderStyle.THIN);
        estiloCelda.setBorderRight(BorderStyle.THIN);
        estiloCelda.setBorderTop(BorderStyle.THIN);
        
        CellStyle estiloCeldaPar = workbook.createCellStyle();
        estiloCeldaPar.cloneStyleFrom(estiloCelda);
        estiloCeldaPar.setFillForegroundColor(IndexedColors.LIGHT_YELLOW.getIndex());
        estiloCeldaPar.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        
        // Crear fila de encabezado
        Row filaEncabezado = sheet.createRow(0);
        String[] encabezados = {"Orden Mérito", "Código", "Tema", "Correctas", "Incorrectas", "Vacías", "Puntaje", "Condición"};
        
        for (int i = 0; i < encabezados.length; i++) {
            Cell celda = filaEncabezado.createCell(i);
            celda.setCellValue(encabezados[i]);
            celda.setCellStyle(estiloEncabezado);
        }
        
        // Agregar datos
        int numeroFila = 1;
        for (Resultado resultado : resultados) {
            Row fila = sheet.createRow(numeroFila++);
            CellStyle estiloActual = (numeroFila % 2 == 0) ? estiloCeldaPar : estiloCelda;
            
            Cell celdaOrdenMerito = fila.createCell(0);
            celdaOrdenMerito.setCellValue(resultado.getOrdenMerito());
            celdaOrdenMerito.setCellStyle(estiloActual);
            
            Cell celdaCodigo = fila.createCell(1);
            celdaCodigo.setCellValue(resultado.getCodigo());
            celdaCodigo.setCellStyle(estiloActual);
            
            Cell celdaTema = fila.createCell(2);
            celdaTema.setCellValue(resultado.getTema());
            celdaTema.setCellStyle(estiloActual);
            
            Cell celdaCorrectas = fila.createCell(3);
            celdaCorrectas.setCellValue(resultado.getCorrectas());
            celdaCorrectas.setCellStyle(estiloActual);
            
            Cell celdaIncorrectas = fila.createCell(4);
            celdaIncorrectas.setCellValue(resultado.getIncorrectas());
            celdaIncorrectas.setCellStyle(estiloActual);
            
            Cell celdaVacias = fila.createCell(5);
            celdaVacias.setCellValue(resultado.getVacias());
            celdaVacias.setCellStyle(estiloActual);
            
            Cell celdaPuntaje = fila.createCell(6);
            celdaPuntaje.setCellValue(resultado.getPuntaje());
            celdaPuntaje.setCellStyle(estiloActual);
            
            Cell celdaCondicion = fila.createCell(7);
            celdaCondicion.setCellValue(resultado.getCondicion());
            celdaCondicion.setCellStyle(estiloActual);
        }
        
        // Ajustar ancho de columnas automáticamente
        for (int i = 0; i < encabezados.length; i++) {
            sheet.autoSizeColumn(i);
        }
        
        // Escribir el archivo
        try (FileOutputStream outputStream = new FileOutputStream(rutaArchivo)) {
            workbook.write(outputStream);
        }
        
        workbook.close();
    }
    
    /**
     * Exporta los resultados a un archivo CSV
     * @param resultados Lista de resultados a exportar
     * @param rutaArchivo Ruta donde se guardará el archivo CSV
     * @throws IOException Si ocurre un error de E/S
     */
    public void exportarACSV(List<Resultado> resultados, String rutaArchivo) throws IOException {
        // Asegurar que el directorio exista
        File archivo = new File(rutaArchivo);
        File directorio = archivo.getParentFile();
        if (directorio != null && !directorio.exists()) {
            if (!directorio.mkdirs()) {
                throw new IOException("No se pudo crear el directorio: " + directorio.getAbsolutePath());
            }
        }
        
        try (FileWriter writer = new FileWriter(archivo)) {
            // Escribir encabezados
            writer.append("Orden Mérito,Código,Tema,Correctas,Incorrectas,Vacías,Puntaje,Condición\n");
            
            // Escribir datos
            for (Resultado resultado : resultados) {
                writer.append(String.valueOf(resultado.getOrdenMerito()))
                      .append(",")
                      .append(resultado.getCodigo())
                      .append(",")
                      .append(resultado.getTema())
                      .append(",")
                      .append(String.valueOf(resultado.getCorrectas()))
                      .append(",")
                      .append(String.valueOf(resultado.getIncorrectas()))
                      .append(",")
                      .append(String.valueOf(resultado.getVacias()))
                      .append(",")
                      .append(String.format("%.2f", resultado.getPuntaje()))
                      .append(",")
                      .append(resultado.getCondicion())
                      .append("\n");
            }
            
            writer.flush();
        }
    }
}