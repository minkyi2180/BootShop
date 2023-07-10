package com.mysite.prac.order;

import java.security.Principal;
import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;
import javax.validation.Valid;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import com.mysite.prac.cartItem.CartItem;
import com.mysite.prac.cartItem.CartItemRepository;
import com.mysite.prac.item.Item;
import com.mysite.prac.item.ItemRepository;
import com.mysite.prac.item.ItemService;
import com.mysite.prac.orderItem.OrderItem;
import com.mysite.prac.orderItem.OrderItemService;
import com.mysite.prac.user.SiteUser;
import com.mysite.prac.user.UserRepository;
import com.mysite.prac.user.UserService;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class OrderController {
	private final ItemService itemService;
	private final UserService userService;
	private final UserRepository userRepository;
	private final OrderRepository orderRepository;
	private final OrderService orderService;
	private final OrderItemService orderItemService;
	private final ItemRepository itemRepository;
	private final CartItemRepository cartItemRepository;
	
	// 주문 폼
	@PreAuthorize("isAuthenticated()")
	@GetMapping("/order/{id}")
	public String order(
			Model model, Principal principal, @PathVariable(value = "id") int id) {
		Item item = this.itemService.getItem(id);
	    model.addAttribute("item", item);
	    String username = principal.getName();
	    model.addAttribute("username", username);
	
		return "orderform";
	}
		
	// 장바구니 상품 전체 주문 처리
	@PreAuthorize("isAuthenticated()")
	@PostMapping("/order/{id}")
	@Transactional
	public String orderCart(
		Principal principal,
		@PathVariable(value = "id") Long id,
		Model model
	) {
		Optional<SiteUser> user = userService.getById(id);

		if (user.isPresent()) {
			List<CartItem> cartItems = cartItemRepository.findByCartUser(user.get());
			if (!cartItems.isEmpty()) {
				Order order = orderService.create(user.get());
				for (CartItem cartItem : cartItems) {
					Item item = cartItem.getItem();
					int count = cartItem.getCount();
					OrderItem orderItem = orderItemService.create(order, item, count,
							item.getPrice(), "", "", "", "");
					int sellCnt = item.getStock();
					sellCnt -= count;
					item.setStock(sellCnt);

					itemRepository.save(item);
				}

				return "orderSuccess";
			}
		}
		return "redirect:/cart";
	}
	
	// 주문 처리
	@PreAuthorize("isAuthenticated()")
	@PostMapping("/order/buy/{id}")
	public String buy(
		@Valid OrdersForm ordersForm,
		BindingResult bindingResult,
		Principal principal,
		@PathVariable(value = "id") int id
	) {
		if (bindingResult.hasErrors()) {
			System.out.println("error");
			return "orderForm";
		}
		
		Item item = this.itemService.getItem(id);
		Optional<SiteUser> user = this.userService.getByUserName(principal.getName());
		
		Order order = this.orderRepository.findAllByUser(user.get());
		if (order == null) {
			order = this.orderService.create(user.get());
		}
		
		OrderItem orderItem = this.orderItemService.create(
			order,
			item,
			ordersForm.getCount(),
			ordersForm.getPrice(),
			ordersForm.getAddress(),
			ordersForm.getAddress2(),
			ordersForm.getPostcode(),
			ordersForm.getSp_name()
		);
		
		// 주문 완료 후 상품 재고 -1
		int sellCnt = item.getStock();
		sellCnt -= orderItem.getCount();
		item.setStock(sellCnt);
		int orderCount = item.getSell_count();
		orderCount += 1;
		item.setSell_count(orderCount);
		this.itemRepository.save(item);
		
		return "redirect:orderSuccess";
	}
		
	// 주문 성공 시
	@GetMapping("/order_success")
	public String order_success() {
		return "orderSuccess";
	}
}
