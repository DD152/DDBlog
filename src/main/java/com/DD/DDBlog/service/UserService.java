package com.DD.DDBlog.service;

import com.DD.DDBlog.dao.RolesDao;
import com.DD.DDBlog.dao.UserDao;
import com.DD.DDBlog.entity.Role;
import com.DD.DDBlog.entity.User;
import com.DD.DDBlog.utils.Util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.DigestUtils;

import java.util.List;


@Service
@Transactional
public class UserService implements UserDetailsService {
    @Autowired
    UserDao userDao;
    @Autowired
    RolesDao rolesDao;

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        User user = userDao.loadUserByUsername(s);
        if (user == null) {
            //避免返回null，这里返回一个不含有任何值的User对象，在后期的密码比对过程中一样会验证失败
            return new User();
        }
        //查询用户的角色信息，并返回存入user中
        List<Role> roles = rolesDao.getRolesByUid(user.getId());
        user.setRoles(roles);
        return user;
    }

    /**
     * @param user
     * @return 0表示成功
     * 1表示用户名重复
     * 2表示失败
     */
    public int reg(User user) {
        User loadUserByUsername = userDao.loadUserByUsername(user.getUsername());
        if (loadUserByUsername != null) {
            return 1;
        }
        //插入用户,插入之前先对密码进行加密
        user.setPassword(DigestUtils.md5DigestAsHex(user.getPassword().getBytes()));
        user.setEnabled(true);//用户可用
        long result = userDao.reg(user);
        //配置用户的角色，默认都是普通用户
        String[] roles = new String[]{"2"};
        int i = rolesDao.addRoles(roles, user.getId());
        boolean b = i == roles.length && result == 1;
        if (b) {
            return 0;
        } else {
            return 2;
        }
    }

    public int updateUserEmail(String email) {
        return userDao.updateUserEmail(email, Util.getCurrentUser().getId());
    }

    public List<User> getUserByNickname(String nickname) {
        List<User> list = userDao.getUserByNickname(nickname);
        return list;
    }

    public List<Role> getAllRole() {
        return userDao.getAllRole();
    }

    public int updateUserEnabled(Boolean enabled, Long uid) {
        return userDao.updateUserEnabled(enabled, uid);
    }

    public int deleteUserById(Long uid) {
        return userDao.deleteUserById(uid);
    }

    public int updateUserRoles(Long[] rids, Long id) {
        int i = userDao.deleteUserRolesByUid(id);
        return userDao.setUserRoles(rids, id);
    }

    public User getUserById(Long id) {
        return userDao.getUserById(id);
    }
}
