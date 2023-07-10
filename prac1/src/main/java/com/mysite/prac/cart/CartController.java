package com.mysite.prac.cart;

import java.security.Principal;
import java.util.List;
import java.util.Optional;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.mysite.prac.cartItem.CartItem;
import com.mysite.prac.cartItem.CartItemRepository;
import com.mysite.prac.cartItem.CartItemService;
import com.mysite.prac.item.Item;
import com.mysite.prac.item.ItemService;
import com.mysite.prac.user.SiteUser;
import com.mysite.prac.user.UserRepository;
import com.mysite.prac.user.UserService;

import lombok.RequiredArgsConstructor;

@RequestMapping("/cart")
@RequiredArgsConstructor
@Controller
public class CartController {
	private final CartRepository cartRepository;
	private final CartService cartService;
	private final UserRepository userRepository;
	private final UserService userService;
	private final ItemService itemService;
	private final CartItemRepository cartItemRepository;
	private final CartItemService cartItemService;
	
	
	@GetMapping("/list/{username}")
	public String cartlist(Model model, Principal principal) {
	    // Get the currently logged-in user
	    String username = principal.getName();
	
	    model.addAttribute("username", username);
	    SiteUser user = userRepository.findAllByusername(username);
	    
	    List<CartItem> cartlist = cartItemRepository.findByCartUser(user);
	    model.addAttribute("cartlist", cartlist);
	    
	    int totalPrice = (int) cartlist.stream()
	            .mapToDouble(cartItem -> cartItem.getItem().getPrice() * cartItem.getCount())
	            .sum();
	    model.addAttribute("totalPrice", totalPrice);
	    
	    return "cart";
	}
	
	
	@PreAuthorize("isAuthenticated()")
	@PostMapping("/add/{id}")
	public String addCart(
			@PathVariable(value = "id")int id,
			@RequestParam(value = "count",defaultValue = "1")int count,
			Principal principal,
			Model model
			) {
		//로그인한 사용자명으로 유저조회
		SiteUser user = this.userRepository.findAllByusername(principal.getName());
		Item item = this.itemService.getItem(id);		
		Cart cart = this.cartRepository.findAllByUser(user);
		
		
		//사용자에게 카트가 없다면 사용자에게 카트생성
		if(cart == null) {
			cart = this.cartService.create(user);
		}
		
		int cart_id = cart.getId();
		CartItem cartItem = this.cartItemRepository.findByCartIdAndItemId(cart_id, id);
		//해당상품이 카트에 없다면 카트에 상품 추가
		if(cartItem == null) {
			cartItem = this.cartItemService.create(cart, item, count);						
		}else if(cartItem.getItem().equals(item)) {
			this.cartItemService.addCount(cartItem,count);
			return String.format("redirect:/item/detail/%s", id);
		}
		
		
		return String.format("redirect:/item/detail/%s", id);
	}
	
	
	//장바구니 삭제
	@PostMapping("/delete/{id}")
	public String deleteCartItem(
	        @PathVariable(value = "id") int id,
	        Principal principal
	) {
	    // 로그인한 사용자명으로 유저 조회
	    SiteUser user = userRepository.findAllByusername(principal.getName());
	    Cart cart = cartRepository.findAllByUser(user);
	    
	    if (cart != null) {
	        CartItem cartItem = cartItemRepository.findByCartIdAndItemId(cart.getId(), id);
	        
	        if (cartItem != null) {
	            cartItemRepository.delete(cartItem);
	        }
	    }
	    
	    return "redirect:/cart/list/{id}";
	}
	
	//수량변경
	@PostMapping("/updateQuantity/{id}")
	public String updateCartItemQuantity(
	        @PathVariable(value = "id") int id,
	        @RequestParam("quantity") int quantity,
	        Principal principal
	) {
	    // 로그인한 사용자명으로 유저 조회
	    SiteUser user = userRepository.findAllByusername(principal.getName());
	    Cart cart = cartRepository.findAllByUser(user);
	    
	    if (cart != null) {
	        CartItem cartItem = cartItemRepository.findByCartIdAndItemId(cart.getId(), id);
	        
	        if (cartItem != null) {
	            cartItemService.updateQuantity(cartItem, quantity);
	        }
	    }
	    
	    
	  
	    return "redirect:/cart/list/{id}";
	}

	   
	    @GetMapping("/order")
	    public String order(
	    	Model model, Principal principal, @PathVariable(value="id") int id){
				Item item = this.itemService.getItem(id);
			    model.addAttribute("item", item);
			    Optional<SiteUser> user = this.userService.getByUserName(principal.getName());
			    String username = principal.getName();
			    model.addAttribute("username", username);
		
				return "orderform";
	    }

	

}
