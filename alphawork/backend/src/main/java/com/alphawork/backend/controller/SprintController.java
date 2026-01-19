package com.alphawork.backend.controller;

import com.alphawork.backend.dto.SprintDTO;
import com.alphawork.backend.service.SprintService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/sprints")
public class SprintController {

    @Autowired
    private SprintService sprintService;

    @PostMapping
    public ResponseEntity<SprintDTO> createSprint(@RequestBody SprintDTO dto) {
        SprintDTO created = sprintService.createSprint(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    @GetMapping("/board/{boardId}")
    public ResponseEntity<List<SprintDTO>> getSprintsByBoard(@PathVariable String boardId) {
        List<SprintDTO> sprints = sprintService.getSprintsByBoard(boardId);
        return ResponseEntity.ok(sprints);
    }

    @GetMapping("/{id}")
    public ResponseEntity<SprintDTO> getSprint(@PathVariable String id) {
        SprintDTO sprint = sprintService.getSprint(id);
        return ResponseEntity.ok(sprint);
    }

    @PutMapping("/{id}")
    public ResponseEntity<SprintDTO> updateSprint(@PathVariable String id, @RequestBody SprintDTO dto) {
        SprintDTO updated = sprintService.updateSprint(id, dto);
        return ResponseEntity.ok(updated);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteSprint(@PathVariable String id) {
        sprintService.deleteSprint(id);
        return ResponseEntity.noContent().build();
    }
}
