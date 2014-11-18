



-- create class
local QuestInfoData = class("QuestInfoData")

		--[[ 任务ID ]]
		QuestInfoData.questId = nil
		--[[ 任务类型 ]]
		QuestInfoData.questType = nil
		--[[ 任务npc头像 ]]
		QuestInfoData.npcIcon = nil
		--[[ 任务引导图标 ]]
		QuestInfoData.questIcon = nil
		--[[ 任务名称 ]]
		QuestInfoData.questName = nil
		--[[ 任务状态 ]]
		QuestInfoData.questState = nil
		--[[ 任务描述 ]]
		QuestInfoData.questDesc = nil
		--[[ 任务目标描述 ]]
		QuestInfoData.questAimDesc = nil
		--[[ 接受任务最小等级 ]]
		QuestInfoData.minLevel = nil
		--[[ 任务完成奖励经验 ]]
		QuestInfoData.rewardExp = nil
		--[[ 任务完成奖励金币 ]]
		QuestInfoData.rewardMoney = nil
		--[[ 任务完成奖励积分 ]]
		QuestInfoData.rewardStore = nil
		--[[ 任务目标 ]]
		QuestInfoData.questAims = {}
		--[[ 任务引导信息  ]]
		QuestInfoData.stageGuideInfo = nil
		--[[ 物品 ]]
		QuestInfoData.questItems = {}
		--[[ 剩余时间 ]]
		QuestInfoData.remainTime = nil
		--[[ 任务质量 ]]
		QuestInfoData.quantity = nil
		--[[ 功能ID ]]
		QuestInfoData.gameFuncId = nil
