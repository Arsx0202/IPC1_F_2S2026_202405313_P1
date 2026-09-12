
package com.mycompany.proyecto1s2.models;

/**
 *
 * @author celad
 */
public class Animal {
    private int codigo;
    private String nombre;
    private String especie;
    private String estado;
    private boolean activo;

    public boolean isActivo() {
        return activo;
    }

    public void setActivo(boolean activo) {
        this.activo = activo;
    }
    
    
    public Animal(int codigo, String nombre, String especie, String estado) {
        this.codigo = codigo;
        this.nombre = nombre;
        this.especie = especie;
        this.estado = estado;
        this.activo = true;
    }
    
    
    public int getCodigo() {
        return codigo;
    }

    public void setCodigo(int codigo) {
        this.codigo = codigo;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getEspecie() {
        return especie;
    }

    public void setEspecie(String especie) {
        this.especie = especie;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }
    
    
}
