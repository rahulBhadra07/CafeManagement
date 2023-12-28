package com.cafemanagement.serviceImpl;

import com.cafemanagement.constants.CafeConstants;
import com.cafemanagement.dao.UserDao;
import com.cafemanagement.model.User;
import com.cafemanagement.service.UserService;
import com.cafemanagement.utils.CafeUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.Objects;

@Service
@Slf4j
public class UserServiceImpl implements UserService {

    @Autowired
    UserDao repo;

    @Override
    public ResponseEntity<String> signUp(Map<String, String> requestMap) {
        log.info("Inside signUp Request map is" + requestMap.toString());
        try {
        if (validateSignup(requestMap)) {
            User user = repo.findByEmailId(requestMap.get("email"));
            if (Objects.isNull(user)) {
                repo.save(getUserFromMap(requestMap));
            } else {
                return CafeUtils.getResponseEntity(CafeConstants.EMAIL_EXIST, HttpStatus.BAD_REQUEST);
            }
        } else {
            return CafeUtils.getResponseEntity(CafeConstants.INVALID_DATA, HttpStatus.BAD_REQUEST);
        }
    }catch(Exception e){
            log.error("Exception occured"+e.getStackTrace()+e.getMessage());
        }
        return CafeUtils.getResponseEntity(CafeConstants.SOMETHING_WENT_WRONG,HttpStatus.INTERNAL_SERVER_ERROR);
    }


    private boolean validateSignup(Map<String, String> requestMap) {
        if (requestMap.containsKey("name") && requestMap.containsKey("email")
                && requestMap.containsKey("contactNumber") && requestMap.containsKey("password")) {
            return true;
        } else {
            return false;
        }
    }

    private User getUserFromMap(Map<String,String> requestMap){

        User user =new User();
        user.setName(requestMap.get("name"));
        user.setContactNumber(requestMap.get("contactNumber"));
        user.setPassword(requestMap.get("password"));
        user.setEmail(requestMap.get("email"));
        user.setStatus("false");
        user.setRole("user");
        return user;
    }
}
