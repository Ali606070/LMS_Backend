package com.library.task.services;

import com.library.task.dtos.BookCreateDto;
import com.library.task.dtos.BookDto;
import com.library.task.dtos.BookUpdateDto;
import com.library.task.dtos.PageResponse;

import org.springframework.data.domain.Pageable;
import java.util.List;

public interface IBookService {
    List<BookDto> getAllBooks();
    BookDto getBookById(Long id);
    BookDto addBook(BookCreateDto bookCreateDto);
    BookDto updateBook(Long id, BookUpdateDto bookUpdateDto);
    void deleteBook(Long id);
    PageResponse<BookDto> getBooksByPage(Pageable pageable);
}
