package service.impl.repo;

import model.Movie;

import java.util.Random;

public class repository {
    private Movie[] ListaFilme = new Movie[0];

    public repository() {}

    public Boolean Add(String film) {
        if(this.ListaFilme.length > 0) {
            Movie[] aux = new Movie[this.ListaFilme.length+1];
            for(int i = 0; i < this.ListaFilme.length; i++)
                if(this.ListaFilme[i].Name().toLowerCase().equals(film.toLowerCase()))
                    return Boolean.FALSE;
                else aux[i] = new Movie(this.ListaFilme[i].id(), this.ListaFilme[i].Name());
            Random rand = new Random();
            aux[aux.length-1] = new Movie(rand.nextLong(), film);
            this.ListaFilme = aux;
        }
        else {
            this.ListaFilme = new Movie[1];
            Random rand = new Random();
            ListaFilme[0] = new Movie(rand.nextLong(), film);
        }
        return Boolean.TRUE;
    }

    public Movie[] Search(String film) {
        Movie[] Rezultat = new Movie[0];
        Boolean Ok = Boolean.FALSE;
        for(int i = 0; i < this.ListaFilme.length; i++) {
            if (this.ListaFilme[i].Name().toLowerCase().contains(film.toLowerCase())) {
                Movie[] aux = new Movie[Rezultat.length+1];
                for(int j = 0; j < Rezultat.length; j++)
                    aux[j] = Rezultat[j];
                aux[aux.length-1] = this.ListaFilme[i];
                Rezultat = aux;
            }
        }
        return Rezultat;
    }

    public Boolean Change(String Film, String film) {
        Random rand = new Random();
        Boolean Ok = Boolean.FALSE;
        for(int i = 0; i < this.ListaFilme.length; i++) {
            if(this.ListaFilme[i].Name().toLowerCase().equals(Film.toLowerCase())) {
                this.ListaFilme[i] = new Movie(rand.nextLong(), film);
                Ok = Boolean.TRUE;
            }
        }
        return Ok;
    }

    public int Delete(String film) {
        if(this.ListaFilme.length > 0) {
            Movie[] aux = new Movie[this.ListaFilme.length - 1];
            int j = 0;
            int Ok = 0;
            for (int i = 0; i < this.ListaFilme.length; i++) {
                if (!this.ListaFilme[i].Name().toLowerCase().equals(film.toLowerCase())) {
                    if(j < aux.length) {
                        aux[j] = this.ListaFilme[i];
                        j += 1;
                    }
                }
                else Ok = 1;
            }
            if(Ok == 0)
                return -1;
            else {
                this.ListaFilme = aux;
                return Ok;
            }
        }
        return 0;
    }
}
