package com.ai.user.service;

import com.ai.user.common.PageResult;
import com.ai.user.dto.UserCreateRequest;
import com.ai.user.dto.UserUpdateRequest;
import com.ai.user.entity.User;

/**
 * 用户服务接口
 */
public interface UserService {

    /**
     * 分页查询用户列表
     */
    PageResult<User> page(String username, int pageNum, int pageSize);

    /**
     * 根据 ID 查询
     */
    User getById(Long id);

    /**
     * 创建用户
     */
    User create(UserCreateRequest request);

    /**
     * 更新用户
     */
    User update(Long id, UserUpdateRequest request);

    /**
     * 删除用户
     */
    void delete(Long id);
}