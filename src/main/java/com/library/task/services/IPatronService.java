package com.library.task.services;

import com.library.task.dtos.*;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface IPatronService {
    List<PatronDto> getAllPatrons();
    PageResponse<PatronDto> getPatronsByPage(Pageable pageable);

    PatronDto getPatronById(Long id);
    PatronDto addPatron(PatronCreateDto patronCreateDto);
    PatronDto updatePatron(Long id, PatronUpdateDto patronDto);
    void deletePatron(Long id);

}