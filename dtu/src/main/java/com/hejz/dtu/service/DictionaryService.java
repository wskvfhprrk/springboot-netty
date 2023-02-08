package com.hejz.dtu.service;

import com.hejz.dtu.dto.DictionaryFindByPageDto;
import com.hejz.dtu.entity.Dictionary;
import org.springframework.data.domain.Page;


/**
 *
 */
public interface DictionaryService {
    Dictionary save(Dictionary dictionary);

    Dictionary update(Dictionary dictionary);

    void delete(Long id);

    Dictionary findById(Long id);

    Page<Dictionary> findPage(DictionaryFindByPageDto dto);
}
