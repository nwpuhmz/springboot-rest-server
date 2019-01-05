package com.jkz.XzRestServer.service.imp;

import com.jkz.XzRestServer.dao.YuyueMapper;
import com.jkz.XzRestServer.model.Yuyue;
import com.jkz.XzRestServer.service.YuyueService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by scuhmz on 2017/12/25.
 */
@Service
public class YuyueserviceImpl implements YuyueService {
    private final YuyueMapper yuyueMapper;

    @Autowired
    public YuyueserviceImpl(YuyueMapper yuyueMapper)
    {
        this.yuyueMapper = yuyueMapper;
    }
    @Override
    public List<Yuyue> findAllYuyue() {
        return yuyueMapper.findAllYuyue();
    }
}
