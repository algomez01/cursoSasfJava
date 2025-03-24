package org.example;

import java.util.*;
import java.util.stream.Collectors;

/**
 * Clase con métodos de lógica de negocio para las operaciones del sistema
 *
 * @author Alex Gomez
 * @since 23/03/2025
 * @version 1.0
 */
public class TareaService {

    private Utilitario util;
    private List<Tarea> tareas;

    public TareaService() {
        util = new Utilitario();
        tareas = new ArrayList<>();

        generaDataDummy();
    }

    /**
     * Método para procesar opciones del sistema
     * @param opcion {@code int}
     */
    public void procesa(int opcion){

        boolean exitProceso;

        //Ciclo que permite mantenerse en la opcion o regresar al menu
        do
        {
            try
            {
                switch (opcion)
                {
                    case 1:
                        agregar();
                        break;
                    case 2:
                        editar();
                        break;
                    case 3:
                        eliminar();
                        break;
                    case 4:
                        listarTodos();
                        break;
                    case 5:
                        marcarCompletado();
                        break;
                    case 6:
                        ordenarPresentar();
                        break;
                    case 7:
                        buscar();
                        break;
                }
            }
            catch (NumberFormatException ne)
            {
                System.out.println("Error: Formato no válido");
            }
            catch (ServiceException se)
            {
                System.out.println("Alerta: "+se.getMessage());
            }
            catch (Exception ex)
            {
                System.out.println("Error General: " + ex.getMessage());
            }

            exitProceso = util.getBoolean("\nDesea regresar al menu? (S/N): ");
        } while (!exitProceso);
    }

    /**
     * Método para agregar nuevas tareas
     */
    private void agregar(){
        System.out.println("\n==AGREGAR TAREA==");
        Tarea tarea = this.solicitarDatosTarea("");
        this.tareas.add(tarea);

        System.out.println("Tarea Creada con ID: "+tarea.getId());
    }

    /**
     * Método para editar tareas existentes
     */
    private void editar() throws ServiceException{
        System.out.println("\n==EDITAR TAREA==");
        Tarea tarea = buscarPorId();
        if(tarea == null){
            throw new ServiceException("No se ha encontrado la tarea!");
        }

        Tarea tareaModificada = this.solicitarDatosTarea(tarea.getId());
        boolean estado = util.getBoolean("Ingrese el estado \n[1.Pendiente, 2.Completada]:");
        tareaModificada.setEstado(estado);

        int index = this.tareas.indexOf(tarea);
        this.tareas.set(index,tareaModificada);

        System.out.println("Tarea Modificada con ID: "+tareaModificada.getId());
    }

    /**
     * Método general para solicitar datos de una tarea y devolver un objeto.\n
     * Asigna un id si no posee.
     *
     * @param id {@code String}
     * @return {@code Tarea}
     */
    private Tarea solicitarDatosTarea(String id){
        String titulo = util.getString("Ingrese el titulo:");
        String descripcion = util.getString("Ingrese la descripción:");
        Date fechaVencimiento = util.getDate("Ingrese la fecha de vencimiento ", "dd/MM/yyyy");
        int prioridad = util.getInt("Ingrese el nivel de prioridad \n[1.Alto, 2.Medio, 3.Bajo]:",1,3);

        return new Tarea((id.isEmpty())?util.getUUID():id,titulo,descripcion,
                fechaVencimiento,prioridad,true);
    }

    /**
     * Método para eliminar tareas por id
     */
    private void eliminar() throws ServiceException{
        System.out.println("\n==ELIMINAR TAREA==");
        Tarea tarea = buscarPorId();
        if(tarea == null){
            throw new ServiceException("No se ha encontrado la tarea!");
        }

        this.tareas.remove(tarea);
        System.out.println("Tarea Eliminada con ID: "+tarea.getId());
    }

    /**
     * Método general para búsqueda de tareas por id
     * @return {@code Tarea}
     */
    private Tarea buscarPorId(){
        String id = util.getString("Ingrese el ID:");
        return this.tareas.stream().filter(p -> p.getId().equals(id))
                .findFirst().orElse(null);
    }

    /**
     * Método para listar todas las tareas existentes
     */
    private void listarTodos() throws ServiceException{
        System.out.println("\n==LISTAR TAREAS==");
        if(this.tareas.isEmpty()){
            throw new ServiceException("No existen tareas registradas!");
        }
        System.out.println("Total de tareas registradas: " + this.tareas.size());
        System.out.println("ID | TITULO | DESCRIPCION | FECHA VENCIMIENTO | PRIORIDAD | ESTADO");
        for(Tarea tarea: this.tareas){
            System.out.println(tarea);
        }
    }

