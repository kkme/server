



-- create class
local NormalTrainingInfoData = class("NormalTrainingInfoData")

		--[[ 训练Id ]]
		NormalTrainingInfoData.trainingId = nil
		--[[ 训练名称 ]]
		NormalTrainingInfoData.trainingName = nil
		--[[ 训练类型 ]]
		NormalTrainingInfoData.trainingType = nil
		--[[ 训练所需金币 ]]
		NormalTrainingInfoData.costCoinNum = nil
		--[[ 训练所得经验值 ]]
		NormalTrainingInfoData.exp = nil
		--[[ 1:待训练，2:正在训练中,3:可收获训练经验 ]]
		NormalTrainingInfoData.trainingState = nil
