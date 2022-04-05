package com.epam.esm;

import com.epam.esm.dto.UserClientModel;

import java.util.List;
import java.util.Map;

public interface UserService {

    List<UserClientModel> readByParameters(Map<String, String> parameters);

    List<UserClientModel> readAll(Map<String, String> parameters);

    UserClientModel readById(long id);

    UserClientModel readByNickname(String nickname);

    UserClientModel readByMail(String mail);
}
