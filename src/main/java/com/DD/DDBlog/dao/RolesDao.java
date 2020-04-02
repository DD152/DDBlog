package com.DD.DDBlog.dao;

import com.DD.DDBlog.entity.Role;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface RolesDao {
    int addRoles(@Param("roles") String[] roles, @Param("uid") Long uid);

    List<Role> getRolesByUid(Long uid);
}
