package com.jkz.XzRestServer.service;

import com.jkz.XzRestServer.model.Item;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/**
 * Created by scuhmz on 2017/9/17.
 */
public interface ItemService{

    Optional<Item> findItemById(int id);
    boolean insert(Item item);
    boolean update(Item item);
    boolean delete(int id);
    boolean deleteBatch(int[] ids);
    List<Item> findAllItems();
    List<Item> findItemsByPage(int page,int perPage);
    int getTotalPage(int perPage);
    int getTotalCount();

}
