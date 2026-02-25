--------- EXCEPCIONES ---------

public class ValidationException extends RuntimeException {
    public ValidationException(String message) {
        super(message);
    }
}


public class DataAccessException extends RuntimeException {
    public DataAccessException(String message, Throwable cause) {
        super(message, cause);
    }
}

--------- REPOSITORIO ---------

INTERFACE:

import model.Item;
import java.util.List;

public interface ItemRepository {
    List<Item> load();          // cargar del archivo
    void save(List<Item> items);// guardar al archivo
}

ARCHIVOS:

import exceptions.DataAccessException;
import model.Item;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public class JsonItemRepository implements ItemRepository {

    private final Path file;

    public JsonItemRepository(Path file) {
        this.file = file;
    }

    @Override
    public List<Item> load() {
        if (Files.notExists(file)) return new ArrayList<>();

        try (BufferedReader br = Files.newBufferedReader(file)) {
            // Aquí parseas JSON -> List<Item>
            // EJ: return mapper.readValue(br, new TypeReference<List<Item>>() {});
            return new ArrayList<>(); // placeholder
        } catch (IOException e) {
            throw new DataAccessException("Error leyendo archivo: " + file, e);
        }
    }

    @Override
    public void save(List<Item> items) {
        try {
            if (file.getParent() != null) Files.createDirectories(file.getParent());
        } catch (IOException e) {
            throw new DataAccessException("Error creando directorio: " + file, e);
        }

        try (BufferedWriter bw = Files.newBufferedWriter(file)) {
            // Aquí serializas List<Item> -> JSON y lo escribes
            // EJ: mapper.writerWithDefaultPrettyPrinter().writeValue(bw, items);
        } catch (IOException e) {
            throw new DataAccessException("Error escribiendo archivo: " + file, e);
        }
    }
}


--------- CONTROLADOR ---------

import exceptions.ValidationException;
import model.EstadoItem;
import model.Item;
import repository.ItemRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class ItemController {

    private final ItemRepository repo;
    private final List<Item> items = new ArrayList<>();

    public ItemController(ItemRepository repo) {
        this.repo = repo;
    }

    public List<Item> getItems() {
        return new ArrayList<>(items); // copia para no exponer lista interna
    }

    public void loadFromFile() {
        items.clear();
        items.addAll(repo.load());
    }

    public void saveToFile() {
        repo.save(items);
    }

    public void add(String nombre, EstadoItem estado) {
        validate(nombre, estado);
        items.add(new Item(UUID.randomUUID().toString(), nombre.trim(), estado));
    }

    public void delete(Item selected) {
        if (selected == null) throw new ValidationException("Selecciona un elemento.");
        items.remove(selected);
    }

    public void update(Item selected, String nuevoNombre, EstadoItem nuevoEstado) {
        if (selected == null) throw new ValidationException("Selecciona un elemento.");
        validate(nuevoNombre, nuevoEstado);
        selected.setNombre(nuevoNombre.trim());
        selected.setEstado(nuevoEstado);
    }

    private void validate(String nombre, EstadoItem estado) {
        if (nombre == null || nombre.isBlank()) throw new ValidationException("Nombre vacío.");
        if (estado == null) throw new ValidationException("Estado requerido.");
    }
}

--------- VISTA ---------

