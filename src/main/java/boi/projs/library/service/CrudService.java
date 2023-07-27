package boi.projs.library.service;

import boi.projs.library.domain.BaseEntity;
import boi.projs.library.logging.LoggableCrud;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@LoggableCrud
public abstract class CrudService<T extends BaseEntity> {

    protected JpaRepository<T, UUID> repository;

    public CrudService(JpaRepository<T, UUID> repository) {
        this.repository = repository;
    }

    public void save(T entity) {
        repository.save(entity);
    }

    public void deleteById(UUID id) {
        repository.deleteById(id);
    }

    public Optional<T> findById(UUID id) {
        return repository.findById(id);
    }

    public List<T> findAll() {
        return repository.findAll();
    }

    public void saveAll(List<T> entities) {
        repository.saveAll(entities);
    }
}
