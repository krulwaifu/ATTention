package com.attention.demo.Repository;


import com.attention.demo.Models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface IUserRepo extends JpaRepository<User,Long> {

    User findByUsername(String username);

    User findById(long id);
    User findByUsernameAndPassword(String username, String password);

}
