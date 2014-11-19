package com.oracle.cloud.demo.oe.sessions;

import java.util.Collections;
import java.util.List;

import javax.ejb.Remote;
import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import com.oracle.cloud.demo.oe.entities.Customer;
import com.oracle.cloud.demo.oe.entities.Order;
import com.oracle.cloud.demo.oe.entities.OrderItem;
import com.oracle.cloud.demo.oe.entities.ProductInformation;

@Remote
public interface OrdersFacadeRemote {


    public EntityManager getEntityManager() ;

    public void setFilterByCustomerEmail(String email) ;
    public CriteriaQuery filterQuery(CriteriaQuery query, Root<Order> rt) ;
    public CriteriaQuery orderByQuery(CriteriaQuery query, Root<Order> rt);

    public List<Order> getOrdersByCustomerEmail(String customerEmail) ;
    public List<OrderItem> getItemsByCustomerEmailAndOrderId(String customerEmail, Integer orderId);
    public void create(Order entity) ;

    public void edit(Order entity) ;

    public void remove(Order entity) ;

    public Order find(Object id) ;

    public List<Order> findAll() ;

    public List<Order> findRange(int[] range) ;

    public int count();

}
