package com.oracle.cloud.demo.oe.sessions;

import javax.ejb.Remote;
import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import com.oracle.cloud.demo.oe.entities.Customer;
@Remote
public interface CustomersFacadeRemote<Customer> extends
		AbstractFacadeRemote<Customer> {

	public EntityManager getEntityManager();

	public void setFilterByEmail(String email);

	public CriteriaQuery filterQuery(CriteriaQuery query, Root<Customer> rt);

	public Customer getCustomerByEmail(String email);

}
