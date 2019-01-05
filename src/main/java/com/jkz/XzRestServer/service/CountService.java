package com.jkz.XzRestServer.service;

import com.jkz.XzRestServer.model.Item;
import com.jkz.XzRestServer.model.dto.CountDto;

import java.util.List;
import java.util.Optional;

/**
 * Created by scuhmz on 2017/9/17.
 */
public interface CountService {

    List<CountDto> CountItemFxHxDmByYear(String year,String flight);
    List<CountDto>  CountItemFxHxDmByYearAndUid(String year,int uid);
}
