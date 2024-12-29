package com.itbank.worldcup.mapper;

import com.itbank.worldcup.model.FileEntity;
import org.apache.ibatis.annotations.Mapper;



@Mapper
public interface FileMapper {
    int insertFile(FileEntity fileEntity);

    int updateFile(FileEntity newFile);
}
