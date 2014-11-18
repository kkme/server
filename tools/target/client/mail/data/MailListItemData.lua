



-- create class
local MailListItemData = class("MailListItemData")

		--[[ 邮件Id ]]
		MailListItemData.mailId = nil
		--[[ 邮件类型（系统，玩家） ]]
		MailListItemData.mailType = nil
		--[[ 邮件主题 ]]
		MailListItemData.theme = nil
		--[[ 发件人姓名 ]]
		MailListItemData.sendHumanName = nil
		--[[ 是否已读取 ]]
		MailListItemData.isRead = nil
		--[[ 是否含附件 ]]
		MailListItemData.isAttachment = nil
		--[[ 是否已拾取附件物品 ]]
		MailListItemData.isPickUp = nil
		--[[ 过期天数（仅在邮件含有奖励物品时有效,0表示已过期） ]]
		MailListItemData.expireDays = nil
		--[[ 是否选中（仅用于客户端绑定） ]]
		MailListItemData.checked = nil
		--[[ 发送时间 ]]
		MailListItemData.sendTime = nil
