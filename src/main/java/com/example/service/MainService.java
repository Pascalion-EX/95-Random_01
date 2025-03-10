package com.example.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import com.example.repository.MainRepository;

@Primary
@Service
public abstract class MainService<T> {

    @Autowired
    protected MainRepository<T> repository;

    public ArrayList<T> getAll() {
        return new ArrayList<>(repository.findAll());
    }

    public Optional<T> getById(UUID id) {
        return repository.findAll().stream()
                .filter(item -> getId(item).equals(id))
                .findFirst();
    }

    public T add(T item) {
        repository.save(item);
        return item;
    }

    public void delete(UUID id) {
        ArrayList<T> items = new ArrayList<>(repository.findAll());
        if (items.removeIf(item -> getId(item).equals(id))) {
            repository.saveAll(items);
        }
    }

    protected abstract UUID getId(T item);
}
