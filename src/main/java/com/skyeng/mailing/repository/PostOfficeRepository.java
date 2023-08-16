package com.skyeng.mailing.repository;

import com.skyeng.mailing.model.PostOffice;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostOfficeRepository extends JpaRepository<PostOffice, Long> {
    PostOffice findPostOfficeByIndex(String index);
}
