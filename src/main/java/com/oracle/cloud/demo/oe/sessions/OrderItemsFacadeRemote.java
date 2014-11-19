package com.oracle.cloud.demo.oe.sessions;

import javax.ejb.Remote;
import javax.persistence.EntityManager;

import com.oracle.cloud.demo.oe.entities.OrderItem;

@Remote
public interface OrderItemsFacadeRemote<OrderItem> extends AbstractFacadeRemote<OrderItem> {

	public EntityManager getEntityManager() ;

}
