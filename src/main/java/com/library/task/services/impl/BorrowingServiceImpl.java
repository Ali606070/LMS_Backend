package com.library.task.services.impl;

import com.library.task.dtos.BorrowingRecordDto;
import com.library.task.exceptions.ConflictException;
import com.library.task.exceptions.ResourceNotFoundException;
import com.library.task.models.Book;
import com.library.task.models.BorrowingRecord;
import com.library.task.models.Patron;
import com.library.task.repositories.BookRepository;
import com.library.task.repositories.BorrowingRecordRepository;
import com.library.task.repositories.PatronRepository;
import com.library.task.services.IBorrowingService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class BorrowingServiceImpl implements IBorrowingService {

    @Autowired
    private BorrowingRecordRepository borrowingRecordRepository;

    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private PatronRepository patronRepository;

    @Autowired
    private ModelMapper modelMapper;

    public BorrowingServiceImpl(BorrowingRecordRepository borrowingRecordRepository, BookRepository bookRepository, PatronRepository patronRepository, ModelMapper modelMapper) {
        this.borrowingRecordRepository = borrowingRecordRepository;
        this.bookRepository = bookRepository;
        this.patronRepository = patronRepository;
        this.modelMapper = modelMapper;
    }



    @Override
    @Transactional
    public BorrowingRecordDto borrowBook(Long bookId, Long patronId) {
        // Check if there's an existing borrowing record for the book and patron with no return date
        borrowingRecordRepository.findByBookIdAndPatronIdAndReturnDateIsNull(bookId, patronId)
                .ifPresent(record -> {
                    throw new ConflictException("You must return the book (ID: " + bookId + ") before borrowing it again.");
                });

        // Fetch the book and patron
        Book book = bookRepository.findById(bookId)
                .orElseThrow(() -> new ResourceNotFoundException("Book not found with id: " + bookId));
        Patron patron = patronRepository.findById(patronId)
                .orElseThrow(() -> new ResourceNotFoundException("Patron not found with id: " + patronId));

        // Create a new borrowing record
        BorrowingRecord record = new BorrowingRecord();
        record.setBook(book);
        record.setPatron(patron);
        record.setBorrowingDate(LocalDate.now());

        // Save and return the new borrowing record
        return modelMapper.map(borrowingRecordRepository.save(record), BorrowingRecordDto.class);
    }

    @Override
    @Transactional(readOnly = true)
    public List<BorrowingRecordDto> getAllBorrowingRecords() {
        return borrowingRecordRepository.findAll().stream()
                .map(record -> modelMapper.map(record, BorrowingRecordDto.class))
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public BorrowingRecordDto getBorrowingRecordById(Long id) {
        BorrowingRecord record = borrowingRecordRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Borrowing record not found with id: " + id));
        return modelMapper.map(record, BorrowingRecordDto.class);
    }

    @Override
    @Transactional
    public BorrowingRecordDto returnBook(Long bookId, Long patronId) {
        BorrowingRecord record = borrowingRecordRepository.findByBookIdAndPatronIdAndReturnDateIsNull(bookId, patronId)
                .orElseThrow(() -> new ResourceNotFoundException("Borrowing record not found with book id: " + bookId + ", patron id: " + patronId + "and return date is null"));
        record.setReturnDate(LocalDate.now());
        return modelMapper.map(borrowingRecordRepository.save(record), BorrowingRecordDto.class);
    }
}
