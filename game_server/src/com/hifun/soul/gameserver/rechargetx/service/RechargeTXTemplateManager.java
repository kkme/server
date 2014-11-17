package com.hifun.soul.gameserver.rechargetx.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.hifun.soul.common.IInitializeRequired;
import com.hifun.soul.core.template.TemplateService;
import com.hifun.soul.gameserver.rechargetx.RechargeInfo;
import com.hifun.soul.gameserver.rechargetx.VipInfo;
import com.hifun.soul.gameserver.rechargetx.template.RechargeTXTemplate;
import com.hifun.soul.gameserver.vip.template.VipPrivilegeTemplate;

/**
 * 腾讯充值模版管理
 */
@Scope("singleton")
@Component
public class RechargeTXTemplateManager implements IInitializeRequired {
	@Autowired
	private TemplateService templateService;
	private RechargeInfo[] rechargeInfos;
	private VipInfo[] vipInfos;
	private Map<Integer,RechargeTXTemplate> rechargeTXTemplateMap = new HashMap<Integer,RechargeTXTemplate>();
	
	@Override
	public void init() {
		initRechargeInfos();
		initVipInfos();
	}
	
	/**
	 * 初始化充值档位信息
	 */
	private void initRechargeInfos() {
		List<RechargeInfo> rechargeInfoList = new ArrayList<RechargeInfo>();
		for(RechargeTXTemplate template : templateService
				.getAll(RechargeTXTemplate.class).values()){
			RechargeInfo rechargeInfo = new RechargeInfo();
			rechargeInfo.setId(template.getId());
			rechargeInfo.setName(template.getName());
			rechargeInfo.setDesc(template.getDesc());
			rechargeInfo.setIcon(template.getIcon());
			rechargeInfo.setCrystal(template.getCrystal());
			rechargeInfo.setPrice(template.getPrice());
			rechargeInfoList.add(rechargeInfo);
			rechargeTXTemplateMap.put(template.getId(), template);
		}
		rechargeInfos = rechargeInfoList.toArray(new RechargeInfo[0]);
	}
	
	/**
	 * 初始化vip信息
	 */
	private void initVipInfos() {
		List<VipInfo> vipInfoList = new ArrayList<VipInfo>();
		for(VipPrivilegeTemplate template : templateService
				.getAll(VipPrivilegeTemplate.class).values()){
			VipInfo vipInfo = new VipInfo();
			vipInfo.setVipId(template.getId());
			vipInfo.setVipLevel(template.getLevel());
			vipInfo.setVipNeedreChargeNum(template.getRechargeNum());
			vipInfoList.add(vipInfo);
		}
		vipInfos = vipInfoList.toArray(new VipInfo[0]);
	}
	
	/**
	 * 充值档位信息
	 * @return
	 */
	public RechargeInfo[] getRechargeInfos() {
		return rechargeInfos;
	}
	
	/**
	 * 各个vip对应的充值信息
	 * @return
	 */
	public VipInfo[] getVipInfos() {
		return vipInfos;
	}
	
	/**
	 * 获取腾讯充值模版
	 * @param id
	 * @return
	 */
	public RechargeTXTemplate getRechargeTXTemplate(int id) {
		return rechargeTXTemplateMap.get(id);
	}
}
