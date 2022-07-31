package com.airjnc.common.auth.service;

import com.airjnc.common.auth.dto.AuthInfoDTO;

public interface AuthService {

    AuthInfoDTO getAuthInfo();

    void setAuthInfo(AuthInfoDTO authInfoDTO);

    void clearAuthInfo();


}
