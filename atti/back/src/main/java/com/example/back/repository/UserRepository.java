package com.example.back.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.back.domain.Post;
import com.example.back.domain.User;

@Repository
public interface UserRepository extends JpaRepository<User, String> {
	
    User findByNickName(String nickName);
    
    User findByNickNameAndPwd(String nickName, String pwd);
    
    Boolean existsByNickName(String nickName);
}