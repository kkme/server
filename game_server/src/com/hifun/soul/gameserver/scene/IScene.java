package com.hifun.soul.gameserver.scene;

import com.hifun.soul.gameserver.human.Human;

/**
 * 场景抽象;
 * 
 * @author crazyjohn
 * 
 */
public interface IScene {

	public void enter(Human human);

	public void leve(Human human);
}
