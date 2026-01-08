package edu.ucr.srp.c24249;

public class ExamProcessor {
    public void process(String name, int grade) {
        // 1. Validar entrada
        if (grade < 0 || grade > 100) {
            System.out.println("Error: Nota inválida");
            return;
        }

        // 2. Lógica de aprobación
        String result = (grade >= 70) ? "APROBADO" : "REPROBADO";

        // 3. Formateo de Diploma
        System.out.println("============================");
        System.out.println("CERTIFICADO DE NOTAS");
        System.out.println("Estudiante: " + name);
        System.out.println("Resultado: " + result);
        System.out.println("============================");

        // 4. "Persistencia" condicional
        if (grade >= 70) {
            System.out.println("Guardando en el registro oficial...");
        }
    }
}
