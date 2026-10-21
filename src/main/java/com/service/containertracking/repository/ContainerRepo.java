package com.service.containertracking.repository;

import com.service.containertracking.entity.Container;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ContainerRepo extends JpaRepository<Container, Integer> {
}
