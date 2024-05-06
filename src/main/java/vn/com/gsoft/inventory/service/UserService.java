package vn.com.gsoft.inventory.service;


import vn.com.gsoft.inventory.model.system.Profile;

import java.util.Optional;

public interface UserService {
    Optional<Profile> findUserByToken(String token);

}
