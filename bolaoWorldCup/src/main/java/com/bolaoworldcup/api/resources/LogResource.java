package com.bolaoworldcup.api.resources;

import com.bolaoworldcup.api.dto.log.LogInputDTO;
import com.bolaoworldcup.api.dto.log.LogOutputDTO;
import com.bolaoworldcup.api.services.LogService;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/logs")
public class LogResource {

    @Autowired
    private LogService logService;

    @GetMapping
    public ResponseEntity<List<LogOutputDTO>> findAll(
            @RequestParam(required = false)
            @JsonFormat(pattern = "yyyy-MM-dd")
            @DateTimeFormat(pattern = "yyyy-MM-dd")
            @JsonDeserialize(using = LocalDateDeserializer.class)
            @JsonSerialize(using = LocalDateSerializer.class) LocalDate date) {
        List<LogOutputDTO> list = logService.findAllPaged(date);
        return ResponseEntity.ok().body(list);
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<LogOutputDTO> findById(@PathVariable Long id) {
        LogOutputDTO dto = logService.findById(id);
        return ResponseEntity.ok().body(dto);
    }
}
