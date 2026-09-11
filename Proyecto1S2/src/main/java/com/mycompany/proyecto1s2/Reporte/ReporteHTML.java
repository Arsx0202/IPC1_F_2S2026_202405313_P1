
package com.mycompany.proyecto1s2.Reporte;

import com.mycompany.proyecto1s2.models.Usuario;

/**
 *
 * @author celad
 */
public class ReporteHTML {
    
    public static boolean generarReporteUsuarios(Usuario[] usuarios, String ruta){
        if(usuarios == null || ruta == null || ruta.trim().isEmpty()){
            return false;
        }
        StringBuilder html = new StringBuilder();
        
        
    }
}
