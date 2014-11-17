package com.hifun.soul.gamedb.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

import com.hifun.soul.core.orm.BaseCommonEntity;

@Entity
@Table(name = "t_prison_log")
public class PrisonLogEntity extends BaseCommonEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@GenericGenerator(name = "persistenceGenerator", strategy = "increment")
	@Column
	private long id;
	@Column
	private int logType;
	@Column
	private long firstId;
	@Column
	private long secondId;
	@Column
	private long thirdId;
	@Column
	private String content;
	@Column
	private int result;
	@Column
	private long operateTime;
	@Column
	private int interactType;

	public Serializable getId() {
		return id;
	}

	@Override
	public void setId(Serializable id) {
		this.id = (Long) id;
	}

	public int getLogType() {
		return logType;
	}

	public void setLogType(int logType) {
		this.logType = logType;
	}

	public long getFirstId() {
		return firstId;
	}

	public void setFirstId(long firstId) {
		this.firstId = firstId;
	}

	public long getSecondId() {
		return secondId;
	}

	public void setSecondId(long secondId) {
		this.secondId = secondId;
	}

	public long getThirdId() {
		return thirdId;
	}

	public void setThirdId(long thirdId) {
		this.thirdId = thirdId;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public int getResult() {
		return result;
	}

	public void setResult(int result) {
		this.result = result;
	}

	public long getOperateTime() {
		return operateTime;
	}

	public void setOperateTime(long operateTime) {
		this.operateTime = operateTime;
	}

	public int getInteractType() {
		return interactType;
	}

	public void setInteractType(int extractType) {
		this.interactType = extractType;
	}

}
