package com.ecommerce.shop.services.interfaces;

import java.util.List;

public interface ICrudService<T, ID> {

    T save(T t);

    T findById(ID id);

    T update(T t, ID id);

    void deleteById(ID id);

    List<T> findAll();
}
