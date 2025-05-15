package com.platform.dentify.inventory.infrastructure.persistence.jpa.repositories;

import com.platform.dentify.inventory.domain.model.aggregates.Item;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface ItemRepository extends JpaRepository<Item, Long> {

    boolean existsByNameAndUser_Id(String name, Long userId);

    List<Item> findAllByUser_Id(Long userId);
}
