package com.jkz.XzRestServer.service.imp;

import com.jkz.XzRestServer.dao.RoleMapper;
import com.jkz.XzRestServer.dao.UserMapper;
import com.jkz.XzRestServer.dao.UserRoleMapper;
import com.jkz.XzRestServer.model.Role;
import com.jkz.XzRestServer.model.User;
import com.jkz.XzRestServer.model.dto.UserDto;
import com.jkz.XzRestServer.service.UserService;
import com.jkz.XzRestServer.util.PageUtil;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

/**
 * Created by scuhmz on 2017/9/17.
 */
@Service
public class UserServiceImpl implements UserService {

    private final UserMapper userMapper;
    private final UserRoleMapper userRoleMapper;
    private final RoleMapper roleMapper;
    @Autowired
    private ModelMapper modelMapper;
    @Autowired
    public UserServiceImpl(UserMapper userMapper,UserRoleMapper userRoleMapper,RoleMapper roleMapper) {

        this.userMapper = userMapper;
        this.userRoleMapper = userRoleMapper;
        this.roleMapper = roleMapper;
    }


    @Override
    public Optional<User> findUserById(int id) {

        return Optional.ofNullable(userMapper.findUserById(id));
    }

    @Override
    public Optional<User> findUserByUsername(String username) {
        return Optional.ofNullable(userMapper.findUserByUsername(username));
    }

    @Override
    public List<String> findUserRolesByUid(int id) {
       return userRoleMapper.findUserRolesByUid(id);
    }

    @Override
    public List<UserDto> findAllUsers() {
        List<User> user_list = userMapper.findAllUsers();

        return  user_list.stream()
                .map(post-> modelMapper.map(post, UserDto.class))
                .collect(Collectors.toList());
    }

    @Override
    public List<User> findUsersByPage(int page, int perPage) {
        int offset = PageUtil.calculateOffset(page, perPage);
        return userMapper.findUsersByPage(offset, perPage);
    }

    @Override
    @Transactional
    public boolean insertUser(User user) {
        if(userMapper.insertUser(user)>0)
        {
           int uid =  this.findUserByUsername(user.getUsername()).get().getId();
            user.getRoles().forEach(role->{
                Role r = roleMapper.findRoleByRoleName(role);
                if(r!=null)
                {
                    userRoleMapper.insertRoleByUid(uid,r.getId());
                }
            });
            return true;
        }
        else
            return false;
    }


    @Override
    public int getTotalPage(int perPage) {
        return PageUtil.calculateTotalPage(userMapper.selectCount(), perPage);
    }

    @Override
    public int getTotalCount() {
        return  userMapper.selectCount();
    }

    @Override
    @Transactional
    public boolean update(User user) {
        if(userMapper.update(user)>0)
        {
           if( userRoleMapper.deleteUserRolesByUid(user.getId())>0)
           {
               user.getRoles().forEach(role->{
                   Role r = roleMapper.findRoleByRoleName(role);
                   if(r!=null)
                   {
                       userRoleMapper.insertRoleByUid(user.getId(),r.getId());
                   }
               });
               return true;
           }
            else
               return false;
        }
        else
            return false;
    }

    @Override
    @Transactional
    public boolean delete(int id) {
        return userMapper.delete(id)>0;
    }

    @Override
    @Transactional
    public boolean deleteBatch(int[] ids) {
        return userMapper.deleteByIds(ids)>0;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userMapper.findUserByUsername(username);

        if (user == null) {
            throw new UsernameNotFoundException("用户名:" + username + " 不存在！");
        }
        Collection<SimpleGrantedAuthority> collection = new HashSet<SimpleGrantedAuthority>();
        Iterator<String> iterator = userRoleMapper.findUserRolesByUid(user.getId()).iterator();
        while (iterator.hasNext()) {
            collection.add(new SimpleGrantedAuthority(iterator.next()));
        }

        return new org.springframework.security.core.userdetails.User(username,user.getPassword(),collection);
    }
}
