package com.alphawork.backend.repository;

import com.alphawork.backend.domain.entity.Issue;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IssueRepository extends JpaRepository<Issue, String> {
    List<Issue> findByBoardId(String boardId);
    List<Issue> findBySprintId(String sprintId);
    List<Issue> findByProjectId(String projectId);
}
