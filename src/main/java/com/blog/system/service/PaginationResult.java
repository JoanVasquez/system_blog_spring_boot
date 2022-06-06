package com.blog.system.service;

import com.blog.system.dto.PaginatedResult;

public interface PaginationResult {
	public PaginatedResult paginatedResult(int numberOfPages, int qtyPerPage, String orderBy, String sortBy);
}
