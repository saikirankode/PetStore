package com.interview.api.model;

import com.interview.api.model.NewPet;

public class Pet extends NewPet {

	private Long id = null;
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}
	
	public Pet(NewPet newPet) {
		this.setName(newPet.getName());
		this.setTag(newPet.getTag());
	}

	
	public Pet(String name, String tag, Long id) {
		this.setName(name);
		this.setTag(tag);
		this.id = id;
	}
	
	public Pet() {
		super();
	}
}
