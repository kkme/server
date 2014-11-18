



-- create class
local ArenaNoticeData = class("ArenaNoticeData")

		--[[ 角色id ]]
		ArenaNoticeData.roleId = nil
		--[[ 角色名称 ]]
		ArenaNoticeData.roleName = nil
		--[[ 角色id ]]
		ArenaNoticeData.otherRoleId = nil
		--[[ 角色名称 ]]
		ArenaNoticeData.otherRoleName = nil
		--[[ 是否是赢家 ]]
		ArenaNoticeData.win = nil
		--[[ 时间间隔  ]]
		ArenaNoticeData.timeInterval = nil
		--[[ 是否是挑战者 ]]
		ArenaNoticeData.isChallenger = nil
		--[[ 排名 ]]
		ArenaNoticeData.rank = nil
		--[[ 是否是前5名，且高排名挑战低排名 ]]
		ArenaNoticeData.isUpFiveAndHigherVsLower = nil
