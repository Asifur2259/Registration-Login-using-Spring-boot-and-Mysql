package com.rafin.reg_login.service;

import com.rafin.reg_login.models.Roles;
import com.rafin.reg_login.models.User;
import com.rafin.reg_login.repository.UserRepository;
import com.rafin.reg_login.web.UserRegistrationDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.Collection;
import java.util.stream.Collectors;

@Service
public class UserServiceImp implements UserService {

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @Autowired
    UserRepository userRepository;

    @Override
    public User save(UserRegistrationDTO registrationDTO) {
        User user = new User(registrationDTO.getFirstName(),registrationDTO.getLastName(), registrationDTO.getEmail(), passwordEncoder.encode(registrationDTO.getPassword()), Arrays.asList(new Roles("ROLE_USER")));
        return userRepository.save(user);
    }

    @Override
    public void saveImage(MultipartFile imageFile) throws Exception {
        String folder = "/photos/";
        byte[] bytes = imageFile.getBytes();
        Path path = Paths.get(folder + imageFile.getOriginalFilename());
        Files.write(path, bytes);
    }

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        User user = userRepository.findByEmail(s);
        if (user==null){
            throw new UsernameNotFoundException("Invalid Username or Password");
        }
        return new org.springframework.security.core.userdetails.User(user.getEmail(), user.getPassword(), mapRolesToAuthorities(user.getRole()));
    }

    private Collection<? extends GrantedAuthority> mapRolesToAuthorities(Collection<Roles>role){
        return role.stream().map(roles -> new SimpleGrantedAuthority(roles.getName())).collect(Collectors.toList());
    }
}
