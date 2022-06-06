package com.blog.system.service;

import java.util.List;

public interface CrudService<Model> extends PaginationResult {

	public Model save(Model model);

	public List<Model> list();
	
	public Model getById(long id);

	public Model update(Model model, long id);

	public void delete(long id);

}
