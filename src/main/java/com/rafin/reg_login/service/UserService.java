package com.rafin.reg_login.service;

import com.rafin.reg_login.models.User;
import com.rafin.reg_login.web.UserRegistrationDTO;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.multipart.MultipartFile;

public interface UserService extends UserDetailsService {
    User save(UserRegistrationDTO registrationDTO);

    void saveImage(MultipartFile imageFile) throws Exception;
}

