package com.cafemanagement.JWT;

import com.cafemanagement.dao.UserDao;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Objects;

@Service
@Slf4j
public class CustomerUSerDetailsService implements UserDetailsService {

    @Autowired
    private UserDao userDao;

    private com.cafemanagement.model.User userDetail;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        log.info("inside loadUserByUsername"+email);
        userDetail=userDao.findByEmailId(email);
        if(!Objects.isNull(userDetail)){
            return new User(userDetail.getEmail(), userDetail.getPassword(), new ArrayList<>());
        }else{
            throw new UsernameNotFoundException("user not found");
        }
    }

    public com.cafemanagement.model.User getUserDetail(){
        return userDetail;
    }
}
