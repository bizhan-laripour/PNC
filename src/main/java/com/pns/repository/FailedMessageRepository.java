package com.pns.repository;

import com.pns.entity.FailedMessages;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface FailedMessageRepository extends JpaRepository<FailedMessages , Long> {
}
