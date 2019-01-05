package com.jkz.XzRestServer.service.imp;

import com.jkz.XzRestServer.dao.CountMapper;
import com.jkz.XzRestServer.dao.ItemMapper;
import com.jkz.XzRestServer.model.Item;
import com.jkz.XzRestServer.model.dto.CountDto;
import com.jkz.XzRestServer.service.CountService;
import com.jkz.XzRestServer.service.ItemService;
import com.jkz.XzRestServer.util.PageUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/**
 * Created by scuhmz on 2017/9/17.
 */
@Service
public class CountServiceImpl implements CountService {

    private final CountMapper countMapper;

    @Autowired
    public CountServiceImpl(CountMapper countMapper) {
           this.countMapper = countMapper;
    }


    @Override
    public List<CountDto> CountItemFxHxDmByYear(String year,String flight) {
        return countMapper.CountItemFxHxDmByYear(year,flight);
    }

    @Override
    public List<CountDto> CountItemFxHxDmByYearAndUid(String year, int uid) {
        return countMapper.CountItemFxHxDmByYearAndUid(year,uid);
    }
}
