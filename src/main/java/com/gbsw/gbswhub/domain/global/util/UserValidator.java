package com.gbsw.gbswhub.domain.global.util;

import com.gbsw.gbswhub.domain.global.Error.ErrorCode;
import com.gbsw.gbswhub.domain.global.Exception.BusinessException;
import com.gbsw.gbswhub.domain.user.model.User;

public class UserValidator {
    public static void validateUser(User user){
        if(user == null) {
            throw new BusinessException(ErrorCode.USER_NOT_FOUND);
        }
    }
}
