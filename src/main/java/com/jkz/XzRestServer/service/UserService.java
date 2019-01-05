package com.jkz.XzRestServer.service;

import com.jkz.XzRestServer.model.User;
import com.jkz.XzRestServer.model.dto.UserDto;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/**
 * Created by scuhmz on 2017/9/17.
 */
public interface UserService extends UserDetailsService{
    //List<User> getAllUsers();
  //  int saveUser(User user);
   // int deleteUserById(int id);
   // int updateUserById(int id);
    Optional<User> findUserById(int id);
    Optional<User> findUserByUsername(String username);
    List<String> findUserRolesByUid(int id);
    List<UserDto> findAllUsers();
    List<User> findUsersByPage(int page,int perPage);
    boolean insertUser(User user);
    int getTotalPage(int perPage);
    int getTotalCount();
    boolean update(User user);
    boolean delete(int id);
    boolean deleteBatch(int[] ids);
}
