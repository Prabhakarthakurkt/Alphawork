package com.alphawork.backend.service;

import com.alphawork.backend.domain.entity.Project;
import com.alphawork.backend.dto.ProjectDTO;
import com.alphawork.backend.repository.ProjectRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProjectService {

    @Autowired
    private ProjectRepository projectRepository;

    @Transactional
    public ProjectDTO createProject(ProjectDTO dto, String organizationId) {
        Project project = new Project();
        project.setName(dto.getName());
        project.setDescription(dto.getDescription());
        project.setStartDate(dto.getStartDate());
        project.setEndDate(dto.getEndDate());

        Project saved = projectRepository.save(project);
        return mapToDTO(saved);
    }

    public List<ProjectDTO> getProjectsByOrganization(String organizationId) {
        return projectRepository.findByOrganizationId(organizationId)
                .stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    public ProjectDTO getProject(String id) {
        Project project = projectRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Project not found"));
        return mapToDTO(project);
    }

    @Transactional
    public ProjectDTO updateProject(String id, ProjectDTO dto) {
        Project project = projectRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Project not found"));

        if (dto.getName() != null) project.setName(dto.getName());
        if (dto.getDescription() != null) project.setDescription(dto.getDescription());
        if (dto.getStartDate() != null) project.setStartDate(dto.getStartDate());
        if (dto.getEndDate() != null) project.setEndDate(dto.getEndDate());
        if (dto.getStatus() != null) project.setStatus(Project.ProjectStatus.valueOf(dto.getStatus()));

        Project updated = projectRepository.save(project);
        return mapToDTO(updated);
    }

    @Transactional
    public void deleteProject(String id) {
        projectRepository.deleteById(id);
    }

    private ProjectDTO mapToDTO(Project project) {
        return ProjectDTO.builder()
                .id(project.getId())
                .name(project.getName())
                .description(project.getDescription())
                .organizationId(project.getOrganization() != null ? project.getOrganization().getId() : null)
                .startDate(project.getStartDate())
                .endDate(project.getEndDate())
                .status(project.getStatus().toString())
                .createdAt(project.getCreatedAt())
                .updatedAt(project.getUpdatedAt())
                .build();
    }
}
