package service;

public interface MovieService {
    void AddFilm(String Film);
    void SearchFilm(String Film);
    void DeleteFilm(String Film);

    void ChangeFilm(String ChangeFilm, String ReplaceFilm);
}
