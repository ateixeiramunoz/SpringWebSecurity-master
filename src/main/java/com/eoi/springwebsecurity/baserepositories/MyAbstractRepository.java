package com.eoi.springwebsecurity.baserepositories;

import org.springframework.data.jpa.domain.AbstractPersistable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.NoRepositoryBean;
import org.springframework.data.repository.PagingAndSortingRepository;

/**
 * The interface My abstract repository.
 *
 * @param <T>  the type parameter
 * @param <ID> the type parameter
 */
@NoRepositoryBean
public interface MyAbstractRepository<T , ID> extends JpaRepository<T,ID> ,PagingAndSortingRepository<T, ID> {

}