package com.hejz.service;

import com.hejz.entity.Relay;

public interface DataCheckingRules {
    Relay getById(Integer id);

    Relay save(Relay relay);

    Relay update(Relay relay);

    void delete(Long id);

}
