package com.library.task.services.impl;

import com.library.task.dtos.*;
import com.library.task.exceptions.BadRequestException;
import com.library.task.exceptions.ResourceNotFoundException;
import com.library.task.models.Book;
import com.library.task.models.Patron;
import com.library.task.repositories.PatronRepository;
import com.library.task.services.IPatronService;
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
public class PatronServiceImpl implements IPatronService {

    @Autowired
    private PatronRepository patronRepository;

    @Autowired
    private ModelMapper modelMapper;

    public PatronServiceImpl(PatronRepository patronRepository, ModelMapper modelMapper) {
        this.patronRepository = patronRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    @Cacheable(value = "patrons")
    public List<PatronDto> getAllPatrons() {
        return patronRepository.findAll().stream()
                .map(patron -> modelMapper.map(patron, PatronDto.class))
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    @Cacheable(value = "patrons", key = "#id")
    public PatronDto getPatronById(Long id) {
        Patron patron = patronRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Patron not found with id: " + id));
        return modelMapper.map(patron, PatronDto.class);
    }

    @Override
    @Transactional
    @CachePut(value = "patrons", key = "#result.id")
    public PatronDto addPatron(PatronCreateDto patronCreateDto) {
        Patron patron = modelMapper.map(patronCreateDto, Patron.class);
        return modelMapper.map(patronRepository.save(patron), PatronDto.class);
    }

    @Override
    @Transactional
    @CachePut(value = "patrons", key = "#id")
    public PatronDto updatePatron(Long id, PatronUpdateDto patronDto) {
        if (!id.equals(patronDto.getId())) {
            throw new BadRequestException("ID in path and request body do not match");
        }

        Patron patron = patronRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Patron not found with id: " + id));
        modelMapper.map(patronDto, patron);
        return modelMapper.map(patronRepository.save(patron), PatronDto.class);
    }

    @Override
    @Transactional
    @CacheEvict(value = "patrons", key = "#id")
    public void deletePatron(Long id) {
        if (!patronRepository.existsById(id)) {
           throw new ResourceNotFoundException("Patron not found with id: " + id);
        }
        patronRepository.deleteById(id);
    }

    @Override
    @Transactional(readOnly = true)
    @Cacheable(value = "patrons")
    public PageResponse<PatronDto> getPatronsByPage(Pageable pageable) {
        Page<Patron> patronPage = patronRepository.findAll(pageable);
        List<PatronDto> patronDtos = patronPage.getContent().stream()
                .map(patron -> modelMapper.map(patron, PatronDto.class))
                .collect(Collectors.toList());

        return new PageResponse<>(
                patronDtos,
                patronPage.getNumber(),
                patronPage.getSize(),
                patronPage.getTotalElements(),
                patronPage.getTotalPages()
        );
    }
}