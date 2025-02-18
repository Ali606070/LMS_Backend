package com.library.task.services.impl;

import com.library.task.dtos.BookCreateDto;
import com.library.task.dtos.BookDto;
import com.library.task.dtos.BookUpdateDto;
import com.library.task.dtos.PageResponse;
import com.library.task.exceptions.BadRequestException;
import com.library.task.exceptions.ResourceNotFoundException;
import com.library.task.models.Book;
import com.library.task.repositories.BookRepository;
import com.library.task.services.IBookService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.util.List;
import java.util.stream.Collectors;

@Service
public class BookServiceImpl implements IBookService {

    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private ModelMapper modelMapper;

    public BookServiceImpl(BookRepository bookRepository, ModelMapper modelMapper) {
        this.bookRepository = bookRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    @Transactional(readOnly = true)
    public List<BookDto> getAllBooks() {
        return bookRepository.findAll().stream()
                .map(book -> modelMapper.map(book, BookDto.class))
                .collect(Collectors.toList());
    }
    @Override
    @Transactional(readOnly = true)
    @Cacheable(value = "books")
    public PageResponse<BookDto> getBooksByPage(Pageable pageable) {
        Page<Book> bookPage = bookRepository.findAll(pageable);
        List<BookDto> bookDtos = bookPage.getContent().stream()
                .map(book -> modelMapper.map(book, BookDto.class))
                .collect(Collectors.toList());

        return new PageResponse<>(
                bookDtos,
                bookPage.getNumber(),
                bookPage.getSize(),
                bookPage.getTotalElements(),
                bookPage.getTotalPages()
        );
    }
    @Override
    @Transactional(readOnly = true)
    @Cacheable(value = "books", key = "#id")
    public BookDto getBookById(Long id) {
        Book book = bookRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Book not found with id: " + id));
        return modelMapper.map(book, BookDto.class);
    }

    @Override
    @Transactional
    @CachePut(value = "books", key = "#result.id")
    public BookDto addBook(BookCreateDto bookCreateDto) {
        Book book = modelMapper.map(bookCreateDto, Book.class);
        return modelMapper.map(bookRepository.save(book), BookDto.class);
    }

    @Override
    @Transactional
    @CachePut(value = "books", key = "#id")
    public BookDto updateBook(Long id, BookUpdateDto bookDto) {
        if (!id.equals(bookDto.getId())) {
            throw new BadRequestException("ID in path and request body do not match");
        }

        Book book = bookRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Book not found with id: " + id));
        modelMapper.map(bookDto, book); // Update existing entity with DTO data
        return modelMapper.map(bookRepository.save(book), BookDto.class);
    }

    @Override
    @Transactional
    @CacheEvict(value = "books", key = "#id")
    public void deleteBook(Long id) {
        if (!bookRepository.existsById(id)) {
            throw new ResourceNotFoundException("Book not found with id: " + id);
        }
        bookRepository.deleteById(id);
    }
}