package com.jkz.XzRestServer.controller;


import com.jkz.XzRestServer.constant.PageConstant;
import com.jkz.XzRestServer.constant.ResourceNameConstant;
import com.jkz.XzRestServer.model.Item;
import com.jkz.XzRestServer.model.User;
import com.jkz.XzRestServer.model.dto.PaginatedResult;
import com.jkz.XzRestServer.model.dto.UserDto;
import com.jkz.XzRestServer.service.ItemService;
import com.jkz.XzRestServer.service.UserService;
import com.jkz.XzRestServer.util.PageUtil;
import com.jkz.XzRestServer.web.exception.ResourceNotFoundException;
import io.swagger.annotations.*;
import jdk.nashorn.internal.runtime.options.Option;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;


/**
 * Created by scuhmz on 2017/9/16.
 */
@RestController
@RequestMapping("api/v1/items")
@EnableAutoConfiguration
@Api(value = "API------Item相关操作",description = "卸载模块接口详情")
public class ItemController {
    private static Logger log= LoggerFactory.getLogger(ItemController.class);

    private ItemService itemService;
    private UserService userService;

    @Autowired
    public ItemController(ItemService itemService,UserService userService) {
        this.itemService = itemService;
        this.userService = userService;
    }

     @ApiOperation(value = "根据ID查找卸载信息",notes ="精确查询，结果只有一条",response = Item.class)
     @ApiImplicitParam(name = "id",value = "记录ID",required = true ,dataType = "String",paramType = "path")
     @RequestMapping(value = "/detail",method = RequestMethod.GET, produces = "application/json")
     public ResponseEntity<?> findItemById( @RequestParam(value = "id", required = true) String id){
        log.info("======enter findItemById()======");

        return itemService
                .findItemById(Integer.parseInt(id))
                .map(ResponseEntity::ok)
                .orElseThrow(() -> new ResourceNotFoundException()
                        .setResourceName(ResourceNameConstant.ITEM));

    }


    @RequestMapping(value = "/add",method = RequestMethod.POST, produces = "application/json")
    @ApiOperation(value = "新增卸载记录",notes ="添加一条卸载记录（User权限）",response = Item.class)
    @ApiImplicitParam(name = "item",value = "Item对象",required = true,dataType = "json",paramType = "body")
    public ResponseEntity<?> insertItem(@RequestBody Item item,Authentication authentication){
        log.info("======enter insertItem()======");
        String currentUser = authentication.getName();
        Optional<User> u = userService.findUserByUsername(currentUser);
        item.setUser_id(u.get().getId());
        item.setStatus(0);
        itemService.insert(item);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(item);

    }

    @ApiOperation(value = "修改卸载记录",notes ="修改卸载记录（Admin权限）",response = String.class)
    @ApiImplicitParam(name = "item",value = "Item对象",required = true,dataType = "json",paramType = "body")
    @RequestMapping(value = "/update",method = RequestMethod.PUT, produces = "application/json")
    public ResponseEntity<?> updateItem(@RequestBody Item item) {
        log.info("======enter updateItem()======");
        assertItemExist(item.getId());
        itemService.update(item);
        return ResponseEntity
                .status(HttpStatus.NO_CONTENT)
                .build();
    }

    @ApiOperation(value = "删除卸载记录",notes ="删除卸载记录（Admin权限）",response = String.class)
    @ApiImplicitParam(name = "id",value = "记录ID",required = true ,dataType = "number",paramType = "path")
    @RequestMapping(value = "/delete",method = RequestMethod.DELETE, produces = "application/json")
    public ResponseEntity<?> deleteItem(@RequestParam(value = "id", required = true) int id) {
        log.info("======enter deleteItem()======");
        assertItemExist(id);
        itemService.delete(id);
        return ResponseEntity
                .status(HttpStatus.NO_CONTENT)
                .build();
    }

//    @GetMapping
//    @ApiOperation(value = "卸载信息列表",notes ="返回卸载信息列表，多条数据",response = Item.class,responseContainer = "List")
//    public  ResponseEntity<?> findAllItems(){
//        log.info("======enter findAllItems()======");
//        return ResponseEntity
//                .status(HttpStatus.OK)
//                .body(itemService.findAllItems());
//    }

    @GetMapping
    @ApiOperation(value = "卸载记录列表",notes ="返回卸载记录列表，多条记录，支持分页",response = Item.class)
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
    public ResponseEntity<?> findItems(@RequestParam(value = "page", required = false) String pageString,
                                      @RequestParam(value = "count", required = false) String countString) {
        log.info("======enter findItems()======");
        
        // Parse request parameters
        int page = PageUtil.parsePage(pageString, PageConstant.PAGE);
        int perPage = PageUtil.parsePerPage(countString, PageConstant.PER_PAGE);

        return ResponseEntity
                .ok(new PaginatedResult()
                        .setData(itemService.findItemsByPage(page, perPage))
                        .setCurrentPage(page)
                        .setTotalCount(itemService.getTotalCount())
                        .setTotalPage(itemService.getTotalPage(perPage)));
    }

    @ApiOperation(value = "批量删除卸载记录",notes ="批量删除卸载记录（Admin权限）")
    @ApiImplicitParam(name = "ids",value = "记录ID数组",required = true ,dataType = "array",paramType = "body")
    @RequestMapping(value = "/delete/batch",method = RequestMethod.DELETE, produces = "application/json")
    public ResponseEntity<?> deleteBatch(@RequestBody int[] ids){
        log.info("======enter deleteBatch()======");
        itemService.deleteBatch(ids);
        return ResponseEntity
                .status(HttpStatus.NO_CONTENT)
                .build();
    }
    /********************************** HELPER METHOD **********************************/
    private void assertItemExist(int id) {
        log.info("======enter assertItemExist()======");
        itemService
                .findItemById(id)
                .orElseThrow(() -> new ResourceNotFoundException()
                        .setResourceName(ResourceNameConstant.ITEM));
    }
}
