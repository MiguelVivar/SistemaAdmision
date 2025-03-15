package com.universidad.sistemadmision.service;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.linuxense.javadbf.DBFField;
import com.linuxense.javadbf.DBFReader;
import com.universidad.sistemadmision.model.Clave;
import com.universidad.sistemadmision.model.Identificador;
import com.universidad.sistemadmision.model.Respuesta;
import com.universidad.sistemadmision.model.Resultado;

public class DBFService {
    
    public List<Clave> cargarClaves(String rutaArchivo) throws IOException {
        List<Clave> claves = new ArrayList<>();
        
        try (InputStream inputStream = new FileInputStream(rutaArchivo)) {
            DBFReader reader = new DBFReader(inputStream);
            
            Object[] rowObjects;
            while ((rowObjects = reader.nextRecord()) != null) {
                String litho = rowObjects[0] != null ? rowObjects[0].toString().trim() : "";
                String tema = rowObjects[1] != null ? rowObjects[1].toString().trim() : "";
                String nulo = rowObjects[2] != null ? rowObjects[2].toString().trim() : "";
                
                Clave clave = new Clave(litho, tema, nulo);
                
                // Cargar las respuestas (PREG_001 a PREG_100)
                for (int i = 0; i < 100; i++) {
                    int columnIndex = i + 3; // Las preguntas empiezan en la columna 3
                    if (columnIndex < rowObjects.length) {
                        String respuesta = rowObjects[columnIndex] != null ? rowObjects[columnIndex].toString().trim() : "";
                        clave.setRespuesta(i + 1, respuesta); // Numeramos las preguntas desde 1
                    }
                }
                
                claves.add(clave);
            }
        }
        
        return claves;
    }
    
    public List<Identificador> cargarIdentificadores(String rutaArchivo) throws IOException {
        List<Identificador> identificadores = new ArrayList<>();
        
        try (InputStream inputStream = new FileInputStream(rutaArchivo)) {
            DBFReader reader = new DBFReader(inputStream);
            
            Object[] rowObjects;
            while ((rowObjects = reader.nextRecord()) != null) {
                String litho = rowObjects[0] != null ? rowObjects[0].toString().trim() : "";
                String tema = rowObjects[1] != null ? rowObjects[1].toString().trim() : "";
                String codigo = rowObjects[2] != null ? rowObjects[2].toString().trim() : "";
                String secuencia = rowObjects[5] != null ? rowObjects[5].toString().trim() : ""; // La secuencia está en la columna 6
                
                Identificador identificador = new Identificador(litho, tema, codigo, secuencia);
                identificadores.add(identificador);
            }
        }
        
        return identificadores;
    }
    
    public List<Respuesta> cargarRespuestas(String rutaArchivo) throws IOException {
        List<Respuesta> respuestas = new ArrayList<>();
        
        try (InputStream inputStream = new FileInputStream(rutaArchivo)) {
            DBFReader reader = new DBFReader(inputStream);
            
            // Encontrar el índice de la columna SECUENCIA
            int secuenciaIndex = -1;
            for (int i = 0; i < reader.getFieldCount(); i++) {
                DBFField field = reader.getField(i);
                if (field.getName().equalsIgnoreCase("SECUENCIA")) {
                    secuenciaIndex = i;
                    break;
                }
            }
            
            Object[] rowObjects;
            while ((rowObjects = reader.nextRecord()) != null) {
                String litho = rowObjects[0] != null ? rowObjects[0].toString().trim() : "";
                String tema = rowObjects[1] != null ? rowObjects[1].toString().trim() : "";
                String secuencia = secuenciaIndex != -1 && rowObjects[secuenciaIndex] != null ? 
                        rowObjects[secuenciaIndex].toString().trim() : "";
                
                Respuesta respuesta = new Respuesta(litho, tema, secuencia);
                
                // Cargar las respuestas (PREG_001 a PREG_100)
                for (int i = 0; i < 100; i++) {
                    int columnIndex = i + 2; // Las preguntas empiezan después de LITHO y TEMA
                    // Evitar la columna SECUENCIA
                    if (columnIndex == secuenciaIndex) {
                        continue;
                    }
                    if (columnIndex < rowObjects.length) {
                        String resp = rowObjects[columnIndex] != null ? rowObjects[columnIndex].toString().trim() : "";
                        // Solo guardar si es una columna de pregunta (PREG_XXX)
                        if (reader.getField(columnIndex).getName().startsWith("PREG_")) {
                            int numeroPregunta = Integer.parseInt(reader.getField(columnIndex).getName().substring(5));
                            respuesta.setRespuesta(numeroPregunta, resp);
                        }
                    }
                }
                
                respuestas.add(respuesta);
            }
        }
        
        return respuestas;
    }
    
    public List<Resultado> calcularResultados(List<Clave> claves, List<Identificador> identificadores, 
                                             List<Respuesta> respuestas, double puntoPorCorrecta, 
                                             double puntoPorIncorrecta, double puntoPorVacia) {
        List<Resultado> resultados = new ArrayList<>();
        
        // Crear un mapa para acceder rápidamente a las claves por tema
        Map<String, Clave> mapClaves = new HashMap<>();
        for (Clave clave : claves) {
            mapClaves.put(clave.getTema(), clave);
        }
        
        // Crear un mapa para relacionar secuencia con identificador
        Map<String, Identificador> mapIdentificadores = new HashMap<>();
        for (Identificador identificador : identificadores) {
            mapIdentificadores.put(identificador.getSecuencia(), identificador);
        }
        
        // Procesar cada respuesta
        for (Respuesta respuesta : respuestas) {
            String secuencia = respuesta.getSecuencia();
            Identificador identificador = mapIdentificadores.get(secuencia);
            
            if (identificador != null) {
                String tema = identificador.getTema();
                Clave clave = mapClaves.get(tema);
                
                if (clave != null) {
                    Resultado resultado = new Resultado(identificador.getCodigo(), tema);
                    
                    // Comparar respuestas con la clave
                    for (int i = 1; i <= 100; i++) {
                        String respuestaEstudiante = respuesta.getRespuesta(i);
                        String respuestaClave = clave.getRespuesta(i);
                        
                        if (respuestaEstudiante == null || respuestaEstudiante.isEmpty()) {
                            resultado.incrementarVacias();
                        } else if (respuestaClave == null || respuestaClave.isEmpty() || respuestaEstudiante.equals(respuestaClave)) {
                            // Si la clave está vacía o coincide con la respuesta del estudiante, es correcta
                            resultado.incrementarCorrectas();
                        } else {
                            resultado.incrementarIncorrectas();
                        }
                    }
                    
                    // Calcular puntaje
                    resultado.calcularPuntaje(puntoPorCorrecta, puntoPorIncorrecta, puntoPorVacia);
                    resultados.add(resultado);
                }
            }
        }
        
        return resultados;
    }
}