import POJO.Student;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class Main {
    public static void main(String[] args) {
        List<Student> lista1 = Arrays.asList(new Student("Bobică", 15), new Student("Tănase", 16), new Student("Valeriu", 18), new Student("Cătălin", 15), new Student("Andrei", 16));
        List<Student> lista2 = Arrays.asList(new Student("Iohanes", 15), new Student("Becali", 16), new Student("Mureşan", 18), new Student("Mutu", 15), new Student("Florică", 16));
        List<List<Student>> listaDeListe = Arrays.asList(lista1, lista2);

        Map<Integer, List<Student>> map = listaDeListe.stream()
                .flatMap(List::stream)
                .collect(Collectors.groupingBy(Student::getVarsta));

        map.forEach((varsta, studenti) -> {
            System.out.println("Varsta: " + varsta);
            studenti.forEach(student -> System.out.println("Student cu numele: " + student.getNume()));
        });

        List<Student> listaFaraDuplicate = listaDeListe.stream()
                .flatMap(List::stream)
                .distinct()
                .collect(Collectors.toList());

        System.out.println("Lista initiala: " + listaDeListe.stream().mapToInt(List::size).sum());
        System.out.println("Lista fara duplicate: " + listaFaraDuplicate.size());
    }
}