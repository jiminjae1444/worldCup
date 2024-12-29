package com.itbank.worldcup.repository;

import com.itbank.worldcup.model.Items;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ItemRepository extends JpaRepository<Items,Integer> {
    List<Items> findAllById(int id);
}
