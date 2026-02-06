package src.main.java.com.reversedots.repository;

import src.main.java.com.reversedots.model.Partida;
import src.main.java.com.reversedots.exception.RepositoryException;
/**
 * Interfaz para el repositorio de partidas.
 */
public interface PartidaRepository {

    /**
     * Guarda una partida en un archivo.
     * @param partida partida a guardar
     * @param rutaArchivo ruta del archivo
     * @throws RepositoryException si ocurre un error al guardar
     */
    void guardar(Partida partida, String rutaArchivo) throws RepositoryException;

    /**
     * Carga una partida desde un archivo.
     * @param rutaArchivo ruta del archivo
     * @return partida cargada
     * @throws RepositoryException si ocurre un error al cargar
     */
    Partida cargar(String rutaArchivo) throws RepositoryException;

    /**
     * Verifica si existe un archivo de partida.
     * @param rutaArchivo ruta del archivo
     * @return true si existe, false en caso contrario
     */
    boolean existe(String rutaArchivo);
}