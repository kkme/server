



-- create class
local TotalRechargeGradeInfo = class("TotalRechargeGradeInfo")

		--[[ 档位ID ]]
		TotalRechargeGradeInfo.gradeId = nil
		--[[ 充值金额 ]]
		TotalRechargeGradeInfo.rechargeNum = nil
		--[[ 领奖剩余次数 ]]
		TotalRechargeGradeInfo.remainRewardNum = nil
		--[[ 累计充值奖励信息 ]]
		TotalRechargeGradeInfo.rewardInfos = {}
