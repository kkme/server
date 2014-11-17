package com.hifun.soul.gameserver.prison.converter;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import com.hifun.soul.gameserver.human.Human;
import com.hifun.soul.gameserver.prison.PrisonLog;
import com.hifun.soul.gameserver.prison.msg.PrisonLogInfo;

public class PrisonLogToInfoConverter {
	public static PrisonLogInfo convert(PrisonLog log) {
		PrisonLogInfo info = new PrisonLogInfo();
		info.setId(log.getId());
		info.setFirstId(log.getFirstId());
		info.setFirstName(log.getFirstName());
		info.setSecondId(log.getSecondId());
		info.setSecondName(log.getSecondName());
		info.setThirdId(log.getThirdId());
		info.setThirdName(log.getThirdName());
		info.setContent(log.getContent());
		info.setResult(log.getResult());
		info.setLogType(log.getLogType());
		info.setOperateTime(log.getOperateTime());
		info.setInteractType(log.getInteractType());
		return info;
	}

	public static PrisonLogInfo[] convertToArray(Human human,
			List<PrisonLog> logList) {
		PrisonLogInfo[] logInfos = new PrisonLogInfo[logList.size()];
		List<PrisonLogInfo> logInfoList = new ArrayList<PrisonLogInfo>();
		Collections.sort(logList, new Comparator<PrisonLog>() {
			// 按时间倒序排列
			@Override
			public int compare(PrisonLog o1, PrisonLog o2) {
				if (o2.getOperateTime() > o1.getOperateTime()) {
					return 1;
				} else if (o2.getOperateTime() < o1.getOperateTime()) {
					return -1;
				}
				return 0;
			}

		});
		for (PrisonLog log : logList) {
			PrisonLogInfo logInfo = convert(log);
			logInfoList.add(logInfo);
		}
		return logInfoList.toArray(logInfos);
	}
}
