package org.example;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Scanner;
import java.util.UUID;

/**
 * Clase de métodos útilitarios generales
 *
 * @author Alex Gomez
 * @since 23/03/2025
 * @version 1.0
 */
public class Utilitario {
    Scanner scanner = new Scanner(System.in);

    /**
     * Método para obtener una cadena de texto
     * @param mensaje {@code String}
     */
    public String getString(String mensaje) {
        System.out.println(mensaje);
        String valor = "";
        while(valor.isEmpty()){
            valor = scanner.nextLine();
        }
        return valor;
    }

    /**
     * Método para obtener un valor de tipo int
     * @param mensaje {@code String}
     */
    public int getInt(String mensaje) {
        System.out.println(mensaje);
        while (!scanner.hasNextInt()) {
            System.out.println("Por favor ingrese un número entero válido.");
            scanner.next();
        }
        return scanner.nextInt();
    }

    /**
     * Método para obtener un valor de tipo int de un rango de valores
     * @param mensaje {@code String}
     * @param valorMinimo {@code int}
     * @param valorMaximo {@code int}
     */
    public int getInt(String mensaje, int valorMinimo, int valorMaximo) {
        boolean isvalid = false;
        int valor = 0;
        while (!isvalid) {
            valor = this.getInt(mensaje);
            if(valor < valorMinimo || valor > valorMaximo){
                System.out.println("El número debe ser entre el "+valorMinimo+" y "+valorMaximo+".");
            }else{
                isvalid = true;
            }
        }
        return valor;
    }

    /**
     * Método para obtener un valor de tipo boolean
     * @param mensaje {@code String}
     */
    public boolean getBoolean(String mensaje) {
        while (true) {
            String input = this.getString(mensaje).toLowerCase();
            if (input.equalsIgnoreCase("s") || input.equals("1")) {
                return true;
            } else if (input.equalsIgnoreCase("n") || input.equals("2")) {
                return false;
            } else {
                System.out.println("Opción no válida.");
            }
        }
    }

    /**
     * Método para obtener una fecha desde el usuario en formato específico
     * @param mensaje {@code String}
     * @param formato {@code String}
     */
    public Date getDate(String mensaje, String formato) {
        SimpleDateFormat sdf = new SimpleDateFormat(formato);
        Date fecha = null;

        // Continuar pidiendo la fecha hasta que sea válida
        while (fecha == null) {
            String input = this.getString(mensaje+"("+formato+"):");

            try {
                fecha = sdf.parse(input);
            } catch (Exception e) {
                System.out.println("Formato de fecha inválido. Debe ser en el formato: " + formato);
            }
        }

        return fecha;
    }

    /**
     * Método para generar un UUID
     */
    public String getUUID() {
        UUID uuid = UUID.randomUUID();
        return uuid.toString();
    }
}

