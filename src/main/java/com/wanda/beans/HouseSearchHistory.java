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
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * 房型查询历史记录表
 * @author jinkejk
 *
 */
@Entity
@Table(name="houseSearchHistory")
public class HouseSearchHistory {
	@Id @Column
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Integer historyId;
	//查询的房型
	@ManyToOne(targetEntity=House.class)
	@JoinColumn(name="houseId")
	private House house;
	//查询人
	@ManyToOne(targetEntity=User.class)
	@JoinColumn(name="authorId")
	private User user;
	//查询日期
	@Temporal(TemporalType.TIMESTAMP)
	private Date searchDate;
	public Integer getHistoryId() {
		return historyId;
	}
	public void setHistoryId(Integer historyId) {
		this.historyId = historyId;
	}
	public House getHouse() {
		return house;
	}
	public void setHouse(House house) {
		this.house = house;
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
	
	@Override
	public String toString() {
		return "HouseSearchHistory [historyId=" + historyId + ", house=" + house + ", user=" + user + ", searchDate="
				+ searchDate + "]";
	}
	
	
}
