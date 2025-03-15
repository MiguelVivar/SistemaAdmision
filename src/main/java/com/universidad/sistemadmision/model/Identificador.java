package com.universidad.sistemadmision.model;

public class Identificador {
    private String litho;
    private String tema;
    private String codigo; // DNI del estudiante
    private String secuencia;
    
    public Identificador() {
    }
    
    public Identificador(String litho, String tema, String codigo, String secuencia) {
        this.litho = litho;
        this.tema = tema;
        this.codigo = codigo;
        this.secuencia = secuencia;
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
    
    public String getCodigo() {
        return codigo;
    }
    
    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }
    
    public String getSecuencia() {
        return secuencia;
    }
    
    public void setSecuencia(String secuencia) {
        this.secuencia = secuencia;
    }
    
    @Override
    public String toString() {
        return "Identificador{" +
                "litho='" + litho + '\'' +
                ", tema='" + tema + '\'' +
                ", codigo='" + codigo + '\'' +
                ", secuencia='" + secuencia + '\'' +
                '}';
    }
}