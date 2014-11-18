



-- create class
local SingleRechargeGradeInfo = class("SingleRechargeGradeInfo")

		--[[ 档位ID ]]
		SingleRechargeGradeInfo.gradeId = nil
		--[[ 充值金额 ]]
		SingleRechargeGradeInfo.rechargeNum = nil
		--[[ 领奖剩余次数 ]]
		SingleRechargeGradeInfo.remainRewardNum = nil
		--[[ 单笔充值奖励信息 ]]
		SingleRechargeGradeInfo.rewardInfos = {}
