
package com.mycompany.proyecto1s2.models;

/**
 *
 * @author celad
 */
public class Usuario {
    
    int codigo;
    String usuario;
    String password;
    String rol;

    public Usuario(int codigo, String usuario, String password, String rol) {
        this.codigo = codigo;
        this.usuario = usuario;
        this.password = password;
        this.rol = rol;
    }

    public Usuario() {
    }

    public int getCodigo() {
        return codigo;
    }

    public void setCodigo(int codigo) {
        this.codigo = codigo;
    }

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRol() {
        return rol;
    }

    public void setRol(String rol) {
        this.rol = rol;
    }
    
    
    
}
