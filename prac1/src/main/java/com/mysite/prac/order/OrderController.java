package com.mysite.prac.order;

import java.security.Principal;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import com.mysite.prac.cart.CartService;
import com.mysite.prac.cartItem.CartItem;
import com.mysite.prac.cartItem.CartItemService;
import com.mysite.prac.item.Item;
import com.mysite.prac.item.ItemRepository;
import com.mysite.prac.item.ItemService;
import com.mysite.prac.orderItem.OrderItem;
import com.mysite.prac.orderItem.OrderItemService;
import com.mysite.prac.user.SiteUser;
import com.mysite.prac.user.UserService;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class OrderController {
	private final ItemService itemService;
	private final UserService userService;
	private final OrderRepository orderRepository;
	private final OrderService orderService;
	private final OrderItemService orderItemService;
	private final ItemRepository itemRepository;
	private final CartItemService cartItemService;
	
	//주문폼
	@GetMapping("/order/{id}")
	public String order(
			Model model,@PathVariable(value="id") int id,
			Principal principal,OrdersForm ordersForm
			) { 
	
		
		Item item = this.itemService.getItem(id);
		CartItem cartItem = this.cartItemService.getCartItemById(id);
		model.addAttribute("item", item);
		model.addAttribute("cartItem", cartItem);
		
		Optional<SiteUser> user = this.userService.getByUserName(principal.getName());
		model.addAttribute("user", user.get());
		
		
		return "orderForm";
	}
	
	@PostMapping("/order/{id}")
	public String proOrder(Model model,@PathVariable(value="id") int id,
			Principal principal,OrdersForm ordersForm
			) { 
	
		
		Item item = this.itemService.getItem(id);
		CartItem cartItem = this.cartItemService.getCartItemById(id);
		model.addAttribute("item", item);
		model.addAttribute("cartItem", cartItem);
		
		Optional<SiteUser> user = this.userService.getByUserName(principal.getName());
		model.addAttribute("user", user.get());
		
		
		return "orderForm";
	}
	
	
	//주문처리
	@PreAuthorize("isAuthenticated()")
	@PostMapping("/order/buy/{id}")
	public String buy(@Valid OrdersForm ordersForm, BindingResult bindingResult, Principal principal,
	        @PathVariable(value = "id") int id) {
	    if (bindingResult.hasErrors()) {
	    	System.out.println("error");
	    
	        return "orderForm";
	    }

	    Item item = this.itemService.getItem(id);
	    CartItem cartItem = this.cartItemService.getCartItemById(id);

	    if (item == null) {
	        // 상품이 존재하지 않는 경우에 대한 처리
	        // 오류 메시지 표시 또는 다른 처리를 수행하도록 수정
	        return "redirect:/error";
	    }

	    Optional<SiteUser> user = this.userService.getByUserName(principal.getName());
	    Order order = this.orderRepository.findByUser(user.get());

	   

	    OrderItem orderItem = this.orderItemService.create(order, item, ordersForm.getCount(), ordersForm.getPrice(),
	            ordersForm.getAddress(), ordersForm.getAddress2(), ordersForm.getPostcode(),
	            ordersForm.getShipping_name());

	    int sell_cnt = item.getStock();
	    sell_cnt -= orderItem.getCount();
	    item.setStock(sell_cnt);
	    int order_count = item.getSell_count();
	    order_count += 1;
	    item.setSell_count(order_count);
	    this.itemRepository.save(item);

	    return "redirect:orderSuccess";
	}

	
	//주문성공시
	@GetMapping("/order/order_success")
	public String order_success() {

		return "orderSuccess";
	}
}
