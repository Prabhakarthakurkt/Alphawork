package com.alphawork.backend.service;

import com.alphawork.backend.domain.entity.Board;
import com.alphawork.backend.domain.entity.Sprint;
import com.alphawork.backend.dto.SprintDTO;
import com.alphawork.backend.repository.SprintRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class SprintService {

    @Autowired
    private SprintRepository sprintRepository;

    @Transactional
    public SprintDTO createSprint(SprintDTO dto) {
        Sprint sprint = new Sprint();
        sprint.setName(dto.getName());
        sprint.setGoal(dto.getGoal());
        sprint.setStartDate(dto.getStartDate());
        sprint.setEndDate(dto.getEndDate());

        Sprint saved = sprintRepository.save(sprint);
        return mapToDTO(saved);
    }

    public List<SprintDTO> getSprintsByBoard(String boardId) {
        return sprintRepository.findByBoardId(boardId)
                .stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    public SprintDTO getSprint(String id) {
        Sprint sprint = sprintRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Sprint not found"));
        return mapToDTO(sprint);
    }

    @Transactional
    public SprintDTO updateSprint(String id, SprintDTO dto) {
        Sprint sprint = sprintRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Sprint not found"));

        if (dto.getName() != null) sprint.setName(dto.getName());
        if (dto.getGoal() != null) sprint.setGoal(dto.getGoal());
        if (dto.getStartDate() != null) sprint.setStartDate(dto.getStartDate());
        if (dto.getEndDate() != null) sprint.setEndDate(dto.getEndDate());
        if (dto.getStatus() != null) sprint.setStatus(Sprint.SprintStatus.valueOf(dto.getStatus()));

        Sprint updated = sprintRepository.save(sprint);
        return mapToDTO(updated);
    }

    @Transactional
    public void deleteSprint(String id) {
        sprintRepository.deleteById(id);
    }

    private SprintDTO mapToDTO(Sprint sprint) {
        return SprintDTO.builder()
                .id(sprint.getId())
                .name(sprint.getName())
                .goal(sprint.getGoal())
                .boardId(sprint.getBoard() != null ? sprint.getBoard().getId() : null)
                .startDate(sprint.getStartDate())
                .endDate(sprint.getEndDate())
                .status(sprint.getStatus().toString())
                .createdAt(sprint.getCreatedAt())
                .updatedAt(sprint.getUpdatedAt())
                .build();
    }
}
