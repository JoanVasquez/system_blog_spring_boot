package com.blog.system.dto;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PaginatedResult {

	private List<PostDTO> content;
	private int pageNumber;
	private int qtyPerPage;
	private long totalItems;
	private int totalPages;
	private boolean lastPage;

}
