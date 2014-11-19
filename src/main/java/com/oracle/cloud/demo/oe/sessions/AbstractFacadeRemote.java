package com.oracle.cloud.demo.oe.sessions;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

public interface AbstractFacadeRemote<T> {
	
 
    abstract EntityManager getEntityManager();

    public void create(T entity) ;

    public void edit(T entity) ;

    public void remove(T entity) ;

    public T find(Object id) ;

    public List<T> findAll() ;

    public List<T> findRange(int[] range) ;

    public int count();

    CriteriaQuery filterQuery(CriteriaQuery query, Root<T> rt) ;

    CriteriaQuery orderByQuery(CriteriaQuery query, Root<T> rt) ;

}
