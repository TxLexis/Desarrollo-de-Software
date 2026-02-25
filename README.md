Estructura de carpetas:
-----------------------
src/
  model/
    Item.java
    EstadoItem.java
  exceptions/
    ValidationException.java
    DataAccessException.java
  repository/
    ItemRepository.java
    CsvItemRepository.java
  controller/
    ItemController.java
  view/
    MainFrame.java
    Main.java
test/
  controller/
    ItemControllerTest.java
    ItemControllerMockitoTest.java

========================
EXCEPTIONS
========================

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


========================
REPOSITORY
========================

INTERFACE:

    import model.Item;
    import java.util.List;
    
    public interface ItemRepository {
        List<Item> load();
        void save(List<Item> items);
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
    
    public class CsvItemRepository implements ItemRepository {

    private final Path file;

    public CsvItemRepository(Path file) {
        this.file = file;
    }

    @Override
    public List<Item> load() {
        if (Files.notExists(file)) return new ArrayList<>();

        List<Item> items = new ArrayList<>();
        try (BufferedReader br = Files.newBufferedReader(file)) {
            String line;
            int lineNo = 0;
            while ((line = br.readLine()) != null) {
                lineNo++;
                if (line.isBlank()) continue;

                try {
                    items.add(Item.fromCSV(line));
                } catch (Exception parseEx) {
                    // Puedes decidir: ignorar línea mala o parar.
                    // En examen, lo más robusto es fallar con contexto:
                    throw new DataAccessException("CSV corrupto en línea " + lineNo + ": " + line, parseEx);
                }
            }
            return items;
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
            for (Item item : items) {
                bw.write(item.toCSV());
                bw.newLine();
            }
        } catch (IOException e) {
            throw new DataAccessException("Error escribiendo archivo: " + file, e);
        }
    }
}


========================
 CONTROLLER
========================

    import exceptions.ValidationException;
    import model.EstadoItem;
    import model.Item;
    import repository.ItemRepository;
    
    import java.util.ArrayList;
    import java.util.List;
    import java.util.Objects;
    import java.util.UUID;
    
    public class ItemController {

    private final ItemRepository repo;
    private final List<Item> items = new ArrayList<>();

    public ItemController(ItemRepository repo) {
        this.repo = Objects.requireNonNull(repo);
    }

    public List<Item> getItems() {
        return new ArrayList<>(items); // copia defensiva
    }

    public void loadFromFile() {
        items.clear();
        items.addAll(repo.load());
    }

    public void saveToFile() {
        repo.save(items);
    }

    public Item add(String nombre, EstadoItem estado) {
        validate(nombre, estado);
        Item item = new Item(UUID.randomUUID().toString(), nombre.trim(), estado);
        items.add(item);
        return item;
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
        if (nombre.contains(",")) throw new ValidationException("El nombre no puede contener comas (CSV).");
        if (estado == null) throw new ValidationException("Estado requerido.");
    }
}


========================
VIEW (Swing)
========================


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

    private final JButton addBtn = new JButton("Agregar");
    private final JButton updBtn = new JButton("Actualizar");
    private final JButton delBtn = new JButton("Eliminar");
    private final JButton saveBtn = new JButton("Guardar");
    private final JButton loadBtn = new JButton("Cargar");

    public MainFrame(ItemController controller) {
        this.controller = controller;
        initUI();
        initEvents();
        refresh(controller.getItems());
    }

    private void initUI() {
        setTitle("App Examen (Plantilla)");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel form = new JPanel(new GridLayout(2, 2, 6, 6));
        form.add(new JLabel("Nombre:"));
        form.add(nombreField);
        form.add(new JLabel("Estado:"));
        form.add(estadoCombo);

        JPanel buttons = new JPanel(new FlowLayout(FlowLayout.LEFT));
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
    }

    private void initEvents() {
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

    private void onAdd() {
        try {
            controller.add(nombreField.getText(), (EstadoItem) estadoCombo.getSelectedItem());
            refresh(controller.getItems());
            clearForm();
            setMsgOk("Agregado.");
        } catch (ValidationException ex) {
            setMsgErr(ex.getMessage());
        }
    }

    private void onUpdate() {
        try {
            controller.update(
                    list.getSelectedValue(),
                    nombreField.getText(),
                    (EstadoItem) estadoCombo.getSelectedItem()
            );
            refresh(controller.getItems());
            setMsgOk("Actualizado.");
        } catch (ValidationException ex) {
            setMsgErr(ex.getMessage());
        }
    }

    private void onDelete() {
        Item sel = list.getSelectedValue();
        if (sel == null) {
            setMsgErr("Selecciona un elemento.");
            return;
        }

        int r = JOptionPane.showConfirmDialog(
                this,
                "¿Eliminar el elemento seleccionado?",
                "Confirmar",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.WARNING_MESSAGE
        );
        if (r != JOptionPane.YES_OPTION) return;

        try {
            controller.delete(sel);
            refresh(controller.getItems());
            clearForm();
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
            clearForm();
            setMsgOk("Cargado desde archivo.");
        } catch (DataAccessException ex) {
            setMsgErr(ex.getMessage());
        }
    }

    private void refresh(List<Item> items) {
        model.clear();
        for (Item i : items) model.addElement(i);
    }

    private void clearForm() {
        nombreField.setText("");
        estadoCombo.setSelectedIndex(0);
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

========================
MAIN
========================
    import controller.ItemController;
    import repository.CsvItemRepository;
    import repository.ItemRepository;
    
    import javax.swing.*;
    import java.nio.file.Path;
    
    public class Main {
        public static void main(String[] args) {
            SwingUtilities.invokeLater(() -> {
                ItemRepository repo = new CsvItemRepository(Path.of("data", "items.csv"));
                ItemController controller = new ItemController(repo);
                new MainFrame(controller).setVisible(true);
            });
        }
    }


========================
UNIT TESTS (JUnit 5 + Mockito)
========================

NOTA: Estos tests prueban el Controller (sin Swing) => rápido y correcto para examen.

--------------------------------
JUnit 5
--------------------------------

    import exceptions.ValidationException;
    import model.EstadoItem;
    import org.junit.jupiter.api.Test;
    import repository.ItemRepository;
    
    import java.util.List;
    
    import static org.junit.jupiter.api.Assertions.*;
    
    class ItemControllerTest {

    // Repository "fake" simple en memoria para NO tocar archivos (unit test real)
    static class InMemoryRepo implements ItemRepository {
        List<model.Item> stored = List.of();

        @Override
        public List<model.Item> load() {
            return stored;
        }

        @Override
        public void save(List<model.Item> items) {
            stored = List.copyOf(items);
        }
    }

    @Test
    void add_whenNameBlank_throwsValidationException() {
        InMemoryRepo repo = new InMemoryRepo();
        ItemController controller = new ItemController(repo);

        assertThrows(ValidationException.class, () -> controller.add("   ", EstadoItem.A));
    }

    @Test
    void add_whenValid_increasesList() {
        InMemoryRepo repo = new InMemoryRepo();
        ItemController controller = new ItemController(repo);

        controller.add("Item 1", EstadoItem.B);

        assertEquals(1, controller.getItems().size());
        assertEquals("Item 1", controller.getItems().get(0).getNombre());
        assertEquals(EstadoItem.B, controller.getItems().get(0).getEstado());
    }

    @Test
    void saveToFile_callsRepositorySave() {
        InMemoryRepo repo = new InMemoryRepo();
        ItemController controller = new ItemController(repo);

        controller.add("X", EstadoItem.C);
        controller.saveToFile();

        assertEquals(1, repo.stored.size());
        assertEquals("X", repo.stored.get(0).getNombre());
    }
}

--------------------------------
JUnit 5 + Mockito
--------------------------------
    package controller;
    
    import model.EstadoItem;
    import model.Item;
    import org.junit.jupiter.api.Test;
    import org.junit.jupiter.api.extension.ExtendWith;
    import org.mockito.ArgumentCaptor;
    import org.mockito.junit.jupiter.MockitoExtension;
    import repository.ItemRepository;
    
    import java.util.List;
    
    import static org.junit.jupiter.api.Assertions.*;
    import static org.mockito.Mockito.*;
    
    @ExtendWith(MockitoExtension.class)
    class ItemControllerMockitoTest {

    @Test
    void saveToFile_whenHasItems_callsRepoSaveWithSameSize() {
        ItemRepository repo = mock(ItemRepository.class);
        ItemController controller = new ItemController(repo);

        controller.add("A", EstadoItem.A);
        controller.add("B", EstadoItem.B);

        controller.saveToFile();

        ArgumentCaptor<List<Item>> captor = ArgumentCaptor.forClass(List.class);
        verify(repo).save(captor.capture());

        List<Item> saved = captor.getValue();
        assertEquals(2, saved.size());
        assertEquals("A", saved.get(0).getNombre());
        assertEquals("B", saved.get(1).getNombre());

        verifyNoMoreInteractions(repo);
    }

    @Test
    void loadFromFile_replacesInMemoryList() {
        ItemRepository repo = mock(ItemRepository.class);
        when(repo.load()).thenReturn(List.of(
                new Item("1", "Cargado", EstadoItem.C)
        ));

        ItemController controller = new ItemController(repo);
        controller.add("Temporal", EstadoItem.A);

        controller.loadFromFile();

        assertEquals(1, controller.getItems().size());
        assertEquals("Cargado", controller.getItems().get(0).getNombre());
        verify(repo).load();
        verifyNoMoreInteractions(repo);
    }
}


========================
NOTA FINAL: si mañana te piden JSON en vez de CSV
========================

- No cambias Controller ni View.
- Solo cambias el repository por uno JSON.
- Mantienes ItemRepository igual.

JsonItemRepository (idea):
- private final ObjectMapper mapper = new ObjectMapper();
- load(): mapper.readValue(reader, new TypeReference<List<Item>>() {})
- save(): mapper.writerWithDefaultPrettyPrinter().writeValue(writer, items)

Si me confirmas que sí hay Jackson/Gson en el entorno del examen, te paso ese repository listo.
