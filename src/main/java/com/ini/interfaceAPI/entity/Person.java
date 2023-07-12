package com.ini.interfaceAPI.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;


import lombok.Getter;
import lombok.Setter;

@Getter @Setter @Entity
public class Person {
    
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
	private Integer id;
	
	@Column(nullable = false)
	private String name;
	
	@Column(nullable = false)
	private String document;
	
	@Column(nullable = false)
	private String email;
}
