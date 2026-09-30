package com.ai.user.controller;

import com.ai.user.dto.PageRequest;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 用户分页查询请求
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class UserQueryRequest extends PageRequest {

    /** 用户名模糊搜索 */
    private String username;
}