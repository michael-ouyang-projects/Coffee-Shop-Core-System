package com.ouyang.coffee;

import java.util.NoSuchElementException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CoffeeService {

    @Autowired
    private CoffeeRepository coffeeRepository;

    public Coffee queryCoffeeById(Long id) {

        try {

            return coffeeRepository.findById(id).get();

        } catch (NoSuchElementException e) {

            throw new NoSuchElementException("No such Coffee!");

        }

    }

}
