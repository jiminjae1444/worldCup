package com.itbank.worldcup.mapper;

import com.itbank.worldcup.model.User;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserMapper {
    List<User> getUnapprovedUsers();
}
