package com.hifun.soul.gameserver.costnotify.manager;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.hifun.soul.core.orm.IEntity;
import com.hifun.soul.gamedb.cache.CacheEntry;
import com.hifun.soul.gamedb.cache.ICachableComponent;
import com.hifun.soul.gamedb.entity.HumanCostNotifyEntity;
import com.hifun.soul.gamedb.entity.HumanEntity;
import com.hifun.soul.gameserver.common.GameServerAssist;
import com.hifun.soul.gameserver.costnotify.CostNotifyInfo;
import com.hifun.soul.gameserver.costnotify.CostNotifyType;
import com.hifun.soul.gameserver.costnotify.converter.CostNotifyInfoToEntityConverter;
import com.hifun.soul.gameserver.costnotify.msg.GCUpdateCostNotify;
import com.hifun.soul.gameserver.costnotify.template.CostNotifyTemplate;
import com.hifun.soul.gameserver.human.Human;
import com.hifun.soul.gameserver.human.IHumanPersistenceManager;
import com.hifun.soul.gameserver.human.ILoginManager;
import com.hifun.soul.proto.common.CostNotifyDatas.CostNotifyData;
import com.hifun.soul.proto.data.entity.Entity.HumanCostNotify;

public class HumanCostNotifyManager  implements ICachableComponent,IHumanPersistenceManager,ILoginManager {

	private Human _human;
	private Map<Integer,CostNotifyInfo> _costNotifyInfoMap = new HashMap<Integer,CostNotifyInfo>();
	private CostNotifyInfoToEntityConverter _converter;
	private CacheEntry<CostNotifyType,CostNotifyInfo> _costNotifyInfoCaches = new CacheEntry<CostNotifyType,CostNotifyInfo>(); 
	
	public HumanCostNotifyManager(Human human) {
		this._human = human;
		_converter = new CostNotifyInfoToEntityConverter(human);
		
		this._human.registerPersistenceManager(this);
		this._human.registerCachableManager(this);
		this._human.registerLoginManager(this);
	}
	
	@Override
	public Human getOwner() {
		return _human;
	}
	
	/**
	 * 更新某个消费通知的设置
	 * 
	 * @param costNotifyType
	 * @param costNotifyInfo
	 */
	public void updateCostNotifyInfo(Integer costNotifyType, CostNotifyInfo costNotifyInfo) {
		_costNotifyInfoMap.put(costNotifyType, costNotifyInfo);
		_costNotifyInfoCaches.addUpdate(CostNotifyType.indexOf(costNotifyType), costNotifyInfo);
	}

	@Override
	public void onLoad(HumanEntity humanEntity) {
		// 先把数据库中的读取进来
		for(HumanCostNotify.Builder builder : humanEntity.getBuilder().getCostNotifyBuilderList()){
			CostNotifyData costNotifyData = builder.getCostNotifyData();
			int costNotifyType = costNotifyData.getCostNotifyType();
			CostNotifyInfo costNotifyInfo = new CostNotifyInfo();
			if(costNotifyInfo != null){
				costNotifyInfo.setCostNotifyType(costNotifyType);
				costNotifyInfo.setOpen(costNotifyData.getOpen());
				if (costNotifyType > CostNotifyType.values().length){
					HumanCostNotifyEntity entity = this._converter.convert(costNotifyInfo);
					GameServerAssist.getDataService().delete(entity);
					continue;
				} 
				_costNotifyInfoMap.put(costNotifyType, costNotifyInfo);
			}
		}
		
		for(CostNotifyType costNotifyType : CostNotifyType.values()){
			CostNotifyTemplate costNotifyTemplate = GameServerAssist.getTemplateService().get(costNotifyType.getIndex(), CostNotifyTemplate.class);
			if(costNotifyTemplate != null){
				CostNotifyInfo costNotifyInfo = _costNotifyInfoMap.get(costNotifyType.getIndex());
				if(costNotifyInfo == null){
					costNotifyInfo = new CostNotifyInfo();
					costNotifyInfo.setCostNotifyType(costNotifyType.getIndex());
					costNotifyInfo.setOpen(true);
					costNotifyInfo.setName(costNotifyTemplate.getName());
					costNotifyInfo.setDesc(costNotifyTemplate.getDesc());
					_costNotifyInfoMap.put(costNotifyInfo.getCostNotifyType(), costNotifyInfo);
					updateCostNotifyInfo(costNotifyInfo.getCostNotifyType(), costNotifyInfo);
				}
				else{
					costNotifyInfo.setName(costNotifyTemplate.getName());
					costNotifyInfo.setDesc(costNotifyTemplate.getDesc());
				}

			}
		}
		
	}

	@Override
	public void onPersistence(HumanEntity humanEntity) {
		for(CostNotifyInfo costNotifyInfo : _costNotifyInfoMap.values()){
			humanEntity.getBuilder().addCostNotify(
					this._converter.convert(costNotifyInfo).getBuilder());
		}
		
	}

	@Override
	public List<IEntity> getUpdateEntities() {
		List<IEntity> updateList = new ArrayList<IEntity>();
		
		for(CostNotifyInfo costNotifyInfo : _costNotifyInfoCaches.getAllUpdateData()){
			HumanCostNotifyEntity entity = this._converter.convert(costNotifyInfo);
			updateList.add(entity);
		}
		
		return updateList;
	}

	@Override
	public List<IEntity> getDeleteEntities() {
		List<IEntity> deleteList = new ArrayList<IEntity>();
		
		for(CostNotifyInfo costNotifyInfo : _costNotifyInfoCaches.getAllDeleteData()){
			HumanCostNotifyEntity entity = this._converter.convert(costNotifyInfo);
			deleteList.add(entity);
		}
		
		return deleteList;
	}

	@Override
	public void onLogin() {
		// 登陆时下发消费提醒的信息
		sendCostNotifyInfo();
		
	}
	
	private void sendCostNotifyInfo() {
		GCUpdateCostNotify gcMsg = new GCUpdateCostNotify();
		gcMsg.setCostNotifyInfos(_costNotifyInfoMap.values().toArray(new CostNotifyInfo[0]));
		_human.sendMessage(gcMsg);
	}

}
