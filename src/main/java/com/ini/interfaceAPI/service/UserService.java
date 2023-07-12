package com.ini.interfaceAPI.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ini.interfaceAPI.entity.User;
import com.ini.interfaceAPI.repository.UserRepository;
import com.ini.interfaceAPI.util.Md5Util;
import org.springframework.util.StringUtils;

@Service
public class UserService {

    @Autowired
	private UserRepository repository;

    // CREATE 
	public User createUser(User user) {
        if(user.getId()==null)
            user.setPassword(Md5Util.getMd5(user.getPassword()));

	    return repository.save(user);
	}

    public User logar(String login, String password){
        if(StringUtils.isEmpty(login) || StringUtils.isEmpty(password))
			return null;

		password = Md5Util.getMd5(password);
        User user = getPerson(login);
        if(user != null && (user.getLogin().equalsIgnoreCase(login) && user.getPassword().equalsIgnoreCase(password))){
			return user;
		}
        return null;
    }

	// READ
	private User getPerson(String login) {
	    Optional<User> user = repository.findByLogin(login);
		if(user.isEmpty()){
			return null;
		}
		return user.get();
	}
    
}
