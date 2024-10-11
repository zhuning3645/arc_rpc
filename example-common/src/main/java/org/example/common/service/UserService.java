package org.example.common.service;

import org.example.common.model.User;

/**
 * 用户服务
 * 调用的远程服务
 */
public interface UserService {

    /**
     * 获取用户
     * @param user
     * @return
     */
    User getUser(User user);

    User getMessage(User user);

    String addUser(User user);
}