import controller.ItemController;
import exceptions.DataAccessException;
import exceptions.ValidationException;
import model.EstadoItem;
import model.Item;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class MainFrame extends JFrame {

    private final ItemController controller;

    private final DefaultListModel<Item> model = new DefaultListModel<>();
    private final JList<Item> list = new JList<>(model);

    private final JTextField nombreField = new JTextField(20);
    private final JComboBox<EstadoItem> estadoCombo = new JComboBox<>(EstadoItem.values());

    private final JLabel msg = new JLabel(" ");

    public MainFrame(ItemController controller) {
        this.controller = controller;
        initUI();
        initEvents();
        refresh(controller.getItems());
    }

    private void initUI() {
        setTitle("App Examen");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel form = new JPanel(new GridLayout(2, 2, 6, 6));
        form.add(new JLabel("Nombre:"));
        form.add(nombreField);
        form.add(new JLabel("Estado:"));
        form.add(estadoCombo);

        JPanel buttons = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JButton addBtn = new JButton("Agregar");
        JButton updBtn = new JButton("Actualizar");
        JButton delBtn = new JButton("Eliminar");
        JButton saveBtn = new JButton("Guardar");
        JButton loadBtn = new JButton("Cargar");
        buttons.add(addBtn);
        buttons.add(updBtn);
        buttons.add(delBtn);
        buttons.add(saveBtn);
        buttons.add(loadBtn);

        setLayout(new BorderLayout(8, 8));
        add(form, BorderLayout.NORTH);
        add(new JScrollPane(list), BorderLayout.CENTER);

        JPanel south = new JPanel(new BorderLayout());
        south.add(buttons, BorderLayout.NORTH);
        south.add(msg, BorderLayout.SOUTH);
        add(south, BorderLayout.SOUTH);

        pack();
        setLocationRelativeTo(null);

        // Guardamos referencias de botones en variables locales → usar en initEvents con lambdas:
        addBtn.addActionListener(e -> onAdd());
        updBtn.addActionListener(e -> onUpdate());
        delBtn.addActionListener(e -> onDelete());
        saveBtn.addActionListener(e -> onSave());
        loadBtn.addActionListener(e -> onLoad());

        list.addListSelectionListener(e -> {
            if (e.getValueIsAdjusting()) return;
            Item sel = list.getSelectedValue();
            if (sel != null) {
                nombreField.setText(sel.getNombre());
                estadoCombo.setSelectedItem(sel.getEstado());
            }
        });
    }

    private void initEvents() {
        // (en esta plantilla los listeners se conectaron en initUI para ahorrar código)
    }

    private void onAdd() {
        try {
            controller.add(nombreField.getText(), (EstadoItem) estadoCombo.getSelectedItem());
            refresh(controller.getItems());
            setMsgOk("Agregado.");
        } catch (ValidationException ex) {
            setMsgErr(ex.getMessage());
        }
    }

    private void onUpdate() {
        try {
            controller.update(list.getSelectedValue(), nombreField.getText(),
                    (EstadoItem) estadoCombo.getSelectedItem());
            refresh(controller.getItems());
            setMsgOk("Actualizado.");
        } catch (ValidationException ex) {
            setMsgErr(ex.getMessage());
        }
    }

    private void onDelete() {
        try {
            controller.delete(list.getSelectedValue());
            refresh(controller.getItems());
            setMsgOk("Eliminado.");
        } catch (ValidationException ex) {
            setMsgErr(ex.getMessage());
        }
    }

    private void onSave() {
        try {
            controller.saveToFile();
            setMsgOk("Guardado en archivo.");
        } catch (DataAccessException ex) {
            setMsgErr(ex.getMessage());
        }
    }

    private void onLoad() {
        try {
            controller.loadFromFile();
            refresh(controller.getItems());
            setMsgOk("Cargado desde archivo.");
        } catch (DataAccessException ex) {
            setMsgErr(ex.getMessage());
        }
    }

    private void refresh(List<Item> items) {
        model.clear();
        for (Item i : items) model.addElement(i);
    }

    private void setMsgOk(String text) {
        msg.setForeground(new Color(20, 80, 160));
        msg.setText(text);
    }

    private void setMsgErr(String text) {
        msg.setForeground(new Color(160, 30, 30));
        msg.setText(text);
    }
}

--------- MAIN ---------

import controller.ItemController;
import repository.ItemRepository;
import repository.JsonItemRepository;

import javax.swing.*;
import java.nio.file.Path;

public class Main {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            ItemRepository repo = new JsonItemRepository(Path.of("data", "items.json"));
            ItemController controller = new ItemController(repo);
            new MainFrame(controller).setVisible(true);
        });
    }
}