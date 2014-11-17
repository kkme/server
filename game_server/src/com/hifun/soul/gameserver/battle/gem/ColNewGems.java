package com.hifun.soul.gameserver.battle.gem;

import java.util.ArrayList;
import java.util.List;

public class ColNewGems {
	/** 列号 */
	private int col;
	/** 本列生成的所有宝石 */
	private List<Integer> gems = new ArrayList<Integer>();

	public int[] getGems() {
		int[] result = new int[gems.size()];
		int index = 0;
		for (Integer each : gems) {
			result[index] = each;
			index++;
		}
		return result;
	}

	public void setGems(int[] gems) {
		for (int each : gems) {
			this.gems.add(each);
		}
	}

	public int getCol() {
		return col;
	}

	public void setCol(int col) {
		this.col = col;
	}
}
