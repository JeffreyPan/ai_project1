package com.ai.user.mapper;

import com.ai.user.entity.User;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 用户 Mapper 接口
 */
public interface UserMapper {

    /**
     * 分页查询用户列表（支持用户名模糊查询）
     */
    List<User> selectPage(@Param("username") String username);

    /**
     * 根据 ID 查询
     */
    User selectById(@Param("id") Long id);

    /**
     * 按用户名精确查询（用于唯一性校验）
     */
    User selectByUsername(@Param("username") String username);

    /**
     * 新增用户
     */
    int insert(User user);

    /**
     * 更新用户
     */
    int updateById(User user);

    /**
     * 删除用户
     */
    int deleteById(@Param("id") Long id);
}