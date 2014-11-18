



-- create class
local DailyQuestRewardBoxData = class("DailyQuestRewardBoxData")

		--[[ 箱子ID ]]
		DailyQuestRewardBoxData.boxId = nil
		--[[ 箱子状态 ]]
		DailyQuestRewardBoxData.state = nil
		--[[ 积分限制 ]]
		DailyQuestRewardBoxData.scoreLimit = nil
		--[[ 奖励金币 ]]
		DailyQuestRewardBoxData.rewardMoney = nil
		--[[ 宝箱tip ]]
		DailyQuestRewardBoxData.tip = nil
		--[[ 日常任务物品奖励 ]]
		DailyQuestRewardBoxData.dailyRewardItems = {}
