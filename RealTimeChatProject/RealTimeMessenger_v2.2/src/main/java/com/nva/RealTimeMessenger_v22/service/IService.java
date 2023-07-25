package com.nva.RealTimeMessenger_v22.service;

import com.nva.RealTimeMessenger_v22.dto.UserDto;

public interface IService {
    public UserDto authentication(String token);
}
