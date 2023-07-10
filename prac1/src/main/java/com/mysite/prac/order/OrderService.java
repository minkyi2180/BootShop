package com.mysite.prac.order;

import org.springframework.stereotype.Service;

import com.mysite.prac.user.SiteUser;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class OrderService {
	private final OrderRepository orderRepository;
	
	public Order create(SiteUser user) {
		Order order = new Order();
		
		order.setUser(user);
		
		return this.orderRepository.save(order);
		
	}
	
	public Order getOrderByUser(SiteUser user) {
		return this.orderRepository.findAllByUser(user);
	}

}
