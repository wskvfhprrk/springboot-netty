package com.hejz.dtu.repository;

import com.hejz.dtu.entity.Menu;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

/**
 * 菜单 dao层
 * author: hejz
 * data: 2023-2-7
 */
public interface MenuRepository extends JpaRepository<Menu,Integer>,JpaSpecificationExecutor<Menu> {
}
