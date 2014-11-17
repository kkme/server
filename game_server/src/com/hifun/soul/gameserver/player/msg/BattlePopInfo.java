package com.hifun.soul.gameserver.player.msg;

import java.util.ArrayList;
import java.util.List;

public class BattlePopInfo {
	private long roleId;
	private List<String> words = new ArrayList<String>();

	public long getRoleId() {
		return roleId;
	}

	public void setRoleId(long roleId) {
		this.roleId = roleId;
	}

	public String[] getWords() {
		return words.toArray(new String[0]);
	}

	public void setWords(String[] words) {
		for (String word : words) {
			this.words.add(word);
		}
	}
}
