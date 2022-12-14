package com.bolaoworldcup.api.resources;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.bolaoworldcup.api.dto.team.TeamInputDTO;
import com.bolaoworldcup.api.dto.team.TeamOutputDTO;
import com.bolaoworldcup.api.services.TeamService;

import java.util.List;

@RestController
@RequestMapping(value = "/teams")
public class TeamResource {

    @Autowired
    private TeamService teamService;

    @GetMapping
    public ResponseEntity<List<TeamOutputDTO>> findAll() {
        List<TeamOutputDTO> list = teamService.findAllPaged();
        return ResponseEntity.ok().body(list);
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<TeamOutputDTO> findById(@PathVariable Long id) {
        TeamOutputDTO dto = teamService.findById(id);
        return ResponseEntity.ok().body(dto);
    }

    @PostMapping
    public ResponseEntity<TeamOutputDTO> insert(@RequestBody TeamInputDTO dto) {
        TeamOutputDTO outputDTO = teamService.insert(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(outputDTO);
    }

    @PutMapping(value = "/{id}")
    public ResponseEntity<TeamOutputDTO> update(@PathVariable Long id, @RequestBody TeamInputDTO dto) {
        TeamOutputDTO outputDTO = teamService.update(id, dto);
        return ResponseEntity.ok().body(outputDTO);
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        teamService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
