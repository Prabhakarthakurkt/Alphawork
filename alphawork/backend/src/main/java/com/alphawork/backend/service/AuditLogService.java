package com.alphawork.backend.service;

import com.alphawork.backend.domain.entity.AuditLog;
import com.alphawork.backend.domain.entity.User;
import com.alphawork.backend.repository.AuditLogRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class AuditLogService {

    @Autowired
    private AuditLogRepository auditLogRepository;

    @Transactional
    public void logAction(User user, String action, String entityType, String entityId, 
                         String beforeState, String afterState, String description) {
        AuditLog log = AuditLog.builder()
                .user(user)
                .action(action)
                .entityType(entityType)
                .entityId(entityId)
                .beforeState(beforeState)
                .afterState(afterState)
                .description(description)
                .build();

        auditLogRepository.save(log);
    }

    public List<AuditLog> getAuditLogsForEntity(String entityId) {
        return auditLogRepository.findByEntityId(entityId);
    }

    public List<AuditLog> getAuditLogsByEntityType(String entityType) {
        return auditLogRepository.findByEntityType(entityType);
    }
}
