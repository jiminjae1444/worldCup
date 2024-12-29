package com.itbank.worldcup.mapper;

import com.itbank.worldcup.model.User;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface UserMapper {
    List<User> getUnapprovedUsers();
}
