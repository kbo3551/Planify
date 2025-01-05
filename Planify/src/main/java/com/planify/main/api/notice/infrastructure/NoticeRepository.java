package com.planify.main.api.notice.infrastructure;

import org.springframework.data.jpa.repository.JpaRepository;

import com.planify.main.api.notice.domain.Notice;

public interface NoticeRepository extends JpaRepository<Notice, Long> {
//	List<Notice> findByNotice(String title,Long MemberNo);
}
