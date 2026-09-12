
package com.mycompany.proyecto1s2.controller;

import com.mycompany.proyecto1s2.models.Usuario;
import com.mycompany.proyecto1s2.persistencia.persistencia;

public class UsuarioController {
    private Usuario[] usuarios;
    private static final int CAPACIDAD = 100;

    public UsuarioController() {
        this.usuarios = persistencia.cargar();
        if (this.usuarios == null) {
            this.usuarios = new Usuario[CAPACIDAD];
        }
    }

    public Usuario[] getUsuarios() {
        return usuarios;
    }

    public Usuario buscarPorUsuario(String username) {
        if (username == null || username.trim().isEmpty()) {
            return null;
        }

        for (Usuario u : usuarios) {
            if (u != null && u.getUsuario() != null) {
                if (u.getUsuario().equalsIgnoreCase(username.trim())) {
                    return u; 
                }
            }
        }
        return null; 
    }

    public int generarNuevoCodigo() {
        int maxCodigo = 0;
        for (Usuario u : usuarios) {
            if (u != null && u.getCodigo() > maxCodigo) {
                maxCodigo = u.getCodigo();
            }
        }
        return maxCodigo + 1;
    }
    public boolean registrarUsuario(Usuario nuevoUsuario) {
        
        if (nuevoUsuario == null || 
          nuevoUsuario.getUsuario() == null || nuevoUsuario.getUsuario().trim().isEmpty() ||
            nuevoUsuario.getPassword() == null || nuevoUsuario.getPassword().trim().isEmpty() ||
            nuevoUsuario.getRol() == null || nuevoUsuario.getRol().trim().isEmpty()) {
            return false;
        }
        if (buscarPorUsuario(nuevoUsuario.getUsuario()) != null) {
            return false; 
        } 
        if (nuevoUsuario.getCodigo() <= 0) {
            nuevoUsuario.setCodigo(generarNuevoCodigo());
        }
        for (int i = 0; i < usuarios.length; i++) {
            if (usuarios[i] == null) {
                usuarios[i] = nuevoUsuario;
               
                return persistencia.guardar(usuarios);
            }
        }

        return false; 
    }
    public Usuario autenticar(String username, String password) {
        if (username == null || password == null) {
            return null;
        }

        Usuario u = buscarPorUsuario(username);
        if (u != null && u.getPassword().equals(password)) {
            return u; 
        }
        return null;
    }
}