package com.interview.api.model;

import javax.validation.constraints.NotNull;

public class NewPet {

	@NotNull
	private String name;
	private String tag;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getTag() {
		return tag;
	}

	public void setTag(String tag) {
		this.tag = tag;
	}

}
