
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


    public boolean agregar(int codigo, String nombre, String especie, String estado, int celda) {

        if (buscar(codigo) != null){
            return false;
        }
        if (codigo<0){
           return false; 
        }

        if (contador >= listaAnimales.length){
            return false;
        }

        listaAnimales[contador] = new Animal(codigo, nombre, especie, estado, celda);
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
    public boolean cambiarEstadoAnimal(String codigoStr, String nuevoEstado) {
        if (codigoStr == null || codigoStr.trim().isEmpty()){
        System.out.println("Error: El código recibido está vacío o es nulo.");
        return false;
        }try{
            String soloNumeros = codigoStr.replaceAll("[^0-9]", "");

            if (soloNumeros.isEmpty()) {
                System.out.println("Error: No se encontraron dígitos numéricos en: " + codigoStr);
                return false;
            }

        int codigoInt = Integer.parseInt(soloNumeros);

        Animal a = buscar(codigoInt);
        if(a != null){
            a.setEstado(nuevoEstado);
            return true;
        }else{
            System.out.println("Error: No se encontró ningún animal activo con el código " + codigoInt);
        }
        
        }catch(NumberFormatException e){
        System.out.println("Error al parsear el número: " + e.getMessage());
        }
        return false;
    }
    public Animal obtenerAnimalEnCelda(int numeroCelda) {
        for (int i = 0; i < contador; i++){
            Animal a = listaAnimales[i];
            if (a != null && a.isActivo() && a.getCelda() == numeroCelda && a.getEstado().equalsIgnoreCase("Disponible")){
                return a; 
            }
        }
    return null;
    }
    public boolean agregar(int codigo, String nombre, String especie, String estado) {
    return agregar(codigo, nombre, especie, estado, 0);
}
    public void autoAsignarCeldas() {
    for (int i = 0; i < contador; i++) {
        if (listaAnimales[i] != null) {
            listaAnimales[i].setCelda(i + 1);
        }
    }
}
    
}

