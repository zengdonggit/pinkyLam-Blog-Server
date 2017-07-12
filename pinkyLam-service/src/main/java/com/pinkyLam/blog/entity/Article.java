package com.pinkyLam.blog.entity;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.pinkyLam.blog.utils.CustomDateSerializer;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * @author Pinky Lam 908716835@qq.com
 * @date 2017年7月12日 下午5:10:03
 */

@Entity
@Table(name = "ARTICLE")
public class Article implements Serializable {

	/**
	 * @Fields serialVersionUID : TODO(用一句话描述这个变量表示什么)
	 */
	private static final long serialVersionUID = 5918485060819084203L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "ID")
	private Long id;
	@Column(name = "TITLE")
	private String title;
	@Column(name = "SUBTITLE")
	private String subtitle;
	@Column(name = "CONTENT")
	private String content;
	@Column(name = "STATUS")
	private Integer status;
	@Column(name = "HITS")
	private Integer hits;
	@Column(name = "CREATE_TIME")
	@JsonSerialize(using = CustomDateSerializer.class)
	private Date createTime;
	@Column(name = "UPDATE_TIME")
	@JsonSerialize(using = CustomDateSerializer.class)
	private Date updateTime;

	public String getContent() {
		return content;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public Integer getHits() {
		return hits;
	}

	public Long getId() {
		return id;
	}

	public Integer getStatus() {
		return status;
	}

	public String getSubtitle() {
		return subtitle;
	}

	public String getTitle() {
		return title;
	}

	public Date getUpdateTime() {
		return updateTime;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public void setHits(Integer hits) {
		this.hits = hits;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public void setSubtitle(String subtitle) {
		this.subtitle = subtitle;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

	@Override
	public String toString() {
		return "Article [id=" + id + ", title=" + title + ", subtitle=" + subtitle + ", content=" + content
				+ ", status=" + status + ", hits=" + hits + ", createTime=" + createTime + ", updateTime=" + updateTime
				+ "]";
	}

}
