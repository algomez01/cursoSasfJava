package org.example;

import java.util.HashMap;
import java.util.Map;

/**
 * Clase principal para incialización del menú del sistema
 *
 * @author Alex Gomez
 * @since 23/03/2025
 * @version 1.0
 */
public class Main {
    public static void main(String[] args) {
        menu();
    }

    /**
     * Método para presentación del menú del sistema
     */
    public static void menu(){
        boolean exitMenu = false;
        Utilitario util = new Utilitario();

        TareaService service = new TareaService();
        Map<Integer, String> opciones = new HashMap<>();
        opciones.put(1,"Agregar");
        opciones.put(2,"Editar");
        opciones.put(3,"Eliminar");
        opciones.put(4,"Listar todas");
        opciones.put(5,"Marcar como completada");
        opciones.put(6,"Ordenar y presentar");
        opciones.put(7,"Buscar por estado");
        opciones.put(8,"Salir");

        do{
            try{
                System.out.println("\n****MENU - ADMINISTRADOR DE TAREAS****");

                for(Map.Entry opcion: opciones.entrySet()){
                    System.out.println(opcion.getKey() + ". " + opcion.getValue());
                };

                int seleccion = util.getInt("Ingrese una opción:",1,8);

                if(seleccion == 8)
                {
                    exitMenu = true;
                }
                else
                {
                    service.procesa(seleccion);
                }

            }catch (NumberFormatException ex){
                System.out.println("Error: Formato no válido");
            }catch (Exception e){
                System.out.println("Error General: " + e.getMessage());
            }
        }while (!exitMenu);
    }
}