package com.synnex.cms.utils;

import java.util.ArrayList;
import java.util.Collection;

public class PageList<E> extends ArrayList<E> {
	
	public PageList(){
		
	}
	
	public PageList(Collection<? extends E> list){
		super.addAll(list);
	}
	
	private Page page;
	
	public static class Page{
		int first;
		int max;
		int row;
		int page;
		int rowsOfPage;
		
	}

	public Page getPage() {
		return page;
	}

	public void setPage(Page page) {
		this.page = page;
	}
	
	
}
