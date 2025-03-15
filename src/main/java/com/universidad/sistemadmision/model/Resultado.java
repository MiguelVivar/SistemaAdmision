package com.universidad.sistemadmision.model;

public class Resultado implements Comparable<Resultado> {
    private String codigo; // DNI del estudiante
    private String tema;
    private int correctas;
    private int incorrectas;
    private int vacias;
    private double puntaje;
    
    public Resultado() {
    }
    
    public Resultado(String codigo, String tema) {
        this.codigo = codigo;
        this.tema = tema;
        this.correctas = 0;
        this.incorrectas = 0;
        this.vacias = 0;
        this.puntaje = 0.0;
    }
    
    public String getCodigo() {
        return codigo;
    }
    
    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }
    
    public String getTema() {
        return tema;
    }
    
    public void setTema(String tema) {
        this.tema = tema;
    }
    
    public int getCorrectas() {
        return correctas;
    }
    
    public void setCorrectas(int correctas) {
        this.correctas = correctas;
    }
    
    public void incrementarCorrectas() {
        this.correctas++;
    }
    
    public int getIncorrectas() {
        return incorrectas;
    }
    
    public void setIncorrectas(int incorrectas) {
        this.incorrectas = incorrectas;
    }
    
    public void incrementarIncorrectas() {
        this.incorrectas++;
    }
    
    public int getVacias() {
        return vacias;
    }
    
    public void setVacias(int vacias) {
        this.vacias = vacias;
    }
    
    public void incrementarVacias() {
        this.vacias++;
    }
    
    public double getPuntaje() {
        return puntaje;
    }
    
    public void setPuntaje(double puntaje) {
        this.puntaje = puntaje;
    }
    
    public void calcularPuntaje(double puntoPorCorrecta, double puntoPorIncorrecta, double puntoPorVacia) {
        this.puntaje = (this.correctas * puntoPorCorrecta) + (this.incorrectas * puntoPorIncorrecta) + (this.vacias * puntoPorVacia);
    }
    
    @Override
    public int compareTo(Resultado otro) {
        // Ordenar por puntaje de mayor a menor
        return Double.compare(otro.puntaje, this.puntaje);
    }
    
    @Override
    public String toString() {
        return "Resultado{" +
                "codigo='" + codigo + '\'' +
                ", tema='" + tema + '\'' +
                ", correctas=" + correctas +
                ", incorrectas=" + incorrectas +
                ", vacias=" + vacias +
                ", puntaje=" + puntaje +
                '}';
    }
}