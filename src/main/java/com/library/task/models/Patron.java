package com.library.task.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;

import java.util.List;

@Data
@Entity
@Builder
public class Patron {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Name is required")
    @Size(max = 50, message = "Name must be less than 50 characters")
    private String name;

    @NotBlank(message = "Contact information is required")
    @Size(max = 100, message = "Contact information must be less than 100 characters")
    private String contactInformation;

    @OneToMany(mappedBy = "patron", cascade = CascadeType.REMOVE, orphanRemoval = true)
    private List<BorrowingRecord> borrowingRecords;

    public Patron(Long id, String name, String contactInformation, List<BorrowingRecord> borrowingRecords) {
        this.id = id;
        this.name = name;
        this.contactInformation = contactInformation;
        this.borrowingRecords = borrowingRecords;
    }

    public Patron() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getContactInformation() {
        return contactInformation;
    }

    public void setContactInformation(String contactInformation) {
        this.contactInformation = contactInformation;
    }

    public List<BorrowingRecord> getBorrowingRecords() {
        return borrowingRecords;
    }

    public void setBorrowingRecords(List<BorrowingRecord> borrowingRecords) {
        this.borrowingRecords = borrowingRecords;
    }
}