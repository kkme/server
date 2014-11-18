



-- create class
local WarriorQuest = class("WarriorQuest")

		--[[ 任务id ]]
		WarriorQuest.id = nil
		--[[ 达成任务的数量 ]]
		WarriorQuest.totalCount = nil
		--[[ 当前完成数量 ]]
		WarriorQuest.completeCount = nil
		--[[ 达成任务所需伤害百分数 ]]
		WarriorQuest.damageHpPercent = nil
		--[[ 任务状态 ]]
		WarriorQuest.questState = nil
