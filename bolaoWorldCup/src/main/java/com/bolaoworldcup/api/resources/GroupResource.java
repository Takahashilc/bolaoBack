package com.bolaoworldcup.api.resources;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.bolaoworldcup.api.dto.group.GroupDTO;
import com.bolaoworldcup.api.dto.group.GroupInputDTO;
import com.bolaoworldcup.api.dto.group.GroupOutputDTO;
import com.bolaoworldcup.api.services.GroupService;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping(value = "/groups")
public class GroupResource {

    @Autowired
    private GroupService groupService;

    @GetMapping
    public ResponseEntity<List<GroupOutputDTO>> findAll() {
        List<GroupOutputDTO> list = groupService.findAllPaged();
        return ResponseEntity.ok().body(list);
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<GroupOutputDTO> findById(@PathVariable Long id) {
        GroupOutputDTO dto = groupService.findById(id);
        return ResponseEntity.ok().body(dto);
    }

    @PostMapping
    public ResponseEntity<GroupOutputDTO> insert(@RequestBody @Valid GroupInputDTO dto) {
        GroupOutputDTO newDto = groupService.insert(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(newDto);
    }

    @PutMapping(value = "/{id}")
    public ResponseEntity<GroupOutputDTO> update(@PathVariable Long id, @RequestBody @Valid GroupInputDTO dto) {
        GroupOutputDTO newDto = groupService.update(id, dto);
        return ResponseEntity.ok().body(newDto);
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        groupService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
