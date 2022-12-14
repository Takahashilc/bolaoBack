package com.bolaoworldcup.api.resources;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.bolaoworldcup.api.dto.tip.TipInputDTO;
import com.bolaoworldcup.api.dto.tip.TipOutputDTO;
import com.bolaoworldcup.api.services.TipService;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/tips")
public class TipResource {

    @Autowired
    private TipService tipService;

    @GetMapping
    public ResponseEntity<List<TipOutputDTO>> findAll() {
        List<TipOutputDTO> list = tipService.findAllPaged();
        return ResponseEntity.ok().body(list);
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<TipOutputDTO> findById(@PathVariable Long id) {
        TipOutputDTO dto = tipService.findById(id);
        return ResponseEntity.ok().body(dto);
    }

    @PostMapping
    public ResponseEntity<TipOutputDTO> insert(@RequestBody @Valid TipInputDTO dto) {
        TipOutputDTO newDto = tipService.insert(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(newDto);
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        tipService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
