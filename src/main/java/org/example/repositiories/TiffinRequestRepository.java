package org.example.repositiories;

import org.example.entity.TiffinRequest;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TiffinRequestRepository extends JpaRepository<TiffinRequest, Long> {
    List<TiffinRequest> findBystatus(String status);
}
