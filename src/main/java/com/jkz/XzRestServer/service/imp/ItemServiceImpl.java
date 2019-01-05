package com.jkz.XzRestServer.service.imp;

import com.jkz.XzRestServer.dao.ItemMapper;
import com.jkz.XzRestServer.model.Item;
import com.jkz.XzRestServer.service.ItemService;
import com.jkz.XzRestServer.util.PageUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

/**
 * Created by scuhmz on 2017/9/17.
 */
@Service
public class ItemServiceImpl implements ItemService {

    private final ItemMapper itemMapper;

    @Autowired
    public ItemServiceImpl(ItemMapper itemMapper) {
           this.itemMapper = itemMapper;
    }

    @Override
    public Optional<Item> findItemById(int id) {
        return Optional.ofNullable(itemMapper.findItemById(id));
    }

    @Override
    @Transactional
    public boolean insert(Item item) {
        if(itemMapper.insert(item)>0)
        {
            return true;
        }

        else
            return false;
    }

    @Override
    @Transactional
    public boolean update(Item item) {
        if(itemMapper.update(item)>0)
        {
            return true;
        }

        else
            return false;
    }

    @Override
    @Transactional
    public boolean delete(int id) {
        if (itemMapper.delete(id)>0)
            return true;
        else
            return false;
    }

    @Override
    public List<Item> findAllItems() {
        return itemMapper.findAllItems();
    }

    @Override
    public List<Item> findItemsByPage(int page,int perPage) {
        int offset = PageUtil.calculateOffset(page, perPage);
        return itemMapper.findItemsByPage(offset, perPage);
    }

    @Override
    public int getTotalPage(int perPage) {
        return PageUtil.calculateTotalPage(itemMapper.selectCount(), perPage);
    }

    @Override
    public int getTotalCount() {
        return  itemMapper.selectCount();
    }

    @Override
    @Transactional
    public boolean deleteBatch(int[] ids) {
        return itemMapper.deleteByIds(ids)>0;
    }
}
