



-- create class
local ChessBoardSnapData = class("ChessBoardSnapData")

		--[[ 需要消除的宝石 ]]
		ChessBoardSnapData.erasableGems = {}
		--[[ 新生成的宝石 ]]
		ChessBoardSnapData.generatedGems = {}
		--[[ 本次回合中消除的个数 ]]
		ChessBoardSnapData.eraseCount = nil
		--[[ 是否触发新回合(触发四连消) ]]
		ChessBoardSnapData.triggerNewRound = nil
