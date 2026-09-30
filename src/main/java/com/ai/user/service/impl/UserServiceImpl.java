package com.ai.user.service.impl;

import com.ai.user.common.PageResult;
import com.ai.user.dto.UserCreateRequest;
import com.ai.user.dto.UserUpdateRequest;
import com.ai.user.entity.User;
import com.ai.user.exception.BusinessException;
import com.ai.user.mapper.UserMapper;
import com.ai.user.service.UserService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

/**
 * 用户服务实现
 */
@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserMapper userMapper;

    @Override
    public PageResult<User> page(String username, int pageNum, int pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        List<User> list = userMapper.selectPage(username);
        PageInfo<User> pageInfo = new PageInfo<>(list);
        return new PageResult<>(pageInfo.getTotal(), pageInfo.getPageNum(), pageInfo.getPageSize(),
                pageInfo.getList());
    }

    @Override
    public User getById(Long id) {
        return getUserOrThrow(id);
    }

    @Override
    @Transactional
    public User create(UserCreateRequest request) {
        if (userMapper.selectByUsername(request.getUsername()) != null) {
            throw new BusinessException("用户名已存在");
        }
        User user = new User();
        BeanUtils.copyProperties(request, user);
        if (user.getStatus() == null) {
            user.setStatus(1);
        }
        user.setCreateTime(LocalDateTime.now());
        user.setUpdateTime(LocalDateTime.now());
        userMapper.insert(user);
        return user;
    }

    @Override
    @Transactional
    public User update(Long id, UserUpdateRequest request) {
        User user = getUserOrThrow(id);

        // 用户名唯一性校验（排除自身）
        User byUsername = userMapper.selectByUsername(request.getUsername());
        if (byUsername != null && !byUsername.getId().equals(id)) {
            throw new BusinessException("用户名已存在");
        }

        BeanUtils.copyProperties(request, user, "id");
        user.setUpdateTime(LocalDateTime.now());
        userMapper.updateById(user);
        return user;
    }

    @Override
    @Transactional
    public void delete(Long id) {
        User user = getUserOrThrow(id);
        userMapper.deleteById(user.getId());
    }

    private User getUserOrThrow(Long id) {
        User user = userMapper.selectById(id);
        if (user == null) {
            throw new BusinessException(404, "用户不存在");
        }
        return user;
    }
}