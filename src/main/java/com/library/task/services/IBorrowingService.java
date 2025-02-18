package com.library.task.services;

import com.library.task.dtos.BorrowingRecordDto;

import java.util.List;

public interface IBorrowingService {
    BorrowingRecordDto borrowBook(Long bookId, Long patronId);
    BorrowingRecordDto returnBook(Long bookId, Long patronId);
    List<BorrowingRecordDto> getAllBorrowingRecords();
    BorrowingRecordDto getBorrowingRecordById(Long id);
}
