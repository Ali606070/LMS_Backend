package com.library.task.dtos;

import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.time.LocalDate;

@Data
@Builder

public class BorrowingRecordDto {
    @NotNull(message = "Borrowing record id is required")
    private Long id;
    @NotNull(message = "Book ID is required")
    private Long bookId;
    @NotNull(message = "Patron ID is required")
    private Long patronId;

    @NotNull(message = "Borrowing date is required")
    private LocalDate borrowingDate;

    private LocalDate returnDate;

    public BorrowingRecordDto(Long id, Long bookId, Long patronId, LocalDate borrowingDate, LocalDate returnDate) {
        this.id = id;
        this.bookId = bookId;
        this.patronId = patronId;
        this.borrowingDate = borrowingDate;
        this.returnDate = returnDate;
    }

    public BorrowingRecordDto() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getBookId() {
        return bookId;
    }

    public void setBookId(Long bookId) {
        this.bookId = bookId;
    }

    public Long getPatronId() {
        return patronId;
    }

    public void setPatronId(Long patronId) {
        this.patronId = patronId;
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