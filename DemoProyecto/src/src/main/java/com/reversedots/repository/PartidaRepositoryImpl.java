package src.main.java.com.reversedots.repository;

import src.main.java.com.reversedots.exception.RepositoryException;
import src.main.java.com.reversedots.model.Partida;
import src.main.java.com.reversedots.repository.PartidaRepository;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * Implementación del repositorio de partidas usando serialización.
 */
public class PartidaRepositoryImpl implements PartidaRepository {

    @Override
    public void guardar(Partida partida, String rutaArchivo) throws RepositoryException {
        try {
            Path path = Paths.get(rutaArchivo);

            // Crear directorios si no existen
            if (path.getParent() != null) {
                Files.createDirectories(path.getParent());
            }

            try (ObjectOutputStream oos = new ObjectOutputStream(
                    new FileOutputStream(rutaArchivo))) {
                oos.writeObject(partida);
            }
        } catch (IOException e) {
            throw new RepositoryException("Error al guardar la partida", e);
        }
    }

    @Override
    public Partida cargar(String rutaArchivo) throws RepositoryException {
        try {
            if (!existe(rutaArchivo)) {
                throw new RepositoryException("El archivo no existe: " + rutaArchivo);
            }

            try (ObjectInputStream ois = new ObjectInputStream(
                    new FileInputStream(rutaArchivo))) {
                return (Partida) ois.readObject();
            }
        } catch (IOException | ClassNotFoundException e) {
            throw new RepositoryException("Error al cargar la partida", e);
        }
    }

    @Override
    public boolean existe(String rutaArchivo) {
        return Files.exists(Paths.get(rutaArchivo));
    }
}
