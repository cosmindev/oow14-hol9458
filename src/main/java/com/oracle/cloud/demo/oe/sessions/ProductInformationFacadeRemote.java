package com.oracle.cloud.demo.oe.sessions;

import java.util.List;

import javax.ejb.Remote;
import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import com.oracle.cloud.demo.oe.entities.ProductInformation;

@Remote
public interface ProductInformationFacadeRemote {
	public EntityManager getEntityManager() ;
 
    public void create(ProductInformation entity) ;

    public void edit(ProductInformation entity) ;

    public void remove(ProductInformation entity) ;

    public ProductInformation find(Object id) ;

    public List<ProductInformation> findAll() ;

    public List<ProductInformation> findRange(int[] range) ;

    public int count();

    public CriteriaQuery filterQuery(CriteriaQuery query, Root<ProductInformation> rt);

    public CriteriaQuery orderByQuery(CriteriaQuery query, Root<ProductInformation> rt);

}
