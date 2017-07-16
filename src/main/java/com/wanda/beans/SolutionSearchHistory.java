package com.wanda.beans;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 * 解决方案实体类
 * @author jinkejk
 *
 */
@Entity
@Table(name="solution_search_history")
public class SolutionSearchHistory {
	
	@Id @Column
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Integer solutionSearchHistoryId;
	//查询关键字
	private String keyword;
    //搜索人，可以为空
	@ManyToOne(targetEntity=User.class)
	@JoinColumn(name="userId")
    private User user;
    //查询日期
    @Column(nullable=false)
    private Date searchDate;
    //查询结果数
    @Column(columnDefinition="INT default 0")
    private int resultNum;
    
	public Integer getSolutionSearchHistoryId() {
		return solutionSearchHistoryId;
	}
	public void setSolutionSearchHistoryId(Integer solutionSearchHistoryId) {
		this.solutionSearchHistoryId = solutionSearchHistoryId;
	}
	public String getKeyword() {
		return keyword;
	}
	public void setKeyword(String keyword) {
		this.keyword = keyword;
	}
	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}
	public Date getSearchDate() {
		return searchDate;
	}
	public void setSearchDate(Date searchDate) {
		this.searchDate = searchDate;
	}
	public int getResultNum() {
		return resultNum;
	}
	public void setResultNum(int resultNum) {
		this.resultNum = resultNum;
	}
	@Override
	public String toString() {
		return "SolutionSearchHistory [solutionSearchHistoryId=" + solutionSearchHistoryId + ", keyword=" + keyword
				+ ", user=" + user + ", searchDate=" + searchDate + ", resultNum=" + resultNum + "]";
	}
    
    
}
