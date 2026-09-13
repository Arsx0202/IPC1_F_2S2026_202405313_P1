
package com.mycompany.proyecto1s2.models;

/**
 *
 * @author celad
 */
public class Adoptante {
    private long dpi;
    private String nombre;
    private int telefono;
    private String direccion;
    private boolean activo;
    
    
    public Adoptante(long dpi, String nombre, int telefono, String direccion) {
        this.dpi = dpi;
        this.nombre = nombre;
        this.telefono = telefono;
        this.direccion = direccion;
        this.activo = true;
    }

    public long getDpi() {
        return dpi;
    }

    public void setDpi(long dpi) {
        this.dpi = dpi;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public int getTelefono() {
        return telefono;
    }

    public void setTelefono(int telefono) {
        this.telefono = telefono;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public boolean isActivo() {
        return activo;
    }

    public void setActivo(boolean activo) {
        this.activo = activo;
    }
    
}

