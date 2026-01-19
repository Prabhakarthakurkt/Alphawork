package com.alphawork.backend.service;

import com.alphawork.backend.domain.entity.Board;
import com.alphawork.backend.domain.entity.Project;
import com.alphawork.backend.dto.BoardDTO;
import com.alphawork.backend.repository.BoardRepository;
import com.alphawork.backend.repository.ProjectRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class BoardService {

    @Autowired
    private BoardRepository boardRepository;

    @Autowired
    private ProjectRepository projectRepository;

    @Transactional
    public BoardDTO createBoard(BoardDTO dto, String projectId) {
        Project project = projectRepository.findById(projectId)
                .orElseThrow(() -> new RuntimeException("Project not found"));

        Board board = new Board();
        board.setName(dto.getName());
        board.setType(Board.BoardType.valueOf(dto.getType()));
        board.setProject(project);

        Board saved = boardRepository.save(board);
        return mapToDTO(saved);
    }

    public List<BoardDTO> getBoardsByProject(String projectId) {
        return boardRepository.findByProjectId(projectId)
                .stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    public BoardDTO getBoard(String id) {
        Board board = boardRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Board not found"));
        return mapToDTO(board);
    }

    @Transactional
    public BoardDTO updateBoard(String id, BoardDTO dto) {
        Board board = boardRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Board not found"));

        if (dto.getName() != null) board.setName(dto.getName());
        if (dto.getType() != null) board.setType(Board.BoardType.valueOf(dto.getType()));

        Board updated = boardRepository.save(board);
        return mapToDTO(updated);
    }

    @Transactional
    public void deleteBoard(String id) {
        boardRepository.deleteById(id);
    }

    private BoardDTO mapToDTO(Board board) {
        return BoardDTO.builder()
                .id(board.getId())
                .name(board.getName())
                .projectId(board.getProject() != null ? board.getProject().getId() : null)
                .type(board.getType().toString())
                .createdAt(board.getCreatedAt())
                .updatedAt(board.getUpdatedAt())
                .build();
    }
}
