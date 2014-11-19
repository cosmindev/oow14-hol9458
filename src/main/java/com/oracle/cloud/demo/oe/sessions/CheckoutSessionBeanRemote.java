package com.oracle.cloud.demo.oe.sessions;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.List;

import javax.ejb.Remote;

import com.oracle.cloud.demo.oe.entities.Customer;
import com.oracle.cloud.demo.oe.entities.Order;
import com.oracle.cloud.demo.oe.entities.OrderItem;
import com.oracle.cloud.demo.oe.web.util.BasketItem;
@Remote
public interface CheckoutSessionBeanRemote {
	public Order checkout(String user, List<BasketItem> basketItems);

    public BigDecimal calculateTotal(List<BasketItem> basketItems) ;

}
