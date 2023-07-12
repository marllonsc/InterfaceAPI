package com.ini.interfaceAPI.repository;

import org.springframework.stereotype.Repository;

import com.ini.interfaceAPI.entity.Person;

import org.springframework.data.jpa.repository.JpaRepository;

@Repository
public interface PersonRepository extends JpaRepository<Person,Integer>{		

}