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

import org.hibernate.annotations.Type;

/**
 * 户型的bean
 * @author jinkejk
 *
 */
@Entity
@Table(name="house")
public class House {
	@Id @Column
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Integer houseId;
	//楼盘名称
	private String LPName;
	//户型名称
	private String HXName;
	//面积
	private float area;
	//得房率
	private float rate;
	//总层数
	private int layer;
	//外窗型材
	private String outWinType;
	//外墙保温材料    
	private String warmMaterial;
	//分户楼板保温材料
	private String insulation;
	//分户楼板隔声材料
	private String soundProof;
	//遮阳形式
	private String shadeType;
	//是否有太阳能热水器
	private String hasSolar;
	//关注度
	private int attention;
	//图片名字，对应相应的图片
	private String imgName;	
	//创建日期
	@Temporal(TemporalType.TIMESTAMP)
	private Date createDate;
	//上传人
	@ManyToOne(targetEntity=User.class)
	@JoinColumn(name="authorId")
	private User author;
	//修改日期
	@Temporal(TemporalType.TIMESTAMP)
	private Date alterDate;
	//最近修改者
	@ManyToOne(targetEntity=User.class)
	@JoinColumn(name="lastAlterId")
	private User lastAlter;
	public Integer getHouseId() {
		return houseId;
	}
	public void setHouseId(Integer houseId) {
		this.houseId = houseId;
	}
	public String getLPName() {
		return LPName;
	}
	public void setLPName(String lPName) {
		LPName = lPName;
	}
	public String getHXName() {
		return HXName;
	}
	public void setHXName(String hXName) {
		HXName = hXName;
	}
	public float getArea() {
		return area;
	}
	public void setArea(float area) {
		this.area = area;
	}
	public float getRate() {
		return rate;
	}
	public void setRate(float rate) {
		this.rate = rate;
	}
	public int getLayer() {
		return layer;
	}
	public void setLayer(int layer) {
		this.layer = layer;
	}
	public String getOutWinType() {
		return outWinType;
	}
	public void setOutWinType(String outWinType) {
		this.outWinType = outWinType;
	}
	public String getWarmMaterial() {
		return warmMaterial;
	}
	public void setWarmMaterial(String warmMaterial) {
		this.warmMaterial = warmMaterial;
	}
	public String getInsulation() {
		return insulation;
	}
	public void setInsulation(String insulation) {
		this.insulation = insulation;
	}
	public String getSoundProof() {
		return soundProof;
	}
	public void setSoundProof(String soundProof) {
		this.soundProof = soundProof;
	}
	public String getShadeType() {
		return shadeType;
	}
	public void setShadeType(String shadeType) {
		this.shadeType = shadeType;
	}
	public String getHasSolar() {
		return hasSolar;
	}
	public void setHasSolar(String hasSolar) {
		this.hasSolar = hasSolar;
	}
	public int getAttention() {
		return attention;
	}
	public void setAttention(int attention) {
		this.attention = attention;
	}
	public String getImgName() {
		return imgName;
	}
	public void setImgName(String imgName) {
		this.imgName = imgName;
	}
	public Date getCreateDate() {
		return createDate;
	}
	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}
	public User getAuthor() {
		return author;
	}
	public void setAuthor(User author) {
		this.author = author;
	}
	public Date getAlterDate() {
		return alterDate;
	}
	public void setAlterDate(Date alterDate) {
		this.alterDate = alterDate;
	}
	public User getLastAlter() {
		return lastAlter;
	}
	public void setLastAlter(User lastAlter) {
		this.lastAlter = lastAlter;
	}
	
	@Override
	public String toString() {
		return "House [houseId=" + houseId + ", LPName=" + LPName + ", HXName=" + HXName + ", area=" + area + ", rate="
				+ rate + ", layer=" + layer + ", outWinType=" + outWinType + ", warmMaterial=" + warmMaterial
				+ ", insulation=" + insulation + ", soundProof=" + soundProof + ", shadeType=" + shadeType
				+ ", hasSolar=" + hasSolar + ", attention=" + attention + ", createDate=" + createDate + ", author="
				+ author + ", alterDate=" + alterDate + ", lastAlter=" + lastAlter + "]";
	}
	
	
}
