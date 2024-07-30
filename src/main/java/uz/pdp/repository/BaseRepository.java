package uz.pdp.repository;

import uz.pdp.model.User;

import java.util.List;
import java.util.Optional;

public interface BaseRepository<E, T> {
    Boolean save(E e);
    Boolean update(T id, E e);
    List<E> findAll();
    Optional<E> findById(T id);
}
