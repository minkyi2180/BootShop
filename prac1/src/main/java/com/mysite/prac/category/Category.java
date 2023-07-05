package com.mysite.prac.category;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import com.mysite.prac.item.Item;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Entity
public class Category {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	//1. 유니폼 2.모자
	
	
	private String category_name;
	
	@OneToMany(mappedBy = "category")
	private List<Item> item_list;
}
