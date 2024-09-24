package CRUDs;

import POJOs.Notas;
import java.math.BigDecimal;
import java.util.List;

public class NotaService {

    // Este método recibe las notas de una lista
    public BigDecimal calcularPromedioEstudiante(List<Notas> listaNotas) {
        if (listaNotas == null || listaNotas.isEmpty()) {
            System.out.println("El estudiante no tiene notas registradas.");
            return BigDecimal.ZERO;
        }

        BigDecimal sumaTotal = BigDecimal.ZERO;
        int numeroUnidades = listaNotas.size();

        // Suma todas las notas de la lista anterior
        for (Notas nota : listaNotas) {
            if (nota.getNota() != null && nota.getBorradoLogico() == Boolean.FALSE) {
                sumaTotal = sumaTotal.add(nota.getNota());
            }
        }

        // Calcula el promedio
        BigDecimal promedio = sumaTotal.divide(new BigDecimal(numeroUnidades), 2, BigDecimal.ROUND_HALF_UP);

        //Aqui muestra si ha aprobado o no
        if (promedio.compareTo(new BigDecimal(61)) >= 0) {
            System.out.println("El estudiante ha aprobado con un promedio de: " + promedio);
        } else {
            System.out.println("El estudiante ha reprobado con un promedio de: " + promedio);
        }

        return promedio;
    }
}