
package com.mycompany.proyecto1s2.models;

/**
 *
 * @author celad
 */
public class Solicitud {
    private String id;
    private String dpiAdoptante;
    private String codigoAnimal;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDpiAdoptante() {
        return dpiAdoptante;
    }

    public void setDpiAdoptante(String dpiAdoptante) {
        this.dpiAdoptante = dpiAdoptante;
    }

    public String getCodigoAnimal() {
        return codigoAnimal;
    }

    public void setCodigoAnimal(String codigoAnimal) {
        this.codigoAnimal = codigoAnimal;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }
    private String fecha;
    private String estado;
    
    public Solicitud(String id, String dpiAdoptante, String codigoAnimal, String fecha, String estado){
        this.id = id;
        this.dpiAdoptante = dpiAdoptante;
        this.codigoAnimal = codigoAnimal;
        this.fecha = fecha;
        this.estado = estado;
    }    
}
