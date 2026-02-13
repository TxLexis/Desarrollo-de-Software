## Estrategias de Manejo de Excepciones

## Ejercicio 1: Capturar
Validé los datos de entrada y lancé excepciones cuando el usuario envía valores inválidos, 
solo capturé el error de formato en main, porque es el punto donde interactúa el usuario.

## Ejercicio 2: Propagar
Usé try/catch/finally con excepciones específicas,
cada error tiene su propio mensaje y finally garantiza que siempre se ejecute el cierre de la operación.

## Ejercicio 3: Envolver
La excepción checked se propagó con throws hasta main,
decidí manejarla ahí porque representa el borde del sistema y es donde se puede mostrar un mensaje claro al usuario.

## Ejercicio 4: Capturar
Usé try-with-resources para asegurar que el recurso se cierre automáticamente, incluso si ocurre un error, 
esto evita fugas y simplifica el código.

## Ejercicio 5: Propagar
Creé una excepción personalizada para la regla de negocio, 
esto permite expresar mejor el problema y dar información útil sobre lo que falló.

## Ejercicio 6: Envolver
Envolví una excepción de bajo nivel en una excepción de servicio, conservando la causa, 
así se mantiene la trazabilidad entre capas sin exponer detalles internos.

## Ejercicio 7: Capturar
Eliminé el antipatrón de capturar excepciones genéricas, 
en lugar de ocultar el error, dejé que se propague de forma específica.

## Ejercicio 8: Propagar
Identifiqué la raíz del error y agregué validación adecuada, 
evité usar los catches genéricos en el problema.
