package com.hifun.soul.gameserver.human.quest.aim;

import com.hifun.soul.gameserver.human.quest.aim.counter.IQuestAimCounter;

public interface IQuestAim {
	
	public MainQuestAimType getType();
	
	public int getIndex();
	
	public void setIndex(int index);
	
	public IQuestAimCounter createQuestAimCounter();
	
}
