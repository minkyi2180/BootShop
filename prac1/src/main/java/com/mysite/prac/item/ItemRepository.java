package com.mysite.prac.item;

import java.util.List;

import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;

import com.mysite.prac.category.Category;

public interface ItemRepository extends JpaRepository<Item, Integer>{
	Item findAllById(Integer id);
	List<Item> findAllByCategory(Category category, Sort sort);
	//sort 는 페이지를 카테고리로 나누기전에 목록을 정렬하는데 이때 목록을 정렬할 기준값이 되어준다
	List<Item> findAll(Sort sort);
}
