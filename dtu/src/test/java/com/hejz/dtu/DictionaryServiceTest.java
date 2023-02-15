package com.hejz.dtu;

import com.hejz.dtu.enm.DictionaryTypeEnum;
import com.hejz.dtu.entity.Dictionary;
import com.hejz.dtu.service.DictionaryService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;

import java.util.Date;
import java.util.List;

class DictionaryServiceTest extends DemoApplicationTests {

    @Autowired
    DictionaryService dictionaryService;

    @Test
    void save() {
        for (int i = 0; i < 10; i++) {
            Dictionary dictionary = new Dictionary();
            dictionary.setAppModule("aaaaaas" + i);
            dictionary.setDescription("bbbb"+i);
            dictionary.setCreateTime(new Date());
            dictionary.setIsUse(true);
            dictionary.setItemName("itemName");
            dictionary.setType(DictionaryTypeEnum.CLASS_A);
            dictionary.setItemValue("itemValue"+i);
            dictionary.setSortId(1);
            dictionaryService.save(dictionary);
        }
    }

    @Test
    void delete() {
        dictionaryService.delete(4L);
    }

    @Test
    void findById() {
        Dictionary dictionary = dictionaryService.findById(5L);
        System.out.println(dictionary);
    }

    @Test
    void findPage() {
        Dictionary d=new Dictionary();
        d.setAppModule("aaa");
//        Page<Dictionary> page = dictionaryService.findPage(d, 0, 5);
//        System.out.println(page);
    }

    @Test
    void findAll() {
        Dictionary d=new Dictionary();
        d.setAppModule("aaaaa");
//        List<Dictionary> list = dictionaryService.findAll(d);
//        System.out.println(list);
    }
}