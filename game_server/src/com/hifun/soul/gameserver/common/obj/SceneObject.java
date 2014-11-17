package com.hifun.soul.gameserver.common.obj;

import com.hifun.soul.common.IHeartBeatable;
import com.hifun.soul.gameserver.scene.Scene;


/**
 * 
 * 抽象的场景对象
 * 
 * @author magicstone
 *
 */
public abstract class SceneObject implements IGameObject,IHeartBeatable {

	/** 场景对象唯一标识 */
	private long id;
	
	/** 场景对象名称 */
	private String name;
	
	/** 场景对象图标 */
	private int icon;
	
	/** 场景对象形象 */
	private int image;
	
	/** 场景对象所处的场景 */
	private Scene scene;
	
	/** 场景对象是否可见 */
	private boolean visable;
	
	/**
	 * 场景对象的心跳处理
	 * 
	 */
	@Override
	public abstract void heartBeat();

	@Override
	public long getId() {
		return id;
	}

	@Override
	public void setId(long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getIcon() {
		return icon;
	}

	public void setIcon(int icon) {
		this.icon = icon;
	}

	public int getImage() {
		return image;
	}

	public void setImage(int image) {
		this.image = image;
	}

	public Scene getScene() {
		return scene;
	}

	public void setScene(Scene scene) {
		this.scene = scene;
	}

	public boolean isVisable() {
		return visable;
	}

	public void setVisable(boolean visable) {
		this.visable = visable;
	}
	
}
