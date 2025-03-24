package org.example;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Entidad de Tarea
 *
 * @author Alex Gomez
 * @since 23/03/2025
 * @version 1.0
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Tarea {
    private String id;
    private String titulo;
    private String descripcion;
    private Date fechaVencimiento;
    private int nivelPrioridad;
    private boolean estado;

    @Override
    public String toString(){
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
        String fechaFormateada = sdf.format(fechaVencimiento);
        return String.format("%s | %s | %s | %s | %s | %s",
                id, titulo, descripcion,
                fechaFormateada,
                (nivelPrioridad == 1)?"Alta":(nivelPrioridad == 2)?"Media":"Baja",
                (estado)?"Pendiente":"Completada");
    }
}
