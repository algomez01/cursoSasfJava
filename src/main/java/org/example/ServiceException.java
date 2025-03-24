package org.example;

/**
 * Clase tipo Exception para manipulación de mensajes de alerta dentro del sistema
 *
 * @author Alex Gomez
 * @since 23/03/2025
 * @version 1.0
 */
public class ServiceException extends Exception{
    public ServiceException(String message) {
        super(message);
    }
}
