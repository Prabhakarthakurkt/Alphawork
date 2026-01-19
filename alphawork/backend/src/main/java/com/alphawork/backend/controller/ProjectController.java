package com.alphawork.backend.controller;

import com.alphawork.backend.dto.ProjectDTO;
import com.alphawork.backend.service.ProjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/projects")
public class ProjectController {

    @Autowired
    private ProjectService projectService;

    @PostMapping
    @PreAuthorize("hasRole('ORG_HEAD')")
    public ResponseEntity<ProjectDTO> createProject(@RequestBody ProjectDTO dto,
                                                   @RequestHeader String organizationId) {
        ProjectDTO created = projectService.createProject(dto, organizationId);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    @GetMapping
    public ResponseEntity<List<ProjectDTO>> getProjects(@RequestHeader String organizationId) {
        List<ProjectDTO> projects = projectService.getProjectsByOrganization(organizationId);
        return ResponseEntity.ok(projects);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProjectDTO> getProject(@PathVariable String id) {
        ProjectDTO project = projectService.getProject(id);
        return ResponseEntity.ok(project);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ORG_HEAD') or hasRole('TEAM_LEAD')")
    public ResponseEntity<ProjectDTO> updateProject(@PathVariable String id, @RequestBody ProjectDTO dto) {
        ProjectDTO updated = projectService.updateProject(id, dto);
        return ResponseEntity.ok(updated);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ORG_HEAD')")
    public ResponseEntity<Void> deleteProject(@PathVariable String id) {
        projectService.deleteProject(id);
        return ResponseEntity.noContent().build();
    }
}
