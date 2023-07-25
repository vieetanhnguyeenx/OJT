package com.nva.RealTimeMessenger_v20.service;

import com.nva.RealTimeMessenger_v20.dto.UserDto;

public interface IService {
    public UserDto authentication(String token);
}
