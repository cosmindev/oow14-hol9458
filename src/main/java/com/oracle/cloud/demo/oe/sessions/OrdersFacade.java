package com.oracle.cloud.demo.oe.sessions;

import java.io.Serializable;
import java.util.Collections;
import java.util.List;

import javax.ejb.Stateless;
import javax.faces.bean.SessionScoped;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import com.oracle.cloud.demo.oe.entities.Customer;
import com.oracle.cloud.demo.oe.entities.Order;
import com.oracle.cloud.demo.oe.entities.OrderItem;
import com.oracle.cloud.demo.oe.entities.ProductInformation;

@Stateless(name="OrdersFacade")
public class OrdersFacade implements Serializable, OrdersFacadeRemote {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@PersistenceContext
	private EntityManager em;
	private String filterByCustomerEmail;
	public final Class<Order> entityClass = Order.class;

	@Override
	public EntityManager getEntityManager() {
		return em;
	}

	public OrdersFacade() {
		super();
	}

	public void setFilterByCustomerEmail(String email) {
		this.filterByCustomerEmail = email;
	}

	@Override
	public CriteriaQuery filterQuery(CriteriaQuery query, Root<Order> rt) {
		if (filterByCustomerEmail == null) {
			return query;
		}
		if (filterByCustomerEmail != null
				&& filterByCustomerEmail.trim().isEmpty() == false) {
			CriteriaBuilder cb = getEntityManager().getCriteriaBuilder();
			query.where(cb.like(
					rt.<Customer> get("customer").<String> get("custEmail"),
					"%" + filterByCustomerEmail + "%"));
		}
		return query;
	}

	@Override
	public CriteriaQuery orderByQuery(CriteriaQuery query, Root<Order> rt) {
		CriteriaBuilder cb = getEntityManager().getCriteriaBuilder();
		return query.orderBy(cb.desc(rt.get("orderDate")));
	}

	public List<Order> getOrdersByCustomerEmail(String customerEmail) {
		return em.createNamedQuery("Order.findByCustomerEmail")
				.setParameter("customerEmail", customerEmail).getResultList();
	}

	public List<OrderItem> getItemsByCustomerEmailAndOrderId(
			String customerEmail, Integer orderId) {
		List<OrderItem> orderItems = (List<OrderItem>) em
				.createNamedQuery("OrderItem.findByOrderIdAndCustomerEmail")
				.setParameter("customerEmail", customerEmail)
				.setParameter("orderId", orderId).getResultList();
		if (orderItems == null) {
			return Collections.emptyList();
		}
		return orderItems;
	}

	public void create(Order entity) {
		getEntityManager().persist(entity);
	}

	public void edit(Order entity) {
		getEntityManager().merge(entity);
	}

	public void remove(Order entity) {
		getEntityManager().remove(getEntityManager().merge(entity));
	}

	public Order find(Object id) {
		return getEntityManager().find(entityClass, id);
	}

	public List<Order> findAll() {
		CriteriaQuery cq = getEntityManager().getCriteriaBuilder()
				.createQuery();
		Root<Order> rt = cq.from(entityClass);
		cq.select(rt);
		return getEntityManager().createQuery(
				orderByQuery(filterQuery(cq, rt), rt)).getResultList();
	}

	public List<Order> findRange(int[] range) {
		CriteriaQuery cq = getEntityManager().getCriteriaBuilder()
				.createQuery();
		Root<Order> rt = cq.from(entityClass);
		cq.select(rt);
		javax.persistence.Query q = getEntityManager().createQuery(
				orderByQuery(filterQuery(cq, rt), rt));
		q.setMaxResults(range[1] - range[0] + 1);
		q.setFirstResult(range[0]);
		return q.getResultList();
	}

	public int count() {
		CriteriaQuery cq = getEntityManager().getCriteriaBuilder()
				.createQuery();
		Root<Order> rt = cq.from(entityClass);
		cq.select(getEntityManager().getCriteriaBuilder().count(rt));
		javax.persistence.Query q = getEntityManager().createQuery(
				filterQuery(cq, rt));
		return ((Long) q.getSingleResult()).intValue();
	}

}
