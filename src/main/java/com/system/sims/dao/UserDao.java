package com.system.sims.dao;

import com.system.sims.beans.User;

public interface UserDao {
    User getByUserNumber(String username);

    int updateUser(User user);
    int insertUser(User user);
    int deleteUserById(String id);
    int countUser();
}
