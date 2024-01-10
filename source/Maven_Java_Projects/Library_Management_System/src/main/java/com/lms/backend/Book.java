package com.lms.backend;

import java.util.ArrayList;
import java.util.Objects;

public class Book {
    private String title;
    private Author author; 
    private String ISBN; 
    private int year;
    private int edition;
    private String genre;

    private String language;
    private String synopsis;
    private double price;
    
    public Book(String title, Author author, String ISBN, int year, int edition, String genre, String language, String synopsis, double price) {
        this.title = title;
        this.author = author;
        this.ISBN = ISBN;
        this.year = year;
        this.edition = edition;
        this.genre = genre;
        this.language = language;
        this.synopsis = synopsis;
        this.price = price;
    }

    Book() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    public String getTitle() {
        return title;
    }

    public Author getAuthor() {
        return author;
    }

    public String getISBN() {
        return ISBN;
    }

    public int getYear() {
        return year;
    }

    public int getEdition() {
        return edition;
    }

    public String getGenre() {
        return genre;
    }

    public String getLanguage() {
        return language;
    }

    public String getSynopsis() {
        return synopsis;
    }

    public double getPrice() {
        return price;
    }
}