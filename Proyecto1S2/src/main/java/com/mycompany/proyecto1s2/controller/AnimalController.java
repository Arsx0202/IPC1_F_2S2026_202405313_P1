
package com.mycompany.proyecto1s2.controller;

import com.mycompany.proyecto1s2.models.Animal;

/**
 *
 * @author celad
 */
public class AnimalController {
    private final Animal[] listaAnimales;
    private int contador;

    public AnimalController() {
        this.listaAnimales = new Animal[100];
        this.contador = 0;
    }


    public boolean agregar(int codigo, String nombre, String especie, String estado) {

        if (buscar(codigo) != null){
            return false;
        }

        if (contador >= listaAnimales.length){
            return false;
        }

        listaAnimales[contador] = new Animal(codigo, nombre, especie, estado);
        contador++;
        return true;
    }


    public Animal buscar(int codigo){
        for (int i = 0; i < contador; i++){
            if (listaAnimales[i] != null && listaAnimales[i].isActivo() && listaAnimales[i].getCodigo() == codigo) {
                return listaAnimales[i];
            }
        }
        return null;
    }


    public boolean modificar(int codigo, String nombre, String especie, String estado){
        Animal a = buscar(codigo);
        if (a != null) {
            a.setNombre(nombre);
            a.setEspecie(especie);
            a.setEstado(estado);
            return true;
        }
        return false;
    }

    public boolean eliminarLogico(int codigo){
        Animal a = buscar(codigo);
        if (a != null) {
            a.setActivo(false);
            return true;
        }
        return false;
    }

 
    public Animal[] getListaAnimales(){
        return listaAnimales;
    }

    public int getContador(){
        return contador;
    }
}

