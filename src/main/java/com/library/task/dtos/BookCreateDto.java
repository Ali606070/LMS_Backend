package com.library.task.dtos;

import jakarta.validation.constraints.*;
import lombok.*;

@Data
@Builder

public class BookCreateDto {
    @NotBlank(message = "Title is required")
    @Size(max = 50, message = "Title must be less than 50 characters")
    private String title;

    @NotBlank(message = "Author is required")
    @Size(max = 50, message = "Author must be less than 50 characters")
    private String author;

    @NotNull(message = "Publication year is required")
    @Min(value = 1500, message = "Publication year must be a valid year")
    @Max(value = 2025, message = "Publication year must be a valid year")
    private Integer publicationYear;

    @NotBlank(message = "ISBN is required")
    @Size(max = 20, message = "ISBN must be less than 20 characters")
    private String isbn;

    public BookCreateDto(String title, String author, Integer publicationYear, String isbn) {
        this.title = title;
        this.author = author;
        this.publicationYear = publicationYear;
        this.isbn = isbn;
    }

    public BookCreateDto() {
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public Integer getPublicationYear() {
        return publicationYear;
    }

    public void setPublicationYear(Integer publicationYear) {
        this.publicationYear = publicationYear;
    }

    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }
}