package service.impl;

import model.Movie;
import service.MovieService;
import service.impl.repo.repository;

public class MovieServiceImpl implements MovieService {
    private repository repo;

    public MovieServiceImpl(repository repo) {
        this.repo = repo;
    }

    @Override
    public void AddFilm(String Film) {
        if(repo.Add(Film))
            System.out.println("Filmul a fost adaugat cu succes in baza de date!");
        else System.out.println("Filmul selectat exista deja in baza de date, si nu poate fi duplicat!");
    }

    @Override
    public void SearchFilm(String Film) {
        System.out.println("Rezultatele cautarii:");
        Movie[] rez = repo.Search(Film);
        if(rez.length == 0)
            System.out.println("....Nu s-a gasit niciun film....");
        else {
            for(int i = 0; i < rez.length; i++)
                System.out.println(rez[i].Name());
        }
    }

    @Override
    public void ChangeFilm(String ChangeFilm, String ReplaceFilm) {
        if(repo.Change(ChangeFilm, ReplaceFilm))
            System.out.println("Filmul selectat a fost schimbat cu succes!");
        else System.out.println("Filmul selectat nu exista in baza de date!");
    }

    @Override
    public void DeleteFilm(String Film) {
        int Ok = repo.Delete(Film);
        if(Ok == 1) {
            System.out.println("Filmul selectat a fost sters cu succes!");
        }
        else if(Ok == -1) {
            System.out.println("Filmul selectat nu exista in baza de date!");
        }
        else System.out.println("Nu exista niciun film in baza de date!");
    }
}
