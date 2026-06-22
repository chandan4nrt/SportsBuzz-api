package com.chandan.sports.repository;

import com.chandan.sports.entity.Sport;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SportRepository extends JpaRepository<Sport, Long> {
}
