



-- create class
local GuideStepInfoData = class("GuideStepInfoData")

		--[[ 蒙板颜色 ]]
		GuideStepInfoData.maskColorInt = nil
		--[[ 蒙板透明度 ]]
		GuideStepInfoData.maskAlpha = nil
		--[[ 引导文字 ]]
		GuideStepInfoData.guideText = nil
		--[[ 引导文字 X 坐标位置 ]]
		GuideStepInfoData.textPosX = nil
		--[[ 引导文字 Y 坐标位置 ]]
		GuideStepInfoData.textPosY = nil
		--[[ 引导文字方式 ]]
		GuideStepInfoData.textMode = nil
		--[[ 高亮区域 1 X 坐标位置 ]]
		GuideStepInfoData.highlite1PosX = nil
		--[[ 高亮区域 1 Y 坐标位置 ]]
		GuideStepInfoData.highlite1PosY = nil
		--[[ 高亮区域 1 宽度 ]]
		GuideStepInfoData.highlite1Width = nil
		--[[ 高亮区域 1 高度 ]]
		GuideStepInfoData.highlite1Height = nil
		--[[ 高亮区域 1 边框颜色 ]]
		GuideStepInfoData.highlite1BorderColor = nil
		--[[ 高亮区域 1 边框厚度 ]]
		GuideStepInfoData.highlite1BorderThickness = nil
		--[[ 高亮区域 2 X 坐标位置 ]]
		GuideStepInfoData.highlite2PosX = nil
		--[[ 高亮区域 2 Y 坐标位置 ]]
		GuideStepInfoData.highlite2PosY = nil
		--[[ 高亮区域 2 宽度 ]]
		GuideStepInfoData.highlite2Width = nil
		--[[ 高亮区域 2 高度 ]]
		GuideStepInfoData.highlite2Height = nil
		--[[ 高亮区域 2 边框颜色 ]]
		GuideStepInfoData.highlite2BorderColor = nil
		--[[ 高亮区域 2 边框厚度 ]]
		GuideStepInfoData.highlite2BorderThickness = nil
		--[[ 高亮区域 3 X 坐标位置 ]]
		GuideStepInfoData.highlite3PosX = nil
		--[[ 高亮区域 3 Y 坐标位置 ]]
		GuideStepInfoData.highlite3PosY = nil
		--[[ 高亮区域 3 宽度 ]]
		GuideStepInfoData.highlite3Width = nil
		--[[ 高亮区域 3 高度 ]]
		GuideStepInfoData.highlite3Height = nil
		--[[ 高亮区域 3 边框颜色 ]]
		GuideStepInfoData.highlite3BorderColor = nil
		--[[ 高亮区域 3 边框厚度 ]]
		GuideStepInfoData.highlite3BorderThickness = nil
		--[[ 高亮区域 4 X 坐标位置 ]]
		GuideStepInfoData.highlite4PosX = nil
		--[[ 高亮区域 4 Y 坐标位置 ]]
		GuideStepInfoData.highlite4PosY = nil
		--[[ 高亮区域 4 宽度 ]]
		GuideStepInfoData.highlite4Width = nil
		--[[ 高亮区域 4 高度 ]]
		GuideStepInfoData.highlite4Height = nil
		--[[ 高亮区域 4 边框颜色 ]]
		GuideStepInfoData.highlite4BorderColor = nil
		--[[ 高亮区域 4 边框厚度 ]]
		GuideStepInfoData.highlite4BorderThickness = nil
		--[[ 高亮区域 5 X 坐标位置 ]]
		GuideStepInfoData.highlite5PosX = nil
		--[[ 高亮区域 5 Y 坐标位置 ]]
		GuideStepInfoData.highlite5PosY = nil
		--[[ 高亮区域 5 宽度 ]]
		GuideStepInfoData.highlite5Width = nil
		--[[ 高亮区域 5 高度 ]]
		GuideStepInfoData.highlite5Height = nil
		--[[ 高亮区域 5 边框颜色 ]]
		GuideStepInfoData.highlite5BorderColor = nil
		--[[ 高亮区域 5 边框厚度 ]]
		GuideStepInfoData.highlite5BorderThickness = nil
		--[[ 箭头 1 X 坐标位置 ]]
		GuideStepInfoData.arrow1PosX = nil
		--[[ 箭头 1 Y 坐标位置 ]]
		GuideStepInfoData.arrow1PosY = nil
		--[[ 箭头 2 X 坐标位置 ]]
		GuideStepInfoData.arrow2PosX = nil
		--[[ 箭头 2 Y 坐标位置 ]]
		GuideStepInfoData.arrow2PosY = nil
		--[[ 箭头 3 X 坐标位置 ]]
		GuideStepInfoData.arrow3PosX = nil
		--[[ 箭头 3 Y 坐标位置 ]]
		GuideStepInfoData.arrow3PosY = nil
		--[[ 可操作区域 X 坐标位置 ]]
		GuideStepInfoData.operationalPosX = nil
		--[[ 可操作区域 Y 坐标位置 ]]
		GuideStepInfoData.operationalPosY = nil
		--[[ 可操作区域宽度 ]]
		GuideStepInfoData.operationalWidth = nil
		--[[ 可操作区域高度 ]]
		GuideStepInfoData.operationalHeight = nil
		--[[ 可操作区域边框颜色 ]]
		GuideStepInfoData.operationalBorderColor = nil
		--[[ 可操作区域边框厚度 ]]
		GuideStepInfoData.operationalBorderThickness = nil
		--[[ 事件 ]]
		GuideStepInfoData.event = nil
		--[[ 控件名称 ]]
		GuideStepInfoData.controlName = nil
		--[[ 动画资源名称 ]]
		GuideStepInfoData.movie = nil
		--[[ 在指定延迟时间后自动提交并转入下一步引导 ]]
		GuideStepInfoData.autoCommit = nil
		--[[ 场景类型 ]]
		GuideStepInfoData.sceneType = nil
		--[[ 技能id ]]
		GuideStepInfoData.skillId = nil
		--[[ 可以消除第一个宝石的row位置  ]]
		GuideStepInfoData.row1 = nil
		--[[ 可以消除的第一个宝石的col位置  ]]
		GuideStepInfoData.col1 = nil
		--[[ 可以消除第二个宝石的row位置  ]]
		GuideStepInfoData.row2 = nil
		--[[ 可以消除的第二个宝石的col位置  ]]
		GuideStepInfoData.col2 = nil
		--[[ 引导图标 ]]
		GuideStepInfoData.guideIcon = nil
		--[[ 引导图标x ]]
		GuideStepInfoData.guideIconX = nil
		--[[ 引导图标y ]]
		GuideStepInfoData.guideIconY = nil
