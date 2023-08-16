package com.skyeng.mailing.model.common.extenders;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.MappedSuperclass;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.data.domain.Auditable;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@MappedSuperclass
@Getter
@Setter
public class CommonFields  {
    @Id
    @GeneratedValue
    @Column
    Long id;

    @CreationTimestamp
    @Column(updatable = false, nullable = false)
    private LocalDateTime created;

    @UpdateTimestamp
    @Column(nullable = false)
    private LocalDateTime updated;
}