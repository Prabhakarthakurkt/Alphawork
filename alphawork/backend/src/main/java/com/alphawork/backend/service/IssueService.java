package com.alphawork.backend.service;

import com.alphawork.backend.domain.entity.Board;
import com.alphawork.backend.domain.entity.Issue;
import com.alphawork.backend.dto.IssueDTO;
import com.alphawork.backend.repository.IssueRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class IssueService {

    @Autowired
    private IssueRepository issueRepository;

    @Transactional
    public IssueDTO createIssue(IssueDTO dto) {
        Issue issue = new Issue();
        issue.setTitle(dto.getTitle());
        issue.setDescription(dto.getDescription());
        issue.setType(Issue.IssueType.valueOf(dto.getType()));
        issue.setStatus(Issue.IssueStatus.valueOf(dto.getStatus() != null ? dto.getStatus() : "TODO"));
        issue.setEstimateHours(dto.getEstimateHours() != null ? dto.getEstimateHours() : 0);

        Issue saved = issueRepository.save(issue);
        return mapToDTO(saved);
    }

    public List<IssueDTO> getIssuesByBoard(String boardId) {
        return issueRepository.findByBoardId(boardId)
                .stream()
                .sorted((a, b) -> a.getOrderInColumn().compareTo(b.getOrderInColumn()))
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    public List<IssueDTO> getIssuesBySprint(String sprintId) {
        return issueRepository.findBySprintId(sprintId)
                .stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    public IssueDTO getIssue(String id) {
        Issue issue = issueRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Issue not found"));
        return mapToDTO(issue);
    }

    @Transactional
    public IssueDTO updateIssueStatus(String id, String status) {
        Issue issue = issueRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Issue not found"));

        issue.setStatus(Issue.IssueStatus.valueOf(status));
        Issue updated = issueRepository.save(issue);
        return mapToDTO(updated);
    }

    @Transactional
    public IssueDTO updateIssue(String id, IssueDTO dto) {
        Issue issue = issueRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Issue not found"));

        if (dto.getTitle() != null) issue.setTitle(dto.getTitle());
        if (dto.getDescription() != null) issue.setDescription(dto.getDescription());
        if (dto.getStatus() != null) issue.setStatus(Issue.IssueStatus.valueOf(dto.getStatus()));
        if (dto.getEstimateHours() != null) issue.setEstimateHours(dto.getEstimateHours());
        if (dto.getTimeSpentHours() != null) issue.setTimeSpentHours(dto.getTimeSpentHours());

        Issue updated = issueRepository.save(issue);
        return mapToDTO(updated);
    }

    @Transactional
    public void deleteIssue(String id) {
        issueRepository.deleteById(id);
    }

    private IssueDTO mapToDTO(Issue issue) {
        return IssueDTO.builder()
                .id(issue.getId().toString())
                .title(issue.getTitle())
                .description(issue.getDescription())
                .type(issue.getType().toString())
                .status(issue.getStatus().toString())
                .assigneeId(issue.getAssignee() != null ? issue.getAssignee().getId().toString() : null)
                .assigneeName(issue.getAssignee() != null ? issue.getAssignee().getFirstName() + " " + issue.getAssignee().getLastName() : null)
                .projectId(issue.getProject() != null ? issue.getProject().getId().toString() : null)
                .boardId(issue.getBoard() != null ? issue.getBoard().getId().toString() : null)
                .sprintId(issue.getSprint() != null ? issue.getSprint().getId().toString() : null)
                .estimateHours(issue.getEstimateHours())
                .timeSpentHours(issue.getTimeSpentHours())
                .orderInColumn(issue.getOrderInColumn())
                .createdAt(issue.getCreatedAt())
                .updatedAt(issue.getUpdatedAt())
                .build();
    }
}
