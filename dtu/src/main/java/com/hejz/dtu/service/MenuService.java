package com.hejz.dtu.service;

import com.hejz.dtu.dto.MenuFindByPageDto;
import com.hejz.dtu.entity.Menu;
import org.springframework.data.domain.Page;

import java.util.List;

/**
 *
 */
public interface MenuService {
    Menu Save(Menu menu);
    void delete(Integer id);
    Menu findById(Integer id);
    Page<Menu> findPage(MenuFindByPageDto dto);
}
