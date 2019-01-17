package com.deloitte.fiddler.model;

import java.util.ArrayList;
import java.util.List;

public class FiddlerProcess {
	
	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}


	public List<FiddlerTask> getTasks() {
		return tasks;
	}

	public void setTasks(List<FiddlerTask> tasks) {
		this.tasks = tasks;
	}


	private String category;
	
	private List<FiddlerTask> tasks = new ArrayList<>();

}
