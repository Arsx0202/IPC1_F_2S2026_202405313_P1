
package com.mycompany.proyecto1s2.controller;

import com.mycompany.proyecto1s2.models.Solicitud;

/**
 *
 * @author celad
 */
public class SolicitudController {
    private Solicitud[] solicitudes;
    private int contador;

    public SolicitudController() {
        this.solicitudes = new Solicitud[100]; 
        this.contador = 0;
    }

    public boolean agregarSolicitud(Solicitud solicitud) {
        if (contador >= solicitudes.length) {
            return false; 
        }
        
        
        for (int i = 0; i < contador; i++) {
            if (solicitudes[i].getId().equalsIgnoreCase(solicitud.getId())) {
                return false;
            }
        }

        solicitudes[contador] = solicitud;
        contador++;
        return true;
    }

    public Solicitud buscarPorId(String id) {
        for (int i = 0; i < contador; i++) {
            if (solicitudes[i].getId().equalsIgnoreCase(id)) {
                return solicitudes[i];
            }
        }
        return null;
    }

    public boolean cambiarEstado(String id, String nuevoEstado) {
        Solicitud s = buscarPorId(id);
        if (s != null) {
            s.setEstado(nuevoEstado);
            return true;
        }
        return false;
    }

    public Solicitud[] getSolicitudes() {
        return solicitudes;
    }

    public int getContador() {
        return contador;
    }
    
}
