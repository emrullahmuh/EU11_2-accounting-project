package com.cydeo.fintracker.entity.common;

import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import java.time.LocalDateTime;

public class BaseEntityListener extends AuditingEntityListener {

    @PrePersist
    private void onPrePersist(BaseEntity baseEntity) {

        final Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        baseEntity.insertDateTime = LocalDateTime.now();
        baseEntity.lastUpdateDateTime = LocalDateTime.now();

        if (authentication != null && !authentication.getPrincipal().equals("anonymous")) {
            UserPrincipal principal = (UserPrincipal) authentication.getPrincipal();
            baseEntity.insertUserId = principal.getId();
            baseEntity.lastUpdateUserId = principal.getId();
        } else {
            baseEntity.insertUserId = -1L;
            baseEntity.lastUpdateUserId = -1L;
        }
    }

    @PreUpdate
    private void onPreUpdate(BaseEntity baseEntity) {

        final Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        baseEntity.lastUpdateDateTime = LocalDateTime.now();

        if (authentication != null && !authentication.getPrincipal().equals("anonymous")) {
            UserPrincipal principal = (UserPrincipal) authentication.getPrincipal();
            baseEntity.lastUpdateUserId = principal.getId();
        } else {
            baseEntity.insertUserId = -1L;
            baseEntity.lastUpdateUserId = -1L;
        }
    }
}
