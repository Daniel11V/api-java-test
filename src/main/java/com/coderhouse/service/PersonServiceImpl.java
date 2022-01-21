package com.coderhouse.service;

import org.springframework.stereotype.Service;

@Service
public class PersonServiceImpl implements PersonService {

    @Override
    public String nombresCompletos() {
        return "Jose Ignacio";
    }
}
