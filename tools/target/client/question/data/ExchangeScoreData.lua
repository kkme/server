



-- create class
local ExchangeScoreData = class("ExchangeScoreData")

		--[[ 兑换id ]]
		ExchangeScoreData.id = nil
		--[[ 积分线 ]]
		ExchangeScoreData.score = nil
		--[[ 兑换状态：1表示未兑换，2表示已兑换 ]]
		ExchangeScoreData.exchangeState = nil
		--[[ 金币 ]]
		ExchangeScoreData.coin = nil
		--[[ 经验 ]]
		ExchangeScoreData.exp = nil
		--[[ 科技点 ]]
		ExchangeScoreData.techPoint = nil
