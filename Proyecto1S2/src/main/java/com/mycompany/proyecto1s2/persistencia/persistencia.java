
package com.mycompany.proyecto1s2.persistencia;

import com.mycompany.proyecto1s2.models.Usuario;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.io.IOException;


public class persistencia {
    private static final String ARCHIVO = "usuarios.txt";
    private static final int CAPACIDAD = 100;

    public static boolean guardar(Usuario[] usuarios) {
        if (usuarios == null) {
            return false;
        }

        try (PrintWriter pw = new PrintWriter(new FileWriter(ARCHIVO))) {
            for (Usuario u : usuarios) {
                if (u != null && u.getUsuario() != null && !u.getUsuario().trim().isEmpty()) {

                    pw.println(u.getCodigo() + "," + u.getUsuario().trim() + "," + u.getPassword().trim() + "," +u.getRol().trim());
                }
            }
            return true;
        } catch (IOException e) {
            System.out.println("Error al guardar usuarios: " + e.getMessage());
            return false;
        }
    }

    public static Usuario[] cargar() {
        Usuario[] usuarios = new Usuario[CAPACIDAD];
        File archivo = new File(ARCHIVO);

       
        if (!archivo.exists()) {
            crearUsuariosBase();
        }

        int contador = 0;
        try (BufferedReader br = new BufferedReader(new FileReader(archivo))) {
            String linea;
            while ((linea = br.readLine()) != null && contador < CAPACIDAD) {
                linea = linea.trim();
                if (linea.isEmpty()) {
                    continue;
                }

             
                String[] datos = linea.split(",");
                if (datos.length == 4) {
                    try {
                        int codigo = Integer.parseInt(datos[0].trim());
                        String user = datos[1].trim();
                        String pass = datos[2].trim();
                        String rol = datos[3].trim();

                        usuarios[contador] = new Usuario(codigo, user, pass, rol);
                        contador++;
                    } catch (NumberFormatException e) {
                        System.out.println("Línea ignorada por formato de código inválido: " + linea);
                    }
                }
            }
        } catch (IOException e) {
            System.out.println("Error al cargar usuarios desde texto: " + e.getMessage());
        }

        return usuarios;
    }

    private static void crearUsuariosBase() {
        try (PrintWriter pw = new PrintWriter(new FileWriter(ARCHIVO))) {
            pw.println("1,admin,admin123,ADMIN");
            pw.println("2,auxiliar,aux123,AUXILIAR");
        } catch (IOException e) {
            System.out.println("Error al crear usuarios predeterminados: " + e.getMessage());
        }
    }
}