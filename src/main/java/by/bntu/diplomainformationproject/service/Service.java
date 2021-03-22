package by.bntu.diplomainformationproject.service;

public interface Service<T, K> {
    T findById(K id);

    T create(T object);

    T update(T object);

    boolean deleteById(K id);
}
