package com.project.jdevone.jdev.repository;

import com.project.jdevone.jdev.entity.ExEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

public interface ExRepository extends JpaRepository<ExEntity, Integer>, QuerydslPredicateExecutor {
}
