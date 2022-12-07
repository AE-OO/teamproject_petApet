package com.teamproject.petapet.domain.product.repository;

import com.teamproject.petapet.domain.product.Counter;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CounterRepository extends JpaRepository<Counter, Long> {
}
