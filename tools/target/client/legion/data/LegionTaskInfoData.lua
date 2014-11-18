



-- create class
local LegionTaskInfoData = class("LegionTaskInfoData")

		--[[ 任务ID ]]
		LegionTaskInfoData.taskId = nil
		--[[ 任务名称 ]]
		LegionTaskInfoData.taskTheme = nil
		--[[ 图标ID ]]
		LegionTaskInfoData.iconId = nil
		--[[ 颜色ID ]]
		LegionTaskInfoData.colorId = nil
		--[[ 奖励勋章 ]]
		LegionTaskInfoData.rewardMedal = nil
		--[[ 奖励军团经验 ]]
		LegionTaskInfoData.rewardLegionExperience = nil
		--[[ 是否有任务奖励 ]]
		LegionTaskInfoData.hasTaskReward = nil
		--[[ 剩余时间(ms) ]]
		LegionTaskInfoData.remainTime = nil
		--[[ 需要时间(min) ]]
		LegionTaskInfoData.needTime = nil
