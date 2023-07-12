package com.mysite.prac.order;

import org.springframework.stereotype.Service;

import com.mysite.prac.user.SiteUser;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class OrderService {
	private final OrderRepository orderRepository;
	
	
	public Order create(SiteUser user, String postcode, String address, String address2, String shipping_name) {
		Order order = new Order();
		order.setUser(user);
		order.setPostcode(postcode);
		order.setAddress(address);
		order.setAddress2(address2);
		order.setShipping_name(shipping_name);
		
		
		
		return this.orderRepository.save(order);
	}
	
	//user로 order 조회
	public Order getOrderByUser(SiteUser user) {	
		return this.orderRepository.findByUser(user);
	}
}
	
