package com.oracle.cloud.demo.oe.sessions;

import java.io.Serializable;
import java.util.List;

import com.bea.core.repackaged.aspectj.apache.bcel.util.Class2HTML;
import com.oracle.cloud.demo.oe.entities.ProductInformation;

import javax.ejb.Stateless;
import javax.faces.bean.SessionScoped;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

@Stateless(name="ProductInformationFacade")
public class ProductInformationFacade implements ProductInformationFacadeRemote, Serializable{
	
    private static final long serialVersionUID = 1L;
	public final Class<ProductInformation> entityClass=ProductInformation.class;

    @PersistenceContext
    public EntityManager em;

	public EntityManager getEntityManager() {
        return em;
    }

    public ProductInformationFacade() {
    	super();
        
    }
    
    public void create(ProductInformation entity) {
        getEntityManager().persist(entity);
    }

    public void edit(ProductInformation entity) {
        getEntityManager().merge(entity);
    }

    public void remove(ProductInformation entity) {
        getEntityManager().remove(getEntityManager().merge(entity));
    }

    public ProductInformation find(Object id) {
        return getEntityManager().find(entityClass, id);
    }

    public List<ProductInformation> findAll() {
        CriteriaQuery cq = getEntityManager().getCriteriaBuilder().createQuery();
        Root<ProductInformation> rt = cq.from(entityClass);
        cq.select(rt);
        return getEntityManager().createQuery(orderByQuery(filterQuery(cq, rt), rt)).getResultList();
    }

    public List<ProductInformation> findRange(int[] range) {
        CriteriaQuery cq = getEntityManager().getCriteriaBuilder().createQuery();
        Root<ProductInformation> rt = cq.from(entityClass);
        cq.select(rt);
        javax.persistence.Query q = getEntityManager().createQuery(orderByQuery(filterQuery(cq, rt), rt));
        q.setMaxResults(range[1] - range[0] + 1);
        q.setFirstResult(range[0]);
        return q.getResultList();
    }

    public int count() {
        CriteriaQuery cq = getEntityManager().getCriteriaBuilder().createQuery();
        Root<ProductInformation> rt = cq.from(entityClass);
        cq.select(getEntityManager().getCriteriaBuilder().count(rt));
        javax.persistence.Query q = getEntityManager().createQuery(filterQuery(cq, rt));
        return ((Long) q.getSingleResult()).intValue();
    }

    public CriteriaQuery filterQuery(CriteriaQuery query, Root<ProductInformation> rt) {
        return query;
    }

    public CriteriaQuery orderByQuery(CriteriaQuery query, Root<ProductInformation> rt) {
        return query;
    }


}
