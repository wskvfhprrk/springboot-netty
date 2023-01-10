package com.hejz.studay.service;

import com.hejz.studay.entity.Dictionary;
import org.springframework.data.domain.Page;

import java.util.List;

/**
 *
 */
public interface DictionaryService {
    Dictionary Save(Dictionary dictionary);
    void delete(Long id);
    Dictionary findById(Long id);
    Page<Dictionary> findPage(Dictionary dictionary, int pageNo,int pageSize);
    List<Dictionary> findAll(Dictionary dictionary);
}
