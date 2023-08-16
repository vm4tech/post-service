package com.skyeng.mailing.repository;

import com.skyeng.mailing.model.MailHistory;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MailHistoryRepository extends JpaRepository<MailHistory, Long> {
}
