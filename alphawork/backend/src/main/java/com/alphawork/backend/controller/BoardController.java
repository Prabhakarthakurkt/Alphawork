package com.alphawork.backend.controller;

import com.alphawork.backend.dto.BoardDTO;
import com.alphawork.backend.service.BoardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/boards")
public class BoardController {

    @Autowired
    private BoardService boardService;

    @PostMapping
    public ResponseEntity<BoardDTO> createBoard(@RequestBody BoardDTO dto,
                                               @RequestHeader String projectId) {
        BoardDTO created = boardService.createBoard(dto, projectId);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    @GetMapping("/project/{projectId}")
    public ResponseEntity<List<BoardDTO>> getBoardsByProject(@PathVariable String projectId) {
        List<BoardDTO> boards = boardService.getBoardsByProject(projectId);
        return ResponseEntity.ok(boards);
    }

    @GetMapping("/{id}")
    public ResponseEntity<BoardDTO> getBoard(@PathVariable String id) {
        BoardDTO board = boardService.getBoard(id);
        return ResponseEntity.ok(board);
    }

    @PutMapping("/{id}")
    public ResponseEntity<BoardDTO> updateBoard(@PathVariable String id, @RequestBody BoardDTO dto) {
        BoardDTO updated = boardService.updateBoard(id, dto);
        return ResponseEntity.ok(updated);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBoard(@PathVariable String id) {
        boardService.deleteBoard(id);
        return ResponseEntity.noContent().build();
    }
}
