package com.jkz.XzRestServer.controller;

import com.jkz.XzRestServer.constant.ResourceNameConstant;
import com.jkz.XzRestServer.model.User;
import com.jkz.XzRestServer.model.dto.CountDto;
import com.jkz.XzRestServer.service.CountService;
import com.jkz.XzRestServer.service.UserService;
import com.jkz.XzRestServer.web.exception.ResourceNotFoundException;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.*;

/**
 * Created by scuhmz on 2017/12/2.
 */

@RestController
@RequestMapping("api/v1/count")
@EnableAutoConfiguration
@Api(value = "API------Count相关操作",description = "数据统计模块接口详情")
public class CountController {

    private static Logger log= LoggerFactory.getLogger(CountController.class);

    private CountService countService;
    private UserService userService;
    public CountController(CountService countService,UserService userService) {
        this.countService = countService;
        this.userService = userService;
    }

    @ApiOperation(value = "按照年份统计当前用户的卸载记录",notes ="分别列出飞行、滑行、地面次数",response = String.class)
    @ApiImplicitParam(name = "year",value = "年份，默认当年",required = false ,dataType = "string",paramType = "path")

    @RequestMapping(value = "/user",method = RequestMethod.GET, produces = "application/json")
    public ResponseEntity<?> CountItemFxHxDmByYearAndUid(Authentication authentication,@RequestParam(value = "year", required = false) String year) {
        log.info("======enter CountItemFxHxDmByYearAndUid()======");

        String currentUser = authentication.getName();
        Optional<User> u = userService.findUserByUsername(currentUser);
        if (u==null)
            new ResourceNotFoundException()
                    .setResourceName(ResourceNameConstant.USER);
        if (year==null || year=="")
        {
            Calendar now = Calendar.getInstance();
            year = String.valueOf(now.get(Calendar.YEAR));
        }
        List<CountDto> countList = countService.CountItemFxHxDmByYearAndUid(year,u.get().getId());
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(CountItemFxHxDmByYearResult(year, countList));
    }

    @ApiOperation(value = "按照年份统计飞行",notes ="分别列出飞行、滑行、地面次数",response = String.class)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "year",value = "年份，默认当年",required = false ,dataType = "string",paramType = "path"),
            @ApiImplicitParam(name = "flight",value = "飞机号",required = false ,dataType = "string",paramType = "path")
    })
    @GetMapping
    public ResponseEntity<?> CountItemFxHxDmByYear(@RequestParam(value = "year", required = false) String year,
                                                   @RequestParam(value = "flight", required = false) String flight) {
        log.info("======enter CountItemFxHxDmByYear()======");
        if (year==null || year=="")
        {
            Calendar now = Calendar.getInstance();
            year = String.valueOf(now.get(Calendar.YEAR));
        }
        List<CountDto> countList = countService.CountItemFxHxDmByYear(year,flight);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(CountItemFxHxDmByYearResult(year, countList));
    }

    /********************************** HELPER METHOD **********************************/
    private List<Map<String,String>>  CountItemFxHxDmByYearResult(String year,List<CountDto> countList) {
        log.info("======enter CountItemFxHxDmByYearResult()======");
        Map<String,Object> result = new HashMap<String,Object>();
        List<Map<String,String>> result_list = new ArrayList<Map<String,String>>();
       for (int i=1;i<=12;i++)
       {
           String currentDate = year+"-"+String.format("%02d",i);
           Map<String,String> sub_result = new HashMap<String,String>();
           sub_result.put("Month",currentDate);
           sub_result.put("Fx","0");
           sub_result.put("Hx","0");
           sub_result.put("Dm","0");
           result_list.add(sub_result);
       }
        countList.forEach(item->{
//            Map<String,Integer> get_sub_result = (Map<String,Integer>)result.get(item.getYear_and_month());
//            if (get_sub_result!=null)
//            get_sub_result.replace(item.getFlightFx_Hx_DM(),item.getFlightFx_Hx_DM_count());
            result_list.forEach(item_in->{
                if (item.getYear_and_month().equals(item_in.get("Month")))
                {
                    item_in.replace(item.getFlightFx_Hx_DM(),item.getFlightFx_Hx_DM_count());
                }
            });
           });
        return result_list;
    }
}
