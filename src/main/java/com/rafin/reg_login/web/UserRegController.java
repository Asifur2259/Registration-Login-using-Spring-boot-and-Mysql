package com.rafin.reg_login.web;

import com.rafin.reg_login.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

@Controller
@RequestMapping("/register")
public class UserRegController {

    @Autowired
    private UserService userService;

    @GetMapping
    public String showRegForm(Model model, UserRegistrationDTO userRegistrationDTO){
        model.addAttribute("user", userRegistrationDTO);
        return "register";
    }

    @PostMapping
    public String regUser(UserRegistrationDTO userRegistrationDTO){
        userService.save(userRegistrationDTO);
        return "redirect:/register?success";
    }

    @PostMapping("/uploadImage")
    public String uploadImage(@RequestParam("imageFile") MultipartFile imageFile) throws Exception {

            userService.saveImage(imageFile);

        return "register";
    }
}
