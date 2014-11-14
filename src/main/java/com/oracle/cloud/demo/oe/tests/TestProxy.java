package com.oracle.cloud.demo.oe.tests;

import java.util.List;

import javax.ejb.EJB;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;

import com.oracle.cloud.demo.oe.entities.Customer;
import com.oracle.cloud.demo.oe.entities.Order;
import com.oracle.cloud.demo.oe.sessions.CustomersFacade;
import com.oracle.cloud.demo.oe.sessions.CustomersFacadeRemote;
import com.oracle.cloud.demo.oe.sessions.OrdersFacade;
import com.oracle.cloud.demo.oe.sessions.OrdersFacadeRemote;

/**
 * Session Bean implementation class TestProxy
 */
@Stateless(mappedName = "ProxyForTests")
public class TestProxy implements TestProxyRemote, TestProxyLocal {

	/**
	 * Default constructor.
	 */
	@EJB
	CustomersFacadeRemote<Customer> customers;
	@EJB
	OrdersFacadeRemote orders;

	public TestProxy() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public Customer testGetCustomerByEmail(String email) {
		return (Customer) customers.getCustomerByEmail(email);
	}

	@Override
	public List<Order> testGetOrdersByCustomerEmail(String customerEmail) {
		// TODO Auto-generated method stub
		return orders.getOrdersByCustomerEmail(customerEmail);
	}

}
