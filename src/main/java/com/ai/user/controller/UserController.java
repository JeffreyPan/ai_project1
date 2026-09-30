package com.ai.user.controller;

import com.ai.user.common.PageResult;
import com.ai.user.common.Response;
import com.ai.user.dto.UserCreateRequest;
import com.ai.user.dto.UserUpdateRequest;
import com.ai.user.entity.User;
import com.ai.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

/**
 * 用户管理接口
 */
@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
@Validated
public class UserController {

    private final UserService userService;

    /**
     * 分页查询用户列表
     */
    @GetMapping
    public Response<PageResult<User>> page(@Valid UserQueryRequest request) {
        return Response.success(userService.page(
                request.getUsername(), request.getPageNum(), request.getPageSize()));
    }

    /**
     * 创建用户
     */
    @PostMapping
    public Response<User> create(@Valid @RequestBody UserCreateRequest request) {
        return Response.success(userService.create(request));
    }

    /**
     * 更新用户
     */
    @PutMapping("/{id}")
    public Response<User> update(@PathVariable Long id,
                                 @Valid @RequestBody UserUpdateRequest request) {
        return Response.success(userService.update(id, request));
    }

    /**
     * 删除用户
     */
    @DeleteMapping("/{id}")
    public Response<Void> delete(@PathVariable Long id) {
        userService.delete(id);
        return Response.success();
    }
}