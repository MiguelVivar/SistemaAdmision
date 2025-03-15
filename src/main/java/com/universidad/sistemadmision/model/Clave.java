package com.universidad.sistemadmision.model;

import java.util.HashMap;
import java.util.Map;

public class Clave {
    private String litho;
    private String tema;
    private String nulo;
    private Map<Integer, String> respuestas;
    
    public Clave() {
        this.respuestas = new HashMap<>();
    }
    
    public Clave(String litho, String tema, String nulo) {
        this.litho = litho;
        this.tema = tema;
        this.nulo = nulo;
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
    
    public String getNulo() {
        return nulo;
    }
    
    public void setNulo(String nulo) {
        this.nulo = nulo;
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
        return "Clave{" +
                "litho='" + litho + '\'' +
                ", tema='" + tema + '\'' +
                ", respuestas=" + respuestas +
                '}';
    }
}