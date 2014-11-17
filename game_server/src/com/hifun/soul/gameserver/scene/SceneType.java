package com.hifun.soul.gameserver.scene;

/**
 * 场景类型;
 * 
 * @author crazyjohn
 * 
 */
@Deprecated
public enum SceneType {
	SCENE_FOR_MANAGE(1);
	private int type;

	SceneType(int type) {
		this.type = type;
	}

	public int getType() {
		return this.type;
	}
}
