package POJO;

import java.util.Objects;

public class Student {
    private int varsta;
    private String nume;

    public Student(String nume, int varsta) {
        this.varsta = varsta;
        this.nume = nume;
    }

    public int getVarsta() {
        return varsta;
    }

    public String getNume() {
        return nume;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;
        Student student = (Student) o;
        return varsta == student.varsta;
    }

    @Override
    public int hashCode() {
        return Objects.hash(varsta);
    }
}
