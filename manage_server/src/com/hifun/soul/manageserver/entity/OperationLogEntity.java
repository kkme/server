package com.hifun.soul.manageserver.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

import com.hifun.soul.core.orm.BaseProtobufEntity;
import com.hifun.soul.proto.services.LogServices.OperationLog;
import com.hifun.soul.proto.services.LogServices.OperationLog.Builder;

@Entity
@Table(name="t_operation_log")
public class OperationLogEntity extends BaseProtobufEntity<OperationLog.Builder>{

	public OperationLogEntity(Builder builder) {
		super(builder);
	}
	
	public OperationLogEntity(){
		this(OperationLog.newBuilder());
	}

	@Id
	@Column
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@GenericGenerator(name = "persistenceGenerator", strategy = "increment")
	@Override
	public Long getId() {
		return this.builder.getId();
	}

	@Override
	public void setId(Serializable id) {
		this.builder.setId((Long)id);
	}
	
	@Column
	public int getUserId(){
		return this.builder.getUserId();
	}
	
	public void setUserId(int userId){
		this.builder.setUserId(userId);
	}
	
	@Column
	public String getUserName(){
		return this.builder.getUserName();
	}
	
	public void setUserName(String userName){
		this.builder.setUserName(userName);
	}
	
	@Column
	public int getOperationType(){
		return this.builder.getOperationType();
	}
	
	public void setOperationType(int operationType){
		this.builder.setOperationType(operationType);
	}
	
	@Column
	public String getOperationText(){
		return this.builder.getOperationText();
	}
	
	public void setOperationText(String operationText){
		this.builder.setOperationText(operationText);
	}
	
	@Column
	public boolean getHasPermission(){
		return this.builder.getHasPermission();
	}
	
	public void setHasPermission(boolean hasPermission){
		this.builder.setHasPermission(hasPermission);
	}
	
	@Column
	public String getParam(){
		return this.builder.getParam();
	}
	
	@Column
	public void setParam(String param){
		this.builder.setParam(param);
	}
	
	@Column
	public boolean getResult(){
		return this.builder.getResult();
	}
	
	@Column
	public void setResult(boolean result){
		this.builder.setResult(result);
	}
	
	@Column
	public long getOperateTime(){
		return this.builder.getOperateTime();
	}
	
	@Column
	public void setOperateTime(long operateTime){
		this.builder.setOperateTime(operateTime);
	}
}
