package com.universidad.sistemadmision.model;

import java.util.HashMap;
import java.util.Map;

public class Respuesta {
    private String litho;
    private String tema;
    private String secuencia;
    private Map<Integer, String> respuestas;
    
    public Respuesta() {
        this.respuestas = new HashMap<>();
    }
    
    public Respuesta(String litho, String tema, String secuencia) {
        this.litho = litho;
        this.tema = tema;
        this.secuencia = secuencia;
        this.respuestas = new HashMap<>();
    }
    
    public String getLitho() {
        return litho;
    }
    
    public void setLitho(String litho) {
        this.litho = litho;
    }
    
    public String getTema() {
        return tema;
    }
    
    public void setTema(String tema) {
        this.tema = tema;
    }
    
    public String getSecuencia() {
        return secuencia;
    }
    
    public void setSecuencia(String secuencia) {
        this.secuencia = secuencia;
    }
    
    public Map<Integer, String> getRespuestas() {
        return respuestas;
    }
    
    public void setRespuestas(Map<Integer, String> respuestas) {
        this.respuestas = respuestas;
    }
    
    public void setRespuesta(int numeroPregunta, String respuesta) {
        this.respuestas.put(numeroPregunta, respuesta);
    }
    
    public String getRespuesta(int numeroPregunta) {
        return this.respuestas.get(numeroPregunta);
    }
    
    @Override
    public String toString() {
        return "Respuesta{" +
                "litho='" + litho + '\'' +
                ", tema='" + tema + '\'' +
                ", secuencia='" + secuencia + '\'' +
                ", respuestas=" + respuestas +
                '}';
    }
}