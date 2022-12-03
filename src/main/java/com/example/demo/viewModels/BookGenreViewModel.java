package com.example.demo.viewModels;

import com.example.demo.models.Genre;

import java.util.List;

public class BookGenreViewModel {
    private Iterable<Genre> genres;
    private Genre selectedGenre;

    public BookGenreViewModel(Iterable<Genre> genres) {
        this.genres = genres;
    }

    public Iterable<Genre> getGenres() {
        return genres;
    }

    public void setGenres(Iterable<Genre> genres) {
        this.genres = genres;
    }

    public Genre getSelectedGenre() {
        return selectedGenre;
    }

    public void setSelectedGenre(Genre selectedGenre) {
        this.selectedGenre = selectedGenre;
    }
}
