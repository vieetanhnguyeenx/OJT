package com.nva.RealTimeMessenger_v21.service;

import com.nva.RealTimeMessenger_v21.dto.UserDto;

public interface IService {
    public UserDto authentication(String token);
}
