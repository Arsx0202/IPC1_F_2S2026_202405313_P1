
package com.mycompany.proyecto1s2.controller;
import com.mycompany.proyecto1s2.models.Adoptante;
/**
 *
 * @author celad
 */
public class AdoptanteController {
    private Adoptante[] listaAdoptantes;
    private int contador;

    public AdoptanteController() {
        this.listaAdoptantes = new Adoptante[50];
        this.contador = 0;
    }

    public boolean agregar(long dpi, String nombre, int telefono, String direccion) {
        if (buscar(dpi) != null) {
            return false;
        }
        if (contador < listaAdoptantes.length) {
            listaAdoptantes[contador] = new Adoptante(dpi, nombre, telefono, direccion);
            contador++;
            return true;
        }
        return false;
    }

    public Adoptante buscar(long dpi) {
        for (int i = 0; i < contador; i++) {
            if (listaAdoptantes[i] != null && listaAdoptantes[i].getDpi() == dpi && listaAdoptantes[i].isActivo()) {
                return listaAdoptantes[i];
            }
        }
        return null;
    }

    public boolean modificar(long dpi, String nombre, int telefono, String direccion) {
        Adoptante a = buscar(dpi);
        if (a != null) {
            a.setNombre(nombre);
            a.setTelefono(telefono);
            a.setDireccion(direccion);
            return true;
        }
        return false;
    }

    public boolean eliminarLogico(long dpi) {
        Adoptante a = buscar(dpi);
        if (a != null) {
            a.setActivo(false);
            return true;
        }
        return false;
    }

    public Adoptante[] getListaAdoptantes() {
        return listaAdoptantes;
    }
    
}
