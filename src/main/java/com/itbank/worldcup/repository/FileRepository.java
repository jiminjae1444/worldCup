package com.itbank.worldcup.repository;

import com.itbank.worldcup.model.FileEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FileRepository extends JpaRepository<FileEntity, Integer> {
}
