package com.platform.dentify.inventory.infrastructure.persistence.jpa.repositories;

import com.platform.dentify.inventory.domain.model.aggregates.Item;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;


@Repository
public interface ItemRepository extends JpaRepository<Item, Long> {

    boolean existsByNameAndUser_Id(String name, Long userId);
    Optional<Item> findByIdAndUser_Id(Long id, Long userId);
    List<Item> findTop3ByUser_IdAndStockQuantityLessThanOrderByStockQuantityAsc(Long userId, Integer quantity);
    List<Item> findAllByUser_Id(Long userId);
}
