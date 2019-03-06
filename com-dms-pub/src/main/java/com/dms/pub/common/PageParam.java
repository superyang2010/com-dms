package com.dms.pub.common;

import lombok.Data;

/**
 * 分页参数
 * @author yang.chao.
 * @date 2019/2/20
 */
@Data
public class PageParam {
	/** 当前页码， 从1开始 */
	protected int pageIndex;
	/** 每页显示记录数 */
	protected int pageSize = 20;
	 /** 分页查询开始记录位置 */
	protected int start;
	 /** 分页查询下结束位置 */
	protected int end;
	 /** 查询结果总记录数 */
	protected int total;
	 /** 总共页数 */
	protected int pages;

	public PageParam() {

	}

	public PageParam(int pageIndex, int pageSize) {
		this.pageIndex = pageIndex;
		this.pageSize = pageSize;
		this.start = (this.pageIndex - 1) * this.pageSize;
		this.end = start + this.pageSize;
	}

	public PageParam(int pageIndex, int pageSize, int total) {
		this(pageIndex, pageSize);
		this.total = total;
		this.pages = this.total / this.pageSize;
		if (this.total % this.pageSize != 0) {
			this.pages++;
		}
	}

	public PageParam(int pageIndex, int pageSize, int total, int start,
                     int end, int pages) {
		this.pageIndex = pageIndex;
		this.pageSize = pageSize;
		this.total = total;
		this.pages = pages;
		this.start = start;
		this.end = end;
	}

	/**
	 * @return the begin
	 */
	public int getStart() {
		return start;
	}

	/**
	 * @return the end
	 */
	public int getEnd() {
		return end;
	}

	/**
	 * @return the pageSize
	 */
	public int getPageSize() {
		return pageSize;
	}

	/**
	 * @return the total
	 */
	public int getTotal() {
		return total;
	}

	/**
	 * @return the pageIndex
	 */
	public int getPageIndex() {
		return pageIndex;
	}

	/**
	 * @return the pages
	 */
	public int getPages() {
		return pages;
	}

	public void setTotal(int total) {
		this.total = total;
		this.pages = this.total / this.pageSize;
		if (this.total % this.pageSize != 0) {
			this.pages++;
		}
	}
}
