package com.mysite.prac.item;

import java.security.Principal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import com.mysite.prac.category.Category;
import com.mysite.prac.user.SiteUser;
import com.mysite.prac.user.UserService;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
@RequestMapping("/item")
public class ItemController {
	private final ItemService itemService;
	private final ItemRepository itemRepository;
	private final UserService userService;
	
	
	//상품등록
	@PreAuthorize("hasRole('ADMIN')")
	@GetMapping("/create")
	public String create() {
		return "itemForm";
	}
	
	@PreAuthorize("hasRole('ADMIN')")
	@PostMapping("/create")
	public String create(Item item, MultipartFile file) throws Exception{
		item.setCreateDate(LocalDateTime.now());
		item.setHit(0);
		item.setSell_count(0);
		
		itemService.saveItem(item, file);
		return "redirect:/";
	}
	
	//상품리스트
	@GetMapping("itemList")
	public String itemList(Model model) {
		List<Item> item_list= this.itemRepository.findAll(Sort.by(Sort.Direction.DESC,"createDate"));
		model.addAttribute("item_list", item_list);
		
		return "itemList";
	}
	
	@GetMapping("adminList")
	public String adminList(Model model) {
		List<Item> item_list= this.itemRepository.findAll(Sort.by(Sort.Direction.DESC,"createDate"));
		model.addAttribute("item_list", item_list);
		
		return "admin_List";
	}
	
	@PostMapping("/search")
	public String searchItem(Model model, @RequestParam(value = "category_id") Integer category_id,//카테고리 id
			@RequestParam(value = "field", defaultValue = "createDate") String field //정렬할 필드이름 (createDate, order_cnt, price)
			) {

		List<Item> search_itemList = this.itemService.getCategoryByItemList(category_id, field);
		model.addAttribute("search_itemList", search_itemList);
		
		return "item/search_item";
	}
	//[AJAX] 상품 정렬 (전체)
		@PostMapping("/search/all")
		public String searchItemAll(Model model,
				@RequestParam(value = "field", defaultValue = "createDate") String field //정렬할 필드이름 (createDate, order_cnt, price)
				) {
			//기본정렬값은 최신순으로
			List<Item> search_itemList = this.itemRepository.findAll(Sort.by(Sort.Direction.DESC,field));
			model.addAttribute("search_itemList", search_itemList);
			return "item/search_item";
		}
		
		//상품 삭제
		@PreAuthorize("hasRole('ADMIN')")
		@GetMapping("/delete/{id}")
		public String delete(@PathVariable Integer id,
				Principal principal
				) {
			Item item = this.itemService.getItem(id);
			Optional<SiteUser> user = this.userService.getByUserName(principal.getName());
			//운영자가 아닐떄
			if(!user.get().getUsername().equals("admin")) {
				throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "삭제권한이 없습니다.");
			}
			this.itemService.delete(item);
			
			return "redirect:/item/adminList";
		}
		
		//상품수정
		@PreAuthorize("hasRole('ADMIN')")
		@GetMapping("/modify/{id}")
		public String modify(@PathVariable Integer id, Model model) {
			Item item = this.itemService.getItem(id);
			model.addAttribute("item",item);
			return "itemModify";
		}
		
		@PreAuthorize("hasRole('ADMIN')")
		@PostMapping("/modify/{id}")
		public String modify(
				@PathVariable("id") Integer id,
				@RequestParam String name,
				@RequestParam String text,
				@RequestParam int price,
				@RequestParam int stock,
				@RequestParam int isSoldOut,
				
				MultipartFile file
				
				)throws Exception {
			Item item = this.itemService.getItem(id);
			this.itemService.modify(item, name, text, price, stock, isSoldOut);
			this.itemService.saveItem(item,file);
			
			return String.format("redirect:/item/adminList", id);
		}
		
		//상세페이지

}
