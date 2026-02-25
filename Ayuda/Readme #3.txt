Swing / GUI
EDT (Event Dispatch Thread): hilo donde Swing crea y actualiza componentes.
( ) Verdadero: La GUI debe crearse con SwingUtilities.invokeLater(...).

JFrame: ventana principal (contenedor de alto nivel).

JPanel: contenedor ligero para agrupar componentes y aplicar layouts.

LayoutManager: objeto que decide cómo se acomodan los componentes (Flow, Border, Grid).

Listener / Evento: una fuente (botón) dispara un evento (ActionEvent) que ejecuta tu código (ActionListener).

JList + DefaultListModel:

JList pinta elementos
DefaultListModel guarda elementos y notifica cambios
( ) Verdadero: si cambias el DefaultListModel, la JList se actualiza.
Archivos (Path/Files) y buffers
Persistencia: guardar datos fuera del programa para que no se pierdan al cerrarlo.

Path: representa una ruta; operaciones se hacen con Files.

Files (NIO.2): clase utilitaria para operaciones modernas sobre archivos (leer, escribir, mover, etc.).

BufferedReader: lee texto por líneas (eficiente por buffer).

BufferedWriter: escribe texto por bloques y con newLine().

Try-with-resources: try (recurso) cierra automáticamente al salir del bloque (pase lo que pase).
( ) Verdadero: sirve para clases AutoCloseable (Reader, Writer, streams).

Excepciones
Excepción: evento que interrumpe el flujo normal y transfiere control a catch o se propaga.

throw: lanza una excepción en ese punto.

throws: declara que un método puede lanzar una excepción (se propaga al llamador).

finally: se ejecuta siempre (haya o no excepción), típico para limpieza.
( ) Verdadero: con try-with-resources, muchas veces no necesitas finally para cerrar archivos.

Checked exception: el compilador obliga a capturar o declarar (IOException).

Unchecked exception: hereda de RuntimeException, no obliga manejo (IllegalArgumentException).

Encapsular excepción: capturar una excepción técnica (ej. IOException) y lanzar una propia con contexto (DataAccessException(msg, cause)).

Verdadero/Falso típico:

( ) Es buena práctica tener catch (Exception e) {} vacío. → Falso
( ) Se debe capturar primero lo específico y luego lo general. → Verdadero
MVC / Capas (aunque no lo evalúen “formal”)
Modelo: datos + reglas (NO conoce Swing).
Vista: UI (Swing), no debe contener lógica de negocio.
Controlador: coordina vista/modelo y maneja flujo.
JSON
JSON: formato de texto de pares clave-valor y arreglos para intercambiar/guardar datos.
Serializar: objeto → JSON.
Deserializar: JSON → objeto.
ObjectMapper (Jackson): herramienta para convertir objetos ↔ JSON.
( ) Verdadero: para leer List<T> normalmente necesitas TypeReference<List<T>>() {}.
JUnit 5
Prueba unitaria: verifica una unidad mínima (método/clase) de forma aislada.
AAA: Arrange (preparar) / Act (ejecutar) / Assert (verificar).
Asserts comunes: assertEquals, assertTrue, assertThrows.
Mockito
Mock: objeto falso para simular dependencias externas.
Stubbing: “cuando llamen X, retorne Y” (when(...).thenReturn(...)).
Verification: verificar llamadas (verify(repo).save(...)).
( ) Verdadero: Mockito se usa para aislar dependencias y no tocar archivos/DB reales en unit tests.
Preguntas tipo “marque con X” (con respuesta)
(X) SwingUtilities.invokeLater asegura que la GUI se ejecute en el EDT.
(X) try-with-resources cierra el archivo aunque ocurra una excepción.
( ) finally solo se ejecuta si hay excepción. → Falso
(X) IOException es checked.
(X) IllegalArgumentException es unchecked.
( ) throw se usa para declarar excepciones en la firma del método. → Falso (eso es throws)
(X) En catch, se debe ordenar de específico a general.
( ) Una prueba unitaria debe usar el sistema de archivos real para ser válida. → Falso
(X) JSON puede representar objetos y arreglos.
(X) DefaultListModel es el “modelo” de datos típico para JList