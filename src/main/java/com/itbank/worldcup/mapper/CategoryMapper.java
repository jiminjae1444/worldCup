package com.itbank.worldcup.mapper;

import org.apache.ibatis.annotations.Mapper;


@Mapper
public interface CategoryMapper {

    int update(int id, String cName, String description);
}
