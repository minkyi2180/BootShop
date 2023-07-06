package com.mysite.prac.cart;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.mysite.prac.item.ItemService;
import com.mysite.prac.user.UserRepository;

import lombok.RequiredArgsConstructor;

@RequestMapping("/cart")
@RequiredArgsConstructor
@Controller
public class CartController {
	private final CartRepository cartRepository;
	private final CartService cartService;
	private final UserRepository userRepository;
	private final ItemService itemService;
	
	
	
	

}
