



-- create class
local EscortInfoData = class("EscortInfoData")

		--[[ 押运ID ]]
		EscortInfoData.escortId = nil
		--[[ 怪物模型ID ]]
		EscortInfoData.monsterModelId = nil
		--[[ 怪物类型 ]]
		EscortInfoData.monsterType = nil
		--[[ 押运者ID ]]
		EscortInfoData.ownerId = nil
		--[[ 押运者名称 ]]
		EscortInfoData.ownerName = nil
		--[[ 押运者等级 ]]
		EscortInfoData.ownerLevel = nil
		--[[ 押运者所在军团名称 ]]
		EscortInfoData.ownerLegionName = nil
		--[[ 协助者ID ]]
		EscortInfoData.helperId = nil
		--[[ 协助者名称 ]]
		EscortInfoData.helperName = nil
		--[[ 协助者等级 ]]
		EscortInfoData.helperLevel = nil
		--[[ 剩余被拦截次数 ]]
		EscortInfoData.remainBeRobbedNum = nil
		--[[ 最大被拦截次数 ]]
		EscortInfoData.maxBeRobbedNum = nil
		--[[ 押运剩余时间 ]]
		EscortInfoData.escortRemainTime = nil
		--[[ 鼓舞加成 ]]
		EscortInfoData.encourageRate = nil
		--[[ 押运奖励金币 ]]
		EscortInfoData.escortRewardCoin = nil
		--[[ 护送奖励金币 ]]
		EscortInfoData.helpRewardCoin = nil
		--[[ 拦截奖励金币 ]]
		EscortInfoData.robRewardCoin = nil
		--[[ 押运所需时间 ]]
		EscortInfoData.escortTime = nil
