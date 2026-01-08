package edu.ucr.srp.c24249;

public class VirtualClassroom {
    public void run() {
        // El "Orquestador" une las piezas:
        if (validator.isValid(grade)) {
            String res = evaluator.determineResult(grade);
            diploma.print(name, res);
            if (res.equals("APROBADO")) {
                registry.save(name, grade);
            }
        }
    }
}
