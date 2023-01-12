package com.hejz.studay.service;

import com.hejz.studay.entity.Relay;

import java.util.List;

public interface DataCheckingRules {
    Relay getById(Integer id);

    Relay save(Relay relay);

    Relay update(Relay relay);

    void delete(Long id);

}
