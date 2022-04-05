package com.epam.esm.impl;

import com.epam.esm.OrderService;
import com.epam.esm.UserService;
import com.epam.esm.dao.UserDao;
import com.epam.esm.dto.UserClientModel;
import com.epam.esm.entity.UserEntity;
import com.epam.esm.handler.exception.BadParameterException;
import com.epam.esm.mapper.UserModelMapper;
import com.epam.esm.validator.exception.UnknownUserException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

import static com.epam.esm.handler.RequestParameter.*;

@Service
public class UserServiceImpl implements UserService {
    private static final String NONEXISTENT_USER = "nonexistent.user";
    private static final String BAD_PARAM = "bad.param";
    private UserDao userDao;
    private UserModelMapper userMapper;

    @Override
    public List<UserClientModel> readByParameters(Map<String, String> parameters) {
        for (Map.Entry<String, String> parameter : parameters.entrySet()) {
            if (isRequiredParameter(parameter.getKey())) {
                switch (parameter.getKey()) {
                    case ID:
                        return Collections.singletonList(readById(Long.parseLong(parameter.getValue())));
                    case NICKNAME:
                        return Collections.singletonList(readByNickname(parameter.getValue()));
                    default:
                        return Collections.singletonList(readByMail(parameter.getValue()));

                }
            }
        }
        throw new BadParameterException(BAD_PARAM);
    }

    @Override
    public List<UserClientModel> readAll(Map<String, String> parameters) {
        checkPaginationParameters(parameters);
        return userDao.readAll(Long.parseLong(parameters.remove(PAGE)),
                Long.parseLong(parameters.remove(PAGE_SIZE)))
                .stream()
                .map(a -> userMapper.toClientModel(a))
                .collect(Collectors.toList());
    }

    @Override
    public UserClientModel readById(long id) {
        Optional<UserEntity> user = userDao.readById(id);
        if (user.isPresent()) {
            return userMapper.toClientModel(user.get());
        }
        throw new UnknownUserException(NONEXISTENT_USER + "/id=" + id);
    }

    @Override
    public UserClientModel readByNickname(String nickname) {
        Optional<UserEntity> user = userDao.readByNickname(nickname);
        if (user.isPresent()) {
            return userMapper.toClientModel(user.get());
        }
        throw new UnknownUserException(NONEXISTENT_USER + "/nickname=" + nickname);
    }

    @Override
    public UserClientModel readByMail(String mail) {
        Optional<UserEntity> user = userDao.readByMail(mail);
        if (user.isPresent()) {
            return userMapper.toClientModel(user.get());
        }
        throw new UnknownUserException(NONEXISTENT_USER + "/mail=" + mail);
    }

    private void checkPaginationParameters(Map<String , String> parameters) {
        if (!parameters.containsKey(PAGE) || !parameters.containsKey(PAGE_SIZE)) {
            throw new BadParameterException(BAD_PARAM);
        }
    }

    private boolean isRequiredParameter(String parameter) {
        return parameter.equals(ID) || parameter.equals(MAIL)
                || parameter.equals(NICKNAME);
    }

    @Autowired
    public void setUserMapper(UserModelMapper userMapper) {
        this.userMapper = userMapper;
    }

    @Autowired
    public void setUserDao(UserDao userDao) {
        this.userDao = userDao;
    }
}
