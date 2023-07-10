package com.mysite.prac.orderItem;

import java.time.LocalDateTime;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.mysite.prac.DataNotFoundException;
import com.mysite.prac.item.Item;
import com.mysite.prac.order.Order;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class OrderItemService {
	private final OrderItemRepository orderItemRepository;
	
	
	//주문
	public OrderItem create(Order order, Item item, int count,int price, String postcode, String address, String address2, String sp_name ) {
		OrderItem it = new OrderItem();
		
		it.setStatus(1); //입금 전 1, 입금 후2 
		it.setOrder(order);
		it.setItem(item);
		it.setCount(count);
		it.setPrice(price);
		
		it.setPostcode(postcode);
		it.setAddress(address);
		it.setAddress2(address2);
		
		it.setSp_name(sp_name);
		it.setOrderDate(LocalDateTime.now());
		
		return it;
	}
	
	public OrderItem getOrderItemById(Integer id) {
		Optional<OrderItem> orderItem = this.orderItemRepository.findById(id);
		if(orderItem.isPresent()) {
			return orderItem.get();
		}else {
			throw new DataNotFoundException("장바구니 상품을 찾을 수 없습니다");
		}
	}
	
	
	//삭제
	public void delete(OrderItem orderItem) {
		this.orderItemRepository.delete(orderItem);
	}
	
	
}
