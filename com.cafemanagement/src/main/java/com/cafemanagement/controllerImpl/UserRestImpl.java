package com.cafemanagement.controllerImpl;

import com.cafemanagement.constants.CafeConstants;
import com.cafemanagement.controller.UserRest;
import com.cafemanagement.service.UserService;
import com.cafemanagement.utils.CafeUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@Slf4j
public class UserRestImpl implements UserRest {

    @Autowired
    UserService userService;
    @Override
    public ResponseEntity<String> signUp(Map<String, String> requestMap) {
        try {
            userService.signUp(requestMap);
        } catch (Exception e) {
            log.error("Caught exception at signup" + e.getMessage() + e.getStackTrace());
        }

        return CafeUtils.getResponseEntity(CafeConstants.SUCESSFULLY_REGISTERED, HttpStatus.CREATED);
    }
}
