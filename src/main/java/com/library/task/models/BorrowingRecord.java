package com.library.task.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;

import java.time.LocalDate;

@Data
@Entity
@Builder
@Table(indexes = {
        @Index(name = "idx_borrowingrecord_book", columnList = "book_id"),
        @Index(name = "idx_borrowingrecord_patron", columnList = "patron_id")
})
public class BorrowingRecord {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull(message = "Book is required")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "book_id", nullable = false)
    private Book book;

    @NotNull(message = "Patron is required")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "patron_id", nullable = false)
    private Patron patron;

    @NotNull(message = "Borrowing date is required")
    private LocalDate borrowingDate;

    private LocalDate returnDate;

    @AssertTrue(message = "Return date must be after borrowing date")
    public boolean isReturnDateValid() {
        return returnDate == null || !returnDate.isBefore(borrowingDate);
    }


    public BorrowingRecord(Long id, Book book, Patron patron, LocalDate borrowingDate, LocalDate returnDate) {
        this.id = id;
        this.book = book;
        this.patron = patron;
        this.borrowingDate = borrowingDate;
        this.returnDate = returnDate;
    }

    public BorrowingRecord() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Book getBook() {
        return book;
    }

    public void setBook(Book book) {
        this.book = book;
    }

    public Patron getPatron() {
        return patron;
    }

    public void setPatron(Patron patron) {
        this.patron = patron;
    }

    public LocalDate getBorrowingDate() {
        return borrowingDate;
    }

    public void setBorrowingDate(LocalDate borrowingDate) {
        this.borrowingDate = borrowingDate;
    }

    public LocalDate getReturnDate() {
        return returnDate;
    }

    public void setReturnDate(LocalDate returnDate) {
        this.returnDate = returnDate;
    }
}