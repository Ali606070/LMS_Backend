package com.library.task.controllers;

import com.library.task.dtos.BorrowingRecordDto;
import com.library.task.services.IBorrowingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class BorrowingController {

    @Autowired
    private IBorrowingService borrowingService;

    @GetMapping("/borrow")
    public ResponseEntity<List<BorrowingRecordDto>> getAllBorrowingRecords() {
        return new ResponseEntity<>(borrowingService.getAllBorrowingRecords(), HttpStatus.OK);
    }


    @GetMapping("/borrow/{id}")
    public ResponseEntity<BorrowingRecordDto> getBorrowingRecordById(@PathVariable Long id) {
        return new ResponseEntity<>(borrowingService.getBorrowingRecordById(id), HttpStatus.OK);
    }
    @PostMapping("/borrow/{bookId}/patron/{patronId}")
    public ResponseEntity<BorrowingRecordDto> borrowBook(@PathVariable Long bookId, @PathVariable Long patronId) {
        return new ResponseEntity<>(borrowingService.borrowBook(bookId, patronId), HttpStatus.CREATED);
    }

    @PutMapping("/return/{bookId}/patron/{patronId}")
    public ResponseEntity<BorrowingRecordDto> returnBook(@PathVariable Long bookId, @PathVariable Long patronId) {
        return new ResponseEntity<>(borrowingService.returnBook(bookId, patronId), HttpStatus.OK);
    }
}