    /**
     * Método para actualizar el estado de una tarea pendiente a completada
     */
    private void marcarCompletado() throws ServiceException{
        System.out.println("\n==MARCAR TAREA COMPLETADA==");
        Tarea tarea = buscarPorId();
        if(tarea == null){
            throw new ServiceException("No se ha encontrado la tarea!");
        } else if (!tarea.isEstado()) {
            throw new ServiceException("La tarea ya se encuentra Completada!");
        }

        tarea.setEstado(false);

        System.out.println("Tarea Completada con ID: "+ tarea.getId());
    }

    /**
     * Método para ordenar la lista de tareas por fecha de vencimiento o prioridad y
     * listarlos en pantalla
     */
    private void ordenarPresentar(){
        System.out.println("\n==ORDENAR Y PRESENTAR==");
        int datoFiltro = util.getInt("Ordenar por [1.Fecha Vencimiento, 2.Prioridad]:",1,2);
        int ordenFiltro = util.getInt("Ordenar de forma [1.Ascendente, 2.Descendente]:",1,2);

        // Determinar el Comparator basado en la elección del filtro
        Comparator<Tarea> comparator = null;

        //Orden por filtro
        if (datoFiltro == 1) {
            comparator = Comparator.comparing(Tarea::getFechaVencimiento);
        } else if (datoFiltro == 2) {
            comparator = Comparator.comparingInt(Tarea::getNivelPrioridad);
        }

        // Orden de datos
        if (ordenFiltro == 2) {
            comparator = comparator.reversed();
        }

        // Relizar el ordamiento
        Collections.sort(this.tareas, comparator);

        System.out.println("Total de tareas registradas: " + this.tareas.size());
        System.out.println("ID | TITULO | DESCRIPCION | FECHA VENCIMIENTO | PRIORIDAD | ESTADO");
        for (Tarea tarea : this.tareas) {
            System.out.println(tarea);
        }
    }

    /**
     * Método para filtrar tareas por estado y visualizar en pantalla
     */
    private void buscar() throws ServiceException{
        System.out.println("\n==BUSCAR POR ESTADO==");
        boolean pendientes = util.getBoolean("Filtrar por [1.Pendientes, 2.Completadas]:");
        List<Tarea> resultado = null;
        if(pendientes){
            resultado = this.tareas.stream()
                    .filter(Tarea::isEstado)  // Filtra las tareas con estado 'false'
                    .collect(Collectors.toList());
        }else{
            resultado = this.tareas.stream()
                    .filter(p -> !p.isEstado())  // Filtra las tareas con estado 'false'
                    .collect(Collectors.toList());
        }

        if(resultado.isEmpty()){
            throw new ServiceException("No se ha encontrado tareas!");
        }

        System.out.println("Total de tareas registradas: " + resultado.size());
        System.out.println("ID | TITULO | DESCRIPCION | FECHA VENCIMIENTO | PRIORIDAD | ESTADO");
        for (Tarea tarea : resultado) {
            System.out.println(tarea);
        }
    }

    /**
     * Método para cargar datos ficticios (solo en ambiente dev)
     */
    private void generaDataDummy(){
        this.tareas.add(new Tarea(
                util.getUUID(),
                "Revisar informe",
                "Revisar el informe de resultados de ventas",
                new Date(System.currentTimeMillis() + 86400000L),  // Fecha de vencimiento en 1 día
                2,  // Prioridad media
                false  // Estado pendiente
        ));
        this.tareas.add(new Tarea(
                util.getUUID(),
                "Enviar correos",
                "Enviar correos a los clientes sobre la nueva oferta",
                new Date(System.currentTimeMillis() + 172800000L),  // Fecha de vencimiento en 2 días
                3,  // Prioridad alta
                true  // Estado completado
        ));
        this.tareas.add(new Tarea(
                util.getUUID(),
                "Reunión con equipo",
                "Reunión semanal con el equipo de desarrollo",
                new Date(System.currentTimeMillis() + 259200000L),  // Fecha de vencimiento en 3 días
                1,  // Prioridad baja
                false  // Estado pendiente
        ));
        this.tareas.add(new Tarea(
                util.getUUID(),
                "Actualizar base de datos",
                "Actualizar la base de datos con los nuevos registros",
                new Date(System.currentTimeMillis() + 432000000L),  // Fecha de vencimiento en 5 días
                4,  // Prioridad muy alta
                true  // Estado completado
        ));
        this.tareas.add(new Tarea(
                util.getUUID(),
                "Revisar presupuesto",
                "Revisar y aprobar el presupuesto para el siguiente trimestre",
                new Date(System.currentTimeMillis() + 864000000L),  // Fecha de vencimiento en 10 días
                3,  // Prioridad alta
                false  // Estado pendiente
        ));

        System.out.println("Data Dummy Cargada !!!!");
    }
}
