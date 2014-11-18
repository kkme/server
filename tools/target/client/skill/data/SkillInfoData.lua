



-- create class
local SkillInfoData = class("SkillInfoData")

		--[[ 奖励物品ID ]]
		SkillInfoData.skillId = nil
		--[[ 技能名称 ]]
		SkillInfoData.skillName = nil
		--[[ 技能描述 ]]
		SkillInfoData.skillDesc = nil
		--[[ 技能动作id ]]
		SkillInfoData.skillActionId = nil
		--[[ 飞行特效ID ]]
		SkillInfoData.flyEffectId = nil
		--[[ 受击特效ID ]]
		SkillInfoData.beHitedEffectId = nil
		--[[ 敌方受击特效ID ]]
		SkillInfoData.enemyBeHitedEffectId = nil
		--[[ 技能冷却回合 ]]
		SkillInfoData.cooldownRound = nil
		--[[ 攻击类型,近身或者远程 ]]
		SkillInfoData.attackType = nil
		--[[ 技能类型(普通攻击,combo攻击, 其它技能) ]]
		SkillInfoData.skillType = nil
		--[[ 是否需要点选宝石 ]]
		SkillInfoData.needSelectGem = nil
		--[[ 点选宝石类型 ]]
		SkillInfoData.rangeType = nil
		--[[ 使用技能后;回合是否结束 ]]
		SkillInfoData.useRoundOver = nil
		--[[ 红消耗 ]]
		SkillInfoData.redCost = nil
		--[[ 黄消耗 ]]
		SkillInfoData.yellowCost = nil
		--[[ 绿消耗 ]]
		SkillInfoData.greenCost = nil
		--[[ 蓝消耗 ]]
		SkillInfoData.blueCost = nil
		--[[ 紫消耗 ]]
		SkillInfoData.purpleCost = nil
		--[[ 是否在战斗中携带 ]]
		SkillInfoData.isCarried = nil
		--[[ 技能音效 ]]
		SkillInfoData.skillSound = nil
		--[[ 技能图标ID ]]
		SkillInfoData.skillIcon = nil
		--[[ 技能栏位置, 只有装备了的技能才有此属性 ]]
		SkillInfoData.slotIndex = nil
		--[[ 技能状态 ]]
		SkillInfoData.skillState = nil
		--[[ 技能发展类型 ]]
		SkillInfoData.skillDevelopType = nil
		--[[ 需要等级 ]]
		SkillInfoData.needLevel = nil
		--[[ 需要技能点 ]]
		SkillInfoData.needSkillPoints = nil
		--[[ 卷轴名称 ]]
		SkillInfoData.skillScrollName = nil
		--[[ 是否需要卷轴 ]]
		SkillInfoData.isNeedSkillScroll = nil
		--[[ 前置技能名称 ]]
		SkillInfoData.preSkillName = nil
		--[[ 前置技能是否已经开启 ]]
		SkillInfoData.preSkillIsOpen = nil
		--[[ 是否有技能卷轴 ]]
		SkillInfoData.hasSkillScroll = nil
		--[[ 技能点是否足够 ]]
		SkillInfoData.skillPointsIsEnough = nil
