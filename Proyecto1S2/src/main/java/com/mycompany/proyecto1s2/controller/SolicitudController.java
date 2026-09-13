
package com.mycompany.proyecto1s2.controller;

import com.mycompany.proyecto1s2.models.Solicitud;

/**
 *
 * @author celad
 */
public class SolicitudController {
private Solicitud[] solicitudes;
    private int contador;

    public SolicitudController(){
        this.solicitudes = new Solicitud[100]; 
        this.contador = 0;
    }

    public boolean agregarSolicitud(Solicitud solicitud){
        
        if (solicitud == null || solicitud.getId() == null){
            return false;
        }
        if (contador >= solicitudes.length){
            return false; 
        }
        for (int i = 0; i < contador; i++){
            if (solicitudes[i] != null && solicitudes[i].getId() != null){
                if (solicitudes[i].getId().equalsIgnoreCase(solicitud.getId())){
                    return false; 
                }
            }
        }

        solicitudes[contador] = solicitud;
        contador++;
        return true;
    }

  
    public Solicitud buscarPorId(String id){
        if (id == null){
            return null;
        }

        for (int i = 0; i < contador; i++){
            if (solicitudes[i] != null && solicitudes[i].getId() != null){
                if (solicitudes[i].getId().equalsIgnoreCase(id)) {
                    return solicitudes[i];
                }
            }
        }
        return null;
    }

    public boolean cambiarEstado(String id, String nuevoEstado){
        Solicitud s = buscarPorId(id);
        if (s != null && nuevoEstado != null){
            s.setEstado(nuevoEstado);
            return true;
        }
        return false;
    }

    public Solicitud[] getSolicitudes(){
        return solicitudes;
    }


    public Solicitud[] getSolicitudesValidas(){
        Solicitud[] validas = new Solicitud[contador];
        System.arraycopy(solicitudes, 0, validas, 0, contador);
        return validas;
    }

    public int getContador(){
        return contador;
    }
    
    public boolean aprobarSolicitud(String id){
        return cambiarEstado(id, "Aprobada");
    }
    
    public boolean rechazarSolicitud(String id){
        return cambiarEstado(id, "Rechazada");
    }
    public void rechazarOtrasSolicitudes(String codigoAnimal, String idSolicitudAprobada) {
        
        for(int i = 0; i < contador; i++){
        Solicitud s = solicitudes[i];
            if(s != null){
                if (s.getCodigoAnimal().equals(codigoAnimal) 
                    && !s.getId().equals(idSolicitudAprobada) 
                    && s.getEstado().equalsIgnoreCase("PENDIENTE")){
                    s.setEstado("RECHAZADA");
                }
            }
        }
    }
    
    
}