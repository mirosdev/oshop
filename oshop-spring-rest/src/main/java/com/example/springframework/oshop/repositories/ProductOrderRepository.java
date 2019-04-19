package com.example.springframework.oshop.repositories;

import com.example.springframework.oshop.domain.ProductOrder;
import com.example.springframework.oshop.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProductOrderRepository extends JpaRepository<ProductOrder, Long> {
    Optional<List<ProductOrder>> findAllByUser(User user);
}
