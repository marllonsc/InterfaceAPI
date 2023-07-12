package com.ini.interfaceAPI.repository;

import org.springframework.stereotype.Repository;


import com.ini.interfaceAPI.entity.User;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

@Repository
public interface UserRepository extends JpaRepository<User,Integer>{

    Optional<User> findByLogin(String login);		

}
    
