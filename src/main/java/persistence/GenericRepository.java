package persistence;

import java.util.List;
import java.util.Optional;

/**
 * Generic repository interface for CRUD operations
 * This is a generic contract for repositories.
 * Repositories are contracts between the domain layer and the persistence layer.
 * All the CRUD operations (Create, read, update, and delete) can be implemented with the help of a repository interface.
 */
public interface GenericRepository<T> {
    
    /**
     * Save an entity
     */
    T save(T entity);
    
    /**
     * Find all entities
     */
    List<T> findAll();
    
    /**
     * Find entity by ID
     */
    Optional<T> findById(String id);
    
    /**
     * Update an entity
     */
    void update(T entity);
    
    /**
     * Delete an entity
     */
    void delete(T entity);
} 