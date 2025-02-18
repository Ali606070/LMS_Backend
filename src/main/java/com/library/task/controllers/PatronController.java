package com.library.task.controllers;

import com.library.task.dtos.*;
import com.library.task.services.IPatronService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/patrons")
public class PatronController {

    @Autowired
    private IPatronService patronService;

    @GetMapping
    public ResponseEntity<List<PatronDto>> getAllPatrons() {
        return new ResponseEntity<>(patronService.getAllPatrons(), HttpStatus.OK);
    }

    @GetMapping("/page")
    public ResponseEntity<PageResponse<PatronDto>> getPatronsByPage(@RequestBody PageRequestDto pageRequestDto) {
        Sort sort = Sort.by(Sort.Direction.fromString(pageRequestDto.getSortDirection()), pageRequestDto.getSortBy());
        Pageable pageable = PageRequest.of(pageRequestDto.getPage(), pageRequestDto.getSize(), sort);
        PageResponse<PatronDto> response = patronService.getPatronsByPage(pageable);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
    @GetMapping("/{id}")
    public ResponseEntity<PatronDto> getPatronById(@PathVariable Long id) {
        return new ResponseEntity<>(patronService.getPatronById(id), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<PatronDto> addPatron(@RequestBody PatronCreateDto patronCreateDto) {
        return new ResponseEntity<>(patronService.addPatron(patronCreateDto), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<PatronDto> updatePatron(@PathVariable Long id, @RequestBody PatronUpdateDto patronDto) {
        return new ResponseEntity<>(patronService.updatePatron(id, patronDto), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePatron(@PathVariable Long id) {
        patronService.deletePatron(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}