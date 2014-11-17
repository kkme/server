package com.hifun.soul.gameserver.rechargetx.manager;

import java.util.List;

import org.slf4j.Logger;

import com.hifun.soul.common.LogReasons.RechargeLogReason;
import com.hifun.soul.common.constants.Loggers;
import com.hifun.soul.gamedb.agent.query.DataQueryConstants;
import com.hifun.soul.gamedb.callback.IDBCallback;
import com.hifun.soul.gamedb.entity.QQRechargeEntity;
import com.hifun.soul.gameserver.common.GameServerAssist;
import com.hifun.soul.gameserver.human.Human;
import com.hifun.soul.gameserver.human.ILoginManager;
import com.hifun.soul.gameserver.rechargetx.template.RechargeTXTemplate;
import com.hifun.soul.gameserver.vip.RechargeType;

public class HumanRechargeTxManager implements ILoginManager{
	private Human human;
	private Logger logger = Loggers.RECHARGE_LOGGER;

	public HumanRechargeTxManager(Human human) {
		this.human = human;

		this.human.registerLoginManager(this);
	}

	@Override
	public void onLogin() {
		GameServerAssist.getDataService().query(DataQueryConstants.QUERY_UNRECHARGED_BY_HUMANID, new String[] { "humanId" },
				new Object[] { human.getHumanGuid() }, new IDBCallback<List<?>>() {
					@SuppressWarnings("unchecked")
					@Override
					public void onSucceed(List<?> result) {
						if (!result.isEmpty()) {
							List<QQRechargeEntity> rechargeEntitys = (List<QQRechargeEntity>) result;
							for (QQRechargeEntity rechargeEntity : rechargeEntitys) {
								// 更新数据库记录
								rechargeEntity.setSended(true);
								GameServerAssist.getDataService().update(rechargeEntity);
								// 给玩家加钱
								if(!giveMoneyByRecharge(rechargeEntity.getPayItem())){
									logger.error(
											String.format("onLogin: give money by recharge error! humanGUID:%s,rechargeEntityId:%s",
													human.getHumanGuid(),rechargeEntity.getId()));
								}
							}
						}
					}

					@Override
					public void onFailed(String errorMsg) {
						logger.error(errorMsg);
					}
				});
	}
	
	public boolean giveMoneyByRecharge(String payItem) {
		String[] buyItemInfo = payItem.split("\\*");
		if(buyItemInfo.length < 3){
			logger.error(String.format("QQRechargeConfirmFailed! payItem=%s", payItem));
			return false;
		}
		int id = Integer.valueOf(buyItemInfo[0]);
		int num = Integer.valueOf(buyItemInfo[2]);
		RechargeTXTemplate template = GameServerAssist.getRechargeTXTemplateManager().getRechargeTXTemplate(id);
		if(template == null){
			logger.error(String.format("QQRechargeConfirmFailed! RechargeTXTemplate is null", payItem));
			return false;
		}
		if(num <= 0){
			logger.error(String.format("QQRechargeConfirmFailed! RechargeItem num less than zero", payItem));
			return false;
		}
		human.getHumanVipManager().recharge(template.getCrystal()*num, 1, RechargeType.NORMAL_RECHARGE);
		GameServerAssist.getLogService().sendRechargeLog(human, RechargeLogReason.QQ_RECHARGE, 
				payItem, template.getCrystal()*num, template.getCrystal()*num, 0, human.getCrystal()-template.getCrystal()*num, human.getCrystal(), 1);
		return true;
	}
}
