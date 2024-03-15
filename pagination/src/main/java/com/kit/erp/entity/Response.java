package com.kit.erp.entity;

import java.util.List;

public class Response {
	private List<Film> contents;
	private int pageNumber;
	private int totalContentOfThePage;
	private boolean isLastPage;
	public List<Film> getDetails() {
		return contents;
	}
	public void setDetails(List<Film> contents) {
		this.contents = contents;
	}
	public int getPageNumber() {
		return pageNumber;
	}
	public void setPageNumber(int pageNumber) {
		this.pageNumber = pageNumber;
	}
	public int getTotalContentOfThePage() {
		return totalContentOfThePage;
	}
	public void setTotalContentOfThePage(int totalContentOfThePage) {
		this.totalContentOfThePage = totalContentOfThePage;
	}
	public boolean isLastPage() {
		return isLastPage;
	}
	public void setLastPage(boolean isLastPage) {
		this.isLastPage = isLastPage;
	}
	
	
	
}
