package com.hifun.soul.gameserver.predict.manager;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.hifun.soul.common.LogReasons.PropertyLogReason;
import com.hifun.soul.core.util.KeyValuePair;
import com.hifun.soul.gamedb.entity.HumanEntity;
import com.hifun.soul.gameserver.common.GameServerAssist;
import com.hifun.soul.gameserver.function.GameFuncType;
import com.hifun.soul.gameserver.function.template.GameFuncTemplate;
import com.hifun.soul.gameserver.human.AbstractNotifyManager;
import com.hifun.soul.gameserver.human.Human;
import com.hifun.soul.gameserver.human.IHumanPropertiesLoadForBattle;
import com.hifun.soul.gameserver.human.ILoginManager;
import com.hifun.soul.gameserver.predict.msg.info.PredictInfo;
import com.hifun.soul.gameserver.predict.template.PredictAttribute;
import com.hifun.soul.gameserver.predict.template.PredictTemplate;
import com.hifun.soul.gameserver.role.properties.HumanIntProperty;
import com.hifun.soul.gameserver.role.properties.PropertyType;
import com.hifun.soul.gameserver.role.properties.amend.AmendMethod;
import com.hifun.soul.gameserver.role.properties.manager.HumanPropertyManager;

/**
 * 角色预见管理器
 * 
 * @author yandajun
 * 
 */
