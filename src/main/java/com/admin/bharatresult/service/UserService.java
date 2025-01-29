package com.admin.bharatresult.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.admin.bharatresult.entity.User;
import com.admin.bharatresult.repository.UserRepository;
import com.admin.bharatresult.utils.PasswordUtils;

@Service
public class UserService {

    @Autowired
    private UserRepository userReppo;

    public User saveUser(User user) {
        String hashedpw=PasswordUtils.generatePassword(user.getPassword());
        user.setPassword(hashedpw);
        return this.userReppo.save(user);
    }
    public List<User> getUsers(){
        return this.userReppo.findAll();
    }
    public User userById(Long id){
        return this.userReppo.findById(id).orElse(null);
    }
    public User userByEmail(String email){
        return this.userReppo.findByEmail(email);
    }
    public boolean deleteUser(Long id){
        this.userReppo.deleteById(id);
        return true;
    }
    public boolean existByEmail(String email){
        return this.userReppo.existsByEmail(email);
    }
    public boolean existByMobile(User user){
        return this.userReppo.existsByMobile(user.getMobile());
    }
    public boolean existById(Long id){
        return this.userReppo.existsById(id);
    }

}
