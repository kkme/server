package com.hifun.soul.gamedb.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import com.hifun.soul.core.orm.BaseCommonEntity;

@Entity
@Table(name="t_qq_recharge")
public class QQRechargeEntity extends BaseCommonEntity{
	@Id
	@Column
	private String id;
	/** 充值角色id */
	@Column
	private long humanId;
	/** 跟billno唯一确定一笔交易 */
	@Column
	private String openId;
	/** 交易时间戳 */
	@Column
	private String ts;
	/** 购买的物品 */
	@Column
	private String payItem;
	/** 交易token */
	@Column
	private String token;
	/** 与openId唯一确定一笔交易 */
	@Column
	private String billno;
	/** 扣款金额(0.1Q点为单位) */
	@Column
	private String amt;
	/** 扣取的游戏币总数，单位为Q点  */
	@Column
	private String payamtCoins;
	/** 扣取的抵用券总金额，单位为Q点  */
	@Column
	private String pubacctPayamtCoins;
	/** 如果充值的时候玩家不在线先记录下来,玩家登录的时候在发  */
	@Column
	private boolean sended;
	/** 整个json串也存储起来 */
	@Column
	private String jsonValue;
	
	@Override
	public String getId() {		
		return id;
	}

	@Override
	public void setId(Serializable id) {
		this.id = (String)id;
	}

	public long getHumanId() {
		return humanId;
	}

	public void setHumanId(long humanId) {
		this.humanId = humanId;
	}

	public String getOpenId() {
		return openId;
	}

	public void setOpenId(String openId) {
		this.openId = openId;
	}

	public String getTs() {
		return ts;
	}

	public void setTs(String ts) {
		this.ts = ts;
	}

	public String getPayItem() {
		return payItem;
	}

	public void setPayItem(String payItem) {
		this.payItem = payItem;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public String getBillno() {
		return billno;
	}

	public void setBillno(String billno) {
		this.billno = billno;
	}

	public String getAmt() {
		return amt;
	}

	public void setAmt(String amt) {
		this.amt = amt;
	}

	public String getPayamtCoins() {
		return payamtCoins;
	}

	public void setPayamtCoins(String payamtCoins) {
		this.payamtCoins = payamtCoins;
	}

	public String getPubacctPayamtCoins() {
		return pubacctPayamtCoins;
	}

	public void setPubacctPayamtCoins(String pubacctPayamtCoins) {
		this.pubacctPayamtCoins = pubacctPayamtCoins;
	}

	public boolean isSended() {
		return sended;
	}

	public void setSended(boolean sended) {
		this.sended = sended;
	}

	public String getJsonValue() {
		return jsonValue;
	}

	public void setJsonValue(String jsonValue) {
		this.jsonValue = jsonValue;
	}
	
}