public class HumanPredictManager extends AbstractNotifyManager implements
		ILoginManager, IHumanPropertiesLoadForBattle {
	private Human human;
	private PredictTemplateManager templateManager;
	private Map<Integer, List<PredictInfo>> preditInfos = new HashMap<Integer, List<PredictInfo>>();

	public HumanPredictManager(Human human) {
		super(human);
		this.human = human;
		this.human.registerLoginManager(this);
		templateManager = GameServerAssist.getPredictTemplateManager();
		this.human.addNotifyManager(this);
	}

	@Override
	public void onLogin() {
		// 初始化预见信息
		initPreditInfo();
		// 预见属性加成
		amendPredictProperties(human.getPropertyManager());
	}

	/**
	 * 初始化预见信息
	 */
	private void initPreditInfo() {
		for (Integer pageIndex : templateManager.getPageIndexs()) {
			List<PredictTemplate> templateList = templateManager
					.getPagePredictTemplateList(pageIndex);
			List<PredictInfo> predictInfoList = new ArrayList<PredictInfo>();
			for (PredictTemplate predictTemplate : templateList) {
				PredictInfo predictInfo = new PredictInfo();
				predictInfo.setPredictId(predictTemplate.getId());
				predictInfo
						.setActivateNeedLevel(predictTemplate.getNeedLevel());
				predictInfo.setActivated(isPredictActivated(predictTemplate
						.getId()));
				List<GameFuncTemplate> funcTempalteList = GameServerAssist
						.getGameFuncService().getGameFuncTemplates(
								predictTemplate.getNeedLevel());
				List<GameFuncTemplate> showFuncTempalteList = new ArrayList<GameFuncTemplate>();
				for (GameFuncTemplate funcTempalte : funcTempalteList) {
					if (funcTempalte.isShowInPredict()) {
						showFuncTempalteList.add(funcTempalte);
					}
				}
				int[] functions = new int[showFuncTempalteList.size()];
				for (int i = 0; i < functions.length; i++) {
					functions[i] = showFuncTempalteList.get(i).getIcon();
				}
				predictInfo.setFunctions(functions);
				predictInfo.setX(predictTemplate.getX());
				predictInfo.setY(predictTemplate.getY());
				predictInfoList.add(predictInfo);
			}
			Collections.sort(predictInfoList);
			preditInfos.put(pageIndex, predictInfoList);
		}

	}

	/**
	 * 预见是否激活
	 */
	public boolean isPredictActivated(int predictId) {
		if (predictId <= human.getCurrentPredictId()) {
			return true;
		}
		return false;
	}

	/**
	 * 获取该页的预见信息
	 */
	public List<PredictInfo> getPredictInfoList(int pageIndex) {
		return preditInfos.get(pageIndex);
	}

	/**
	 * 获取某个预见信息
	 */
	private PredictInfo getPredictInfo(int predictId) {
		for (Integer pageIndex : preditInfos.keySet()) {
			List<PredictInfo> infoList = preditInfos.get(pageIndex);
			for (PredictInfo info : infoList) {
				if (info.getPredictId() == predictId) {
					return info;
				}
			}
		}
		return null;
	}

	/**
	 * 获取已激活的预见数量
	 */
	public int getActivatedPredictNum() {
		// 由于预见ID是连续的，当前激活的预见ID就是已激活的数量
		return human.getCurrentPredictId();
	}

	/**
	 * 获取剩余可激活的预见数量
	 */
	public int getRemainCanActivatePredictNum() {
		return templateManager.canActivatePredictNum(human.getLevel())
				- getActivatedPredictNum();
	}

	/**
	 * 激活预见
	 */
	public void activatePredict(int predictId) {
		PredictInfo predictInfo = getPredictInfo(predictId);
		if (predictInfo == null) {
			return;
		}
		predictInfo.setActivated(true);
		human.setCurrentPredictId(predictId);
		// 属性加成
		PredictTemplate previousTemplate = templateManager
				.getPredictTemplate(predictId - 1);
		if (previousTemplate == null) {
			amendPredictProperties(human.getPropertyManager());
		} else {
			// 减去上个级别加成的属性值
			PredictTemplate currentTemplate = templateManager
					.getPredictTemplate(predictId);
			if (currentTemplate == null) {
				return;
			}
			for (PredictAttribute currentAttribute : currentTemplate
					.getAttributes()) {
				for (PredictAttribute previousAttribute : previousTemplate
						.getAttributes()) {
					if (previousAttribute.getPropertyId() == currentAttribute
							.getPropertyId()) {
						human.getPropertyManager().amendProperty(
								human,
								AmendMethod.ADD,
								currentAttribute.getPropertyId(),
								currentAttribute.getPropertyValue()
										- previousAttribute.getPropertyValue(),
								PropertyLogReason.PREDICT, "");
					}
				}

			}
		}
		// 发送功能提示
		sendNotify();
	}

	/**
	 * 获取预见加成的属性列表
	 */
	public KeyValuePair<Integer, Integer>[] getPredictProperties(int predictId) {
		PredictTemplate template = templateManager
				.getPredictTemplate(predictId);
		KeyValuePair<Integer, Integer>[] properties = KeyValuePair
				.newKeyValuePairArray(0);
		if (template != null) {
			List<PredictAttribute> attributes = template.getAttributes();
			properties = KeyValuePair.newKeyValuePairArray(attributes.size());
			for (int i = 0; i < properties.length; i++) {
				properties[i] = new KeyValuePair<Integer, Integer>(attributes
						.get(i).getPropertyId(), attributes.get(i)
						.getPropertyValue());
			}
		}

		return properties;
	}

	/**
	 * 预见属性加成
	 */
	public void amendPredictProperties(HumanPropertyManager propertyManager) {
		int predictId = propertyManager.getIntPropertySet(
				PropertyType.HUMAN_INT_PROPERTY).getPropertyValue(
				HumanIntProperty.CURRENT_PREDICT_ID);
		PredictTemplate template = templateManager
				.getPredictTemplate(predictId);
		if (template == null) {
			return;
		}
		List<PredictAttribute> attributes = template.getAttributes();
		for (PredictAttribute attibute : attributes) {
			propertyManager.amendProperty(human, AmendMethod.ADD,
					attibute.getPropertyId(), attibute.getPropertyValue(),
					PropertyLogReason.PREDICT, "");
		}
	}

	/**
	 * 战斗属性加成
	 */
	@Override
	public void onBattlePropertiesLoad(HumanEntity humanEntity,
			HumanPropertyManager propertyManager) {
		amendPredictProperties(propertyManager);
	}

	@Override
	public GameFuncType getFuncType() {
		return GameFuncType.PREDICT;
	}

	@Override
	public boolean isNotify() {
		// 剩余可激活数量大于0
		if (getRemainCanActivatePredictNum() > 0) {
			return true;
		}
		return false;
	}

}
