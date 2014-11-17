package com.hifun.soul.gameserver.recharge;

import java.util.Date;

import org.slf4j.Logger;

import com.hifun.soul.common.constants.Loggers;
import com.hifun.soul.gamedb.callback.IDBCallback;
import com.hifun.soul.gamedb.entity.RechargeEntity;
import com.hifun.soul.gameserver.common.GameServerAssist;
import com.hifun.soul.gameserver.human.Human;
import com.hifun.soul.gameserver.role.properties.HumanIntProperty;
import com.hifun.soul.gameserver.role.properties.PropertyType;
import com.hifun.soul.gameserver.vip.RechargeType;

/**
 * 充值接口类
 * 
 * @author magicstone
 * 
 */
public class RechargeService {
	private static Logger logger = Loggers.RECHARGE_LOGGER;

	/**
	 * 充值
	 * 
	 * @param role
	 *            充值对象
	 * @param firstCurrencyNum
	 *            一级货币数量
	 * @param rate
	 *            一级货币与二级货币的兑换比例
	 */
	public static void recharge(Human role, int firstCurrencyNum, int rewardNum, int totalNum, RechargeType rechargeType) {
		if (role == null || firstCurrencyNum <= 0 || totalNum <= 0) {
			throw new IllegalArgumentException("充值参数错误。");
		}
		final Human human = role;
		logger.info("开始插入充值记录：humanId:?,humanName:?,firstCurrencyNum:?,rewardNum:?,totalNum:?",
				new Object[] { human.getHumanGuid(), human.getName(), firstCurrencyNum, rewardNum, totalNum });
		int normalRechargeTimes = human.getPropertyManager().getIntPropertySet(PropertyType.HUMAN_INT_PROPERTY)
				.getPropertyValue(HumanIntProperty.NORMAL_RECHARGE_TIMES);
		boolean isFirstRecharge = normalRechargeTimes <= 0;
		if (rechargeType == RechargeType.NORMAL_RECHARGE) {
			normalRechargeTimes++;
			human.getPropertyManager().getIntPropertySet(PropertyType.HUMAN_INT_PROPERTY)
					.setPropertyValue(HumanIntProperty.NORMAL_RECHARGE_TIMES, normalRechargeTimes);
		}
		final RechargeEntity entity = new RechargeEntity();
		entity.setHumanId(human.getHumanGuid());
		entity.setPassportId(human.getPlayer().getPassportId());
		entity.setRechargeCost(firstCurrencyNum);
		entity.setRechargeNum(totalNum);
		entity.setRechargeTime(new Date());
		entity.setRewardNum(rewardNum);
		entity.setFirstRecharge(isFirstRecharge);
		entity.setRechargeType(rechargeType.getIndex());
		GameServerAssist.getDataService().insert(entity, new IDBCallback<Integer>() {
			@Override
			public void onSucceed(Integer result) {
				logger.info("插入充值记录成功！[humanid:" + human.getHumanGuid() + "],[rechargeCost:" + entity.getRechargeCost()
						+ "],[rechargeNum:" + entity.getRechargeNum() + "],[rewardNum:" + entity.getRewardNum() + "]");
			}

			@Override
			public void onFailed(String errorMsg) {
				logger.error("插入充值记录失败！[humanid:" + human.getHumanGuid() + "],[rechargeCost:"
						+ entity.getRechargeCost() + "],[rechargeNum:" + entity.getRechargeNum() + "],[rewardNum:"
						+ entity.getRewardNum() + "],errorMsg:" + errorMsg);
			}
		});
	}
}
