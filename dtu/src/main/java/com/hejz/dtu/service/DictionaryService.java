package com.hejz.dtu.service;

import com.hejz.dtu.common.Result;
import com.hejz.dtu.dto.*;
import com.hejz.dtu.entity.Dictionary;
import org.springframework.data.domain.Page;

import java.util.List;

/**
 *
 */
public interface DictionaryService {

    Dictionary save(Dictionary dictionary);

    Dictionary update(Dictionary dictionary);

    void delete(Long id);

    Dictionary findById(Long id);

    Page<Dictionary> findPage(DictionaryFindByPageDto dto);

    List<Dictionary> findAll(DictionaryAllDto dto);

    Result getParent();

    List<Dictionary> findAllByDictionary(Dictionary dto);
}
