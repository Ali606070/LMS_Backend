package com.library.task.controllers;

import com.library.task.dtos.BookCreateDto;
import com.library.task.dtos.BookDto;
import com.library.task.dtos.BookUpdateDto;
import com.library.task.services.IBookService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

public class BookControllerTest {

    @InjectMocks
    private BookController bookController;

    @Mock
    private IBookService bookService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testGetAllBooks() {
        BookDto book1 = new BookDto(1L, "Book One", "Author One", 2021, "1234567890");
        BookDto book2 = new BookDto(2L, "Book Two", "Author Two", 2022, "0987654321");
        List<BookDto> bookList = Arrays.asList(book1, book2);

        when(bookService.getAllBooks()).thenReturn(bookList);

        ResponseEntity<List<BookDto>> response = bookController.getAllBooks();

        // Assertions
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(2, response.getBody().size()); // Check size
        assertEquals(bookList, response.getBody());
        verify(bookService, times(1)).getAllBooks();
    }

    @Test
    public void testGetBookById() {
        Long bookId = 1L;
        BookDto book = new BookDto(bookId, "Book One", "Author One", 2021, "1234567890");

        when(bookService.getBookById(bookId)).thenReturn(book);

        ResponseEntity<BookDto> response = bookController.getBookById(bookId);

        // Assertions
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(bookId, response.getBody().getId());
        assertEquals("Book One", response.getBody().getTitle());
        assertEquals("Author One", response.getBody().getAuthor());
        verify(bookService, times(1)).getBookById(bookId);
    }

    @Test
    public void testAddBook() {
        BookCreateDto bookCreateDto = new BookCreateDto("Book One", "Author One", 2021, "1234567890");
        BookDto bookDto = new BookDto(1L, "Book One", "Author One", 2021, "1234567890");

        when(bookService.addBook(bookCreateDto)).thenReturn(bookDto);

        ResponseEntity<BookDto> response = bookController.addBook(bookCreateDto);

        // Assertions
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(bookDto, response.getBody());
        assertEquals("Book One", response.getBody().getTitle());
        assertEquals("Author One", response.getBody().getAuthor());
        verify(bookService, times(1)).addBook(bookCreateDto);
    }

    @Test
    public void testUpdateBook() {
        Long bookId = 1L;
        BookUpdateDto bookUpdateDto = new BookUpdateDto(bookId, "Updated Book", "Updated Author", 2022, "1234567890");
        BookDto expectedBookDto = new BookDto(bookId, "Updated Book", "Updated Author", 2022, "1234567890");

        when(bookService.updateBook(bookId, bookUpdateDto)).thenReturn(expectedBookDto);

        ResponseEntity<BookDto> response = bookController.updateBook(bookId, bookUpdateDto);

        // Assertions
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(expectedBookDto, response.getBody());
        assertEquals(bookId, response.getBody().getId());
        assertEquals("Updated Book", response.getBody().getTitle());
        assertEquals("Updated Author", response.getBody().getAuthor());
        verify(bookService, times(1)).updateBook(bookId, bookUpdateDto);
    }

    @Test
    public void testDeleteBook() {
        Long bookId = 1L;

        ResponseEntity<Void> response = bookController.deleteBook(bookId);

        // Assertions
        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
        verify(bookService, times(1)).deleteBook(bookId);
    }
}