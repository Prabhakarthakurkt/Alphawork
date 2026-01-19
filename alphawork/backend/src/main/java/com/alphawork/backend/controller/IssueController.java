package com.alphawork.backend.controller;

import com.alphawork.backend.dto.IssueDTO;
import com.alphawork.backend.service.IssueService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/issues")
public class IssueController {

    @Autowired
    private IssueService issueService;

    @PostMapping
    public ResponseEntity<IssueDTO> createIssue(@RequestBody IssueDTO dto) {
        IssueDTO created = issueService.createIssue(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    @GetMapping("/board/{boardId}")
    public ResponseEntity<List<IssueDTO>> getIssuesByBoard(@PathVariable String boardId) {
        List<IssueDTO> issues = issueService.getIssuesByBoard(boardId);
        return ResponseEntity.ok(issues);
    }

    @GetMapping("/sprint/{sprintId}")
    public ResponseEntity<List<IssueDTO>> getIssuesBySprint(@PathVariable String sprintId) {
        List<IssueDTO> issues = issueService.getIssuesBySprint(sprintId);
        return ResponseEntity.ok(issues);
    }

    @GetMapping("/{id}")
    public ResponseEntity<IssueDTO> getIssue(@PathVariable String id) {
        IssueDTO issue = issueService.getIssue(id);
        return ResponseEntity.ok(issue);
    }

    @PatchMapping("/{id}/status")
    public ResponseEntity<IssueDTO> updateStatus(@PathVariable String id, 
                                                @RequestParam String status) {
        IssueDTO updated = issueService.updateIssueStatus(id, status);
        return ResponseEntity.ok(updated);
    }

    @PutMapping("/{id}")
    public ResponseEntity<IssueDTO> updateIssue(@PathVariable String id, @RequestBody IssueDTO dto) {
        IssueDTO updated = issueService.updateIssue(id, dto);
        return ResponseEntity.ok(updated);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteIssue(@PathVariable String id) {
        issueService.deleteIssue(id);
        return ResponseEntity.noContent().build();
    }
}
