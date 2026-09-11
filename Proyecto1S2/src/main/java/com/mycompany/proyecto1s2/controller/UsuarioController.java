
package com.mycompany.proyecto1s2.controller;

import com.mycompany.proyecto1s2.Reporte.ReporteHTML;
import com.mycompany.proyecto1s2.models.Usuario;
import com.mycompany.proyecto1s2.persistencia.persistencia;

/**
 *
 * @author celad
 */
public class UsuarioController {
    
    //public static Usuario[] usuarios = new Usuario[100];
    public static Usuario[] usuarios = persistencia.cargar();
    public static Usuario login(String usuario, String password){
        if(usuarios == null || usuario == null || password == null){
            return null;
        }
        if(usuario.isEmpty() || password.isEmpty()){
            return null;
        }
        for(Usuario u: usuarios){
        if(u == null){
            continue;
            
        }
        String usuarioGuardado = u.getUsuario();
        String passwordGuardado = u.getPassword();
        
        if(usuarioGuardado == null){
            return null;
        }
        if(usuarioGuardado.equals(usuario) && passwordGuardado.equals(password)){
            return u;
        }
    }
       return null;
    }
    
    public boolean registrarUsuario(Usuario usuario){
        if (usuarios == null || usuario == null){
          return false;
        }
        if(usuario.getUsuario() != null || usuario.getUsuario().trim().isEmpty()){
            return false;
        }
        if (usuario.getPassword() == null || usuario.getPassword().trim().isEmpty()){
            return false;
        }
        if (buscarPorUsuario(usuario.getUsuario()) != null){
            return false;
        }
        for(int i=0; i<usuarios.length; i++){
        if(usuarios[i] == null){
            usuarios[i] = usuario;
            persistencia.guardar(usuarios);
            return true;
        }
        
        }
        return false;
    }
    public Usuario buscarPorUsuario(String usuario){
        if (usuarios == null || usuario == null){
            return null;
        }
        for (Usuario u: usuarios){
            if(u == null || u.getUsuario() == null){
                continue;
            }
            if (u.getUsuario().equals(usuario)){
                
            }
        }
        return null;
    }
    public boolean generarReporteHTML(String ruta){
        return ReporteHTML.generarReporteUsuarios(usuarios, ruta);
    }
    
}
