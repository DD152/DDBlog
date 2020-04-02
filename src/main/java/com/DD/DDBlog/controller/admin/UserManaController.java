package com.DD.DDBlog.controller.admin;

import com.DD.DDBlog.entity.RespBean;
import com.DD.DDBlog.entity.Role;
import com.DD.DDBlog.entity.User;
import com.DD.DDBlog.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/admin")
public class UserManaController {
    @Autowired
    UserService userService;

    @GetMapping("/user")
    public List<User> getUserByNickname(String nickname) {
        return userService.getUserByNickname(nickname);
    }

    @GetMapping("/user/{id}")
    public User getUserById(@PathVariable Long id) {
        return userService.getUserById(id);
    }

    @GetMapping("/roles")
    public List<Role> getAllRole() {
        return userService.getAllRole();
    }

    @PutMapping("/user/enabled")
    public RespBean updateUserEnabled(Boolean enabled, Long uid) {
        if (userService.updateUserEnabled(enabled, uid) == 1) {
            return new RespBean("success", "更新成功!");
        } else {
            return new RespBean("error", "更新失败!");
        }
    }

    @DeleteMapping("/user/{uid}")
    public RespBean deleteUserById(@PathVariable Long uid) {
        if (userService.deleteUserById(uid) == 1) {
            return new RespBean("success", "删除成功!");
        } else {
            return new RespBean("error", "删除失败!");
        }
    }

    @PutMapping("/user/role")
    public RespBean updateUserRoles(Long[] rids, Long id) {
        if (userService.updateUserRoles(rids, id) == rids.length) {
            return new RespBean("success", "更新成功!");
        } else {
            return new RespBean("error", "更新失败!");
        }
    }
}
