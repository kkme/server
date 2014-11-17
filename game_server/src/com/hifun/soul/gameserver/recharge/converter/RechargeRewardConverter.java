package com.hifun.soul.gameserver.recharge.converter;

import com.hifun.soul.gamedb.entity.HumanSingleRechargeRewardEntity;
import com.hifun.soul.gamedb.entity.HumanTotalRechargeRewardEntity;
import com.hifun.soul.gameserver.recharge.msg.TotalRechargeGradeInfo;

public class RechargeRewardConverter {
	public static HumanSingleRechargeRewardEntity toSingleRechargeReward(
			long humanGuid, int gradeId, int remainRewardNum) {
		HumanSingleRechargeRewardEntity entity = new HumanSingleRechargeRewardEntity();
		entity.getBuilder().setHumanGuid(humanGuid);
		entity.getBuilder()
				.setSingleRechargeReward(
						com.hifun.soul.proto.common.SingleRechargeRewards.SingleRechargeReward
								.newBuilder().setGradeId(gradeId)
								.setRemainRewardNum(remainRewardNum));
		return entity;
	}

	public static HumanTotalRechargeRewardEntity toTotalRechargeReward(
			long humanGuid, TotalRechargeGradeInfo totalGradeRewardInfo) {
		HumanTotalRechargeRewardEntity entity = new HumanTotalRechargeRewardEntity();
		entity.getBuilder().setHumanGuid(humanGuid);
		entity.getBuilder()
				.setTotalRechargeReward(
						com.hifun.soul.proto.common.TotalRechargeRewards.TotalRechargeReward
								.newBuilder()
								.setGradeId(totalGradeRewardInfo.getGradeId())
								.setRemainRewardNum(
										totalGradeRewardInfo
												.getRemainRewardNum())
								.setRewardState(
										totalGradeRewardInfo.getRewardState()));
		return entity;
	}
}
