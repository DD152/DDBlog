package com.DD.DDBlog.controller;

import com.DD.DDBlog.entity.RespBean;
import com.DD.DDBlog.service.UserService;
import com.DD.DDBlog.utils.Util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


@RestController
public class UserController {

    @Autowired
    UserService userService;

    @GetMapping("/currentUserName")
    public String currentUserName() {
        return Util.getCurrentUser().getNickname();
    }

    @GetMapping("/currentUserId")
    public Long currentUserId() {
        return Util.getCurrentUser().getId();
    }

    @GetMapping("/currentUserEmail")
    public String currentUserEmail() {
        return Util.getCurrentUser().getEmail();
    }

    @GetMapping("/isAdmin")
    public Boolean isAdmin() {
        List<GrantedAuthority> authorities = (List<GrantedAuthority>) Util.getCurrentUser().getAuthorities();
        for (GrantedAuthority authority : authorities) {
            if (authority.getAuthority().contains("超管")) {
                return true;
            }
        }
        return false;
    }

    @PutMapping("/updateUserEmail")
    public RespBean updateUserEmail(String email) {
        if (userService.updateUserEmail(email) == 1) {
            return new RespBean("success", "修改成功!");
        }
        return new RespBean("error", "修改失败!");
    }
}
