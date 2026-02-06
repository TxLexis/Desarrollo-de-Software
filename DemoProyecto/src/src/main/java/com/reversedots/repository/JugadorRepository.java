package src.main.java.com.reversedots.repository;

import src.main.java.com.reversedots.exception.RepositoryException;
import src.main.java.com.reversedots.model.Jugador;

import java.util.List;
import java.util.Optional;

/**
 * Interfaz para el repositorio de jugadores.
 */
public interface JugadorRepository {

    /**
     * Guarda un jugador en el repositorio.
     * @param jugador jugador a guardar
     * @throws RepositoryException si ocurre un error al guardar
     */
    void guardar(Jugador jugador) throws RepositoryException;

    /**
     * Busca un jugador por su nombre.
     * @param nombre nombre del jugador
     * @return Optional con el jugador si existe
     * @throws RepositoryException si ocurre un error al buscar
     */
    Optional<Jugador> buscarPorNombre(String nombre) throws RepositoryException;

    /**
     * Obtiene todos los jugadores.
     * @return lista de jugadores
     * @throws RepositoryException si ocurre un error al listar
     */
    List<Jugador> listarTodos() throws RepositoryException;

    /**
     * Actualiza un jugador existente.
     * @param jugador jugador a actualizar
     * @throws RepositoryException si ocurre un error al actualizar
     */
    void actualizar(Jugador jugador) throws RepositoryException;

    /**
     * Elimina un jugador por su nombre.
     * @param nombre nombre del jugador
     * @throws RepositoryException si ocurre un error al eliminar
     */
    void eliminar(String nombre) throws RepositoryException;

    /**
     * Verifica si existe un jugador con el nombre dado.
     * @param nombre nombre del jugador
     * @return true si existe, false en caso contrario
     * @throws RepositoryException si ocurre un error al verificar
     */
    boolean existe(String nombre) throws RepositoryException;
}