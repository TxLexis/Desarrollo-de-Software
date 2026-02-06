package src.main.java.com.reversedots.repository;

import src.main.java.com.reversedots.exception.RepositoryException;
import src.main.java.com.reversedots.model.Jugador;
import src.main.java.com.reversedots.repository.JugadorRepository;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Implementación del repositorio de jugadores usando archivos de texto.
 */
public class JugadorRepositoryImpl implements JugadorRepository {

    private static final String ARCHIVO_JUGADORES = "data/jugadores.txt";
    private static final String SEPARADOR = ",";

    /**
     * Constructor que inicializa el repositorio.
     */
    public JugadorRepositoryImpl() {
        try {
            inicializarArchivo();
        } catch (IOException e) {
            System.err.println("Error al inicializar el repositorio de jugadores: " + e.getMessage());
        }
    }

    /**
     * Inicializa el archivo de jugadores si no existe.
     */
    private void inicializarArchivo() throws IOException {
        Path path = Paths.get(ARCHIVO_JUGADORES);
        if (!Files.exists(path)) {
            Files.createDirectories(path.getParent());
            Files.createFile(path);
        }
    }

    @Override
    public void guardar(Jugador jugador) throws RepositoryException {
        try {
            if (existe(jugador.getNombre())) {
                throw new RepositoryException("Ya existe un jugador con el nombre: " + jugador.getNombre());
            }

            try (BufferedWriter writer = new BufferedWriter(new FileWriter(ARCHIVO_JUGADORES, true))) {
                writer.write(jugadorALinea(jugador));
                writer.newLine();
            }
        } catch (IOException e) {
            throw new RepositoryException("Error al guardar el jugador", e);
        }
    }

    @Override
    public Optional<Jugador> buscarPorNombre(String nombre) throws RepositoryException {
        try {
            List<Jugador> jugadores = listarTodos();
            return jugadores.stream()
                    .filter(j -> j.getNombre().equalsIgnoreCase(nombre))
                    .findFirst();
        } catch (Exception e) {
            throw new RepositoryException("Error al buscar el jugador", e);
        }
    }

    @Override
    public List<Jugador> listarTodos() throws RepositoryException {
        List<Jugador> jugadores = new ArrayList<>();

        try (BufferedReader reader = new BufferedReader(new FileReader(ARCHIVO_JUGADORES))) {
            String linea;
            while ((linea = reader.readLine()) != null) {
                if (!linea.trim().isEmpty()) {
                    jugadores.add(lineaAJugador(linea));
                }
            }
        } catch (FileNotFoundException e) {
            // Si no existe el archivo, retornamos lista vacía
            return jugadores;
        } catch (IOException e) {
            throw new RepositoryException("Error al listar jugadores", e);
        }

        return jugadores;
    }

    @Override
    public void actualizar(Jugador jugador) throws RepositoryException {
        try {
            List<Jugador> jugadores = listarTodos();

            boolean encontrado = false;
            for (int i = 0; i < jugadores.size(); i++) {
                if (jugadores.get(i).getNombre().equalsIgnoreCase(jugador.getNombre())) {
                    jugadores.set(i, jugador);
                    encontrado = true;
                    break;
                }
            }

            if (!encontrado) {
                throw new RepositoryException("No se encontró el jugador: " + jugador.getNombre());
            }

            guardarTodos(jugadores);
        } catch (IOException e) {
            throw new RepositoryException("Error al actualizar el jugador", e);
        }
    }

    @Override
    public void eliminar(String nombre) throws RepositoryException {
        try {
            List<Jugador> jugadores = listarTodos();
            jugadores = jugadores.stream()
                    .filter(j -> !j.getNombre().equalsIgnoreCase(nombre))
                    .collect(Collectors.toList());

            guardarTodos(jugadores);
        } catch (IOException e) {
            throw new RepositoryException("Error al eliminar el jugador", e);
        }
    }

    @Override
    public boolean existe(String nombre) throws RepositoryException {
        return buscarPorNombre(nombre).isPresent();
    }

    /**
     * Guarda todos los jugadores en el archivo.
     */
    private void guardarTodos(List<Jugador> jugadores) throws IOException {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(ARCHIVO_JUGADORES))) {
            for (Jugador jugador : jugadores) {
                writer.write(jugadorALinea(jugador));
                writer.newLine();
            }
        }
    }

    /**
     * Convierte un jugador a una línea de texto.
     */
    private String jugadorALinea(Jugador jugador) {
        return String.join(SEPARADOR,
                jugador.getNombre(),
                String.valueOf(jugador.getPartidasGanadas()),
                String.valueOf(jugador.getPartidasPerdidas()),
                String.valueOf(jugador.getPartidasEmpatadas())
        );
    }

    /**
     * Convierte una línea de texto a un jugador.
     */
    private Jugador lineaAJugador(String linea) {
        String[] partes = linea.split(SEPARADOR);
        if (partes.length != 4) {
            throw new IllegalArgumentException("Formato de línea inválido: " + linea);
        }

        return new Jugador(
                partes[0],
                Integer.parseInt(partes[1]),
                Integer.parseInt(partes[2]),
                Integer.parseInt(partes[3])
        );
    }
}