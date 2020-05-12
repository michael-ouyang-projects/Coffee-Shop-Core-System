package com.ouyang.customer;

import java.util.NoSuchElementException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CustomerService {

    @Autowired
    private CustomerRepository customerRepository;

    public Customer queryCustomerById(Long id) {

        try {

            return customerRepository.findById(id).get();

        } catch (NoSuchElementException e) {

            throw new NoSuchElementException("Customer not exist!");

        }

    }

}
