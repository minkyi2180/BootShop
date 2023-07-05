package com.mysite.prac;

import java.security.Principal;
import java.util.List;

import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;

import com.mysite.prac.item.Item;
import com.mysite.prac.item.ItemRepository;


@Controller
public class MainController {

	private final ItemRepository itemRepository;
	
	public MainController(ItemRepository itemRepository) {
		this.itemRepository = itemRepository;
	}
	@GetMapping("/prac")
	@ResponseBody
	public String index() {
		return "메뚜기월드에 오신걸 환영합니다";
	}
	
	@GetMapping("/")
	public String main(Model model) {
		
		List<Item> item_list= this.itemRepository.findAll();
		model.addAttribute("item_list", item_list);
		 
		return "Main";
	}
}
