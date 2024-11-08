package com.example.btl_oop.repository;


import com.example.btl_oop.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    @Transactional
    @Modifying
    @Query("UPDATE User u SET u.fullname = :fullname,u.tel = :tel WHERE u.username = :username")
    void updateInfoUser(String tel,String fullname, String username);

    @Transactional
    @Modifying
    @Query("UPDATE User u SET u.password = :password WHERE u.username = :username")
    void updatePassword(String password, String username);


    @Transactional
    @Modifying
    @Query("UPDATE User u SET u.linkAvatar = :linkAvatar WHERE u.username = :username")
    void updateAvatarUser(String linkAvatar, String username);

    List<User> findUserByUsernameLike(String username);

    Optional<User> findUserByUsername(String username);

    Page<User> findByUsernameContaining(String textSearch, Pageable pageable);
}
