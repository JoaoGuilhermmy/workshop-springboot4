package com.tecsoluction.course.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import com.tecsoluction.course.entities.Order;
import com.tecsoluction.course.entities.User;
import com.tecsoluction.course.repositories.OrderRepository;
import com.tecsoluction.course.service.exception.DatabaseException;
import com.tecsoluction.course.service.exception.ResourceNotFoundException;

import jakarta.persistence.EntityNotFoundException;

@Service
public class OrderService {
    @Autowired
    private OrderRepository repository;

    public List<Order> findAll() {
        return repository.findAll();
    }

    public Order findById(Long id) {
        Optional<Order> obj = repository.findById(id);
        return obj.get();
    }

    public Order insert(Order obj) {
        return repository.save(obj);
    }

    public void delete(Long id) {

        if (!repository.existsById(id)) {
            throw new ResourceNotFoundException(id);
        }
        try {
            repository.deleteById(id);
        } catch (DataIntegrityViolationException e) {
            throw new DatabaseException(e.getMessage());
        }
    }

    public Order update(Long id, Order order) {
        try {
            Order entity = repository.getReferenceById(id);
            updateData(entity, order);
            return repository.save(entity);
        } catch (EntityNotFoundException e) {
            throw new ResourceNotFoundException(id);
        }
    }

    private void updateData(Order entity, Order order) {
        entity.setMoment(order.getMoment());
        entity.setOrderStatus(order.getOrderStatus());
        entity.setClient(order.getClient());
        entity.setPayment(order.getPayment());
    }
}
