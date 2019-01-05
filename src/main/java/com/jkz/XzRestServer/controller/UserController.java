package com.jkz.XzRestServer.controller;


import com.jkz.XzRestServer.constant.PageConstant;
import com.jkz.XzRestServer.constant.ResourceNameConstant;
import com.jkz.XzRestServer.model.Item;
import com.jkz.XzRestServer.model.User;
import com.jkz.XzRestServer.model.dto.PaginatedResult;
import com.jkz.XzRestServer.model.dto.UserDto;
import com.jkz.XzRestServer.service.UserService;
import com.jkz.XzRestServer.util.PageUtil;
import com.jkz.XzRestServer.web.exception.ParameterIllegalException;
import com.jkz.XzRestServer.web.exception.ResourceNotFoundException;
import io.swagger.annotations.*;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.token.TokenService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.provider.token.ConsumerTokenServices;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.Optional;

/**
 * Created by scuhmz on 2017/9/16.
 */
@RestController
@RequestMapping("api/v1/users")
@EnableAutoConfiguration
@Api(value = "API------User相关操作",description = "用户模块接口详情")
public class UserController {
    private static Logger log= LoggerFactory.getLogger(UserController.class);

    private UserService userService;

    @Autowired
    PasswordEncoder encoder;

    @Autowired
    ConsumerTokenServices tokenService;
    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

     @ApiOperation(value = "根据用户ID查找用户信息",notes ="精确查询，结果只有一条",response = User.class)
     @ApiImplicitParam(name = "id",value = "用户ID",required = true ,dataType = "String",paramType = "path")
     @RequestMapping(value = "/detail",method = RequestMethod.GET, produces = "application/json")
     public ResponseEntity<?> findUserById(@RequestParam(value = "id", required = true) String id){
        log.info("======enter findUserById()======");

        return userService
                .findUserById(Integer.parseInt(id))
                .map(ResponseEntity::ok)
                .orElseThrow(() -> new ResourceNotFoundException()
                        .setResourceName(ResourceNameConstant.USER));

    }

    @ApiOperation(value = "返回当前登录用户信息",notes ="需要携带TOKEN访问",response = User.class)
    @RequestMapping(value = "/currentUser", method = RequestMethod.GET)
    public ResponseEntity<?> getCurrentUser(Authentication authentication ){

        String currentUser = authentication.getName();

        return userService
                .findUserByUsername(currentUser)
                .map(ResponseEntity::ok)
                .orElseThrow(() -> new ResourceNotFoundException()
                        .setResourceName(ResourceNameConstant.USER));

    }

    @ApiOperation(value = "注销登录",notes ="撤销TOKEN",response = String.class)
    @RequestMapping(value = "/logout", method = RequestMethod.GET)
    public ResponseEntity<?> logOut(HttpServletRequest request ){

        String authorization = request.getHeader("Authorization");
        if (authorization != null && authorization.contains("Bearer")){
            String tokenId = authorization.substring("Bearer".length()+1);
            tokenService.revokeToken(tokenId);
            return ResponseEntity
                    .noContent()
                    .build();
        }else {
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body(new ParameterIllegalException());
        }

    }

    @GetMapping
    @ApiOperation(value = "用户列表",notes ="返回用户列表，多条记录，支持分页",response = User.class)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "page", value = "当前页码", required = false,
                    dataType = "String", paramType = "query",defaultValue = "1"),
            @ApiImplicitParam(name = "count", value = "一页返回数据量", required = false,
                    dataType = "String", paramType = "query",defaultValue = "10")
    })
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successful — 请求已完成"),
            @ApiResponse(code = 400, message = "请求中有语法问题，或不能满足请求"),
            @ApiResponse(code = 401, message = "未授权客户机访问数据"),
            @ApiResponse(code = 404, message = "服务器找不到给定的资源；文档不存在"),
            @ApiResponse(code = 500, message = "服务器不能完成请求")}
    )
     public  ResponseEntity<?> findAllUsers(@RequestParam(value = "page", required = false) String pageString,
                                            @RequestParam(value = "count", required = false) String countString){
        log.info("======enter findAllUsers()======");
        int page = PageUtil.parsePage(pageString, PageConstant.PAGE);
        int perPage = PageUtil.parsePerPage(countString, PageConstant.PER_PAGE);

        return ResponseEntity
                .ok(new PaginatedResult()
                        .setData(userService.findUsersByPage(page, perPage))
                        .setCurrentPage(page)
                        .setTotalCount(userService.getTotalCount())
                        .setTotalPage(userService.getTotalPage(perPage)));
    }

    @RequestMapping(value = "/add",method = RequestMethod.POST, produces = "application/json")
    @ApiOperation(value = "新增用户",notes ="添加用户(Admin权限)",response = User.class)
    @ApiImplicitParam(name = "user",value = "User对象",required = true,dataType = "json",paramType = "body")
    public ResponseEntity<?> insertUser(@RequestBody User user){
        log.info("======enter insertUser()======");
        String hashedPassword = encoder.encode(user.getPassword());
        user.setPassword(hashedPassword);
        userService.insertUser(user);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(user);

    }

    @RequestMapping(value = "/update",method = RequestMethod.PUT, produces = "application/json")
    @ApiOperation(value = "更新用户",notes ="更新用户(Admin权限)",response = User.class)
    @ApiImplicitParam(name = "user",value = "User对象",required = true,dataType = "json",paramType = "body")
    public ResponseEntity<?> updateUser(@RequestBody User user){
        log.info("======enter updateUser()======");
        User u = userService.findUserByUsername(user.getUsername()).get();
        if(u==null)
            throw  new ResourceNotFoundException().setResourceName(ResourceNameConstant.USER);
        String hashedPassword = encoder.encode(user.getPassword());
        u.setPassword(hashedPassword);
        u.setUsername(user.getUsername());
        u.setRoles(user.getRoles());
        userService.update(u);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(user);

    }

    @ApiOperation(value = "删除用户",notes ="删除用户（Admin权限）",response = String.class)
    @ApiImplicitParam(name = "id",value = "用户ID",required = true ,dataType = "number",paramType = "path")
    @RequestMapping(value = "/delete",method = RequestMethod.DELETE, produces = "application/json")
    public ResponseEntity<?> deleteUser(@RequestParam(value = "id", required = true) int id) {
        log.info("======enter deleteUser()======");
        assertUserExist(id);
        userService.delete(id);
        return ResponseEntity
                .status(HttpStatus.NO_CONTENT)
                .build();
    }

    @ApiOperation(value = "批量删除用户",notes ="批量删除用户（Admin权限）")
    @ApiImplicitParam(name = "ids",value = "用户ID数组",required = true ,dataType = "array",paramType = "body")
    @RequestMapping(value = "/delete/batch",method = RequestMethod.DELETE, produces = "application/json")
    public ResponseEntity<?> deleteBatch(@RequestBody int[] ids){
        log.info("======enter deleteBatch()======");
        userService.deleteBatch(ids);
        return ResponseEntity
                .status(HttpStatus.NO_CONTENT)
                .build();
    }

    /********************************** HELPER METHOD **********************************/
    private void assertUserExist(int id) {
        log.info("======enter assertUserExist()======");
        userService
                .findUserById(id)
                .orElseThrow(() -> new ResourceNotFoundException()
                        .setResourceName(ResourceNameConstant.USER));
    }
}
