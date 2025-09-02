package com.exam.demo.service;

import com.exam.demo.dto.RegisterDto;
import com.exam.demo.dto.UserDto;
import com.exam.demo.model.LMSPerson;
import com.exam.demo.model.User;
import com.exam.demo.repo.LmsPersonRepository;
import com.exam.demo.repo.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final LmsPersonRepository lmsPersonRepository;
    public UserServiceImpl(UserRepository userRepository, LmsPersonRepository lmsPersonRepository) {
        this.userRepository = userRepository;
        this.lmsPersonRepository = lmsPersonRepository;
    }
    @Override
    public List<User> findAll(){

        return userRepository.findAll();
    }

    @Override
    public void saveUser(RegisterDto user) {
        User userEntity = new User();
        userEntity.setUsername(user.getUsername());
        userEntity.setPassword(user.getPassword());
         User savedUser=userRepository.save(userEntity);

        LMSPerson lmsPersonEntity = new LMSPerson();
        lmsPersonEntity.setNameFamily(user.getNameFamily());
        lmsPersonEntity.setPhone(user.getPhone());
        lmsPersonEntity.setNationalCode(user.getNationalCode());
        lmsPersonEntity.setUser(savedUser);
        savedUser.setLmsPerson(lmsPersonEntity);
        lmsPersonRepository.save(lmsPersonEntity);
    }

}
