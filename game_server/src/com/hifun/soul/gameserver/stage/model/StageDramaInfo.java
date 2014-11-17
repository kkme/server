package com.hifun.soul.gameserver.stage.model;

/**
 * 关卡剧情信息
 * @author magicstone
 */
public class StageDramaInfo {
	/**  关卡id */
	protected int stageId;
	/**  剧情顺序 */
	protected int order;
	/**  剧情类型(1:对话;2:旁白;) */
	protected int dramaType;
	/**  对话内容 */
	protected String content;
	/**  人物名称 */
	protected String name;
	/**  头像图标 */
	protected int icon;
	/**  头像位置(1:左;2:右) */
	protected int positionType;

	public int getStageId() {
		return stageId;
	}
	public void setStageId(int stageId) {
		this.stageId = stageId;
	}
	public int getOrder() {
		return order;
	}
	public void setOrder(int order) {
		this.order = order;
	}
	public int getDramaType() {
		return dramaType;
	}
	public void setDramaType(int dramaType) {
		this.dramaType = dramaType;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
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
	public int getPositionType() {
		return positionType;
	}
	public void setPositionType(int positionType) {
		this.positionType = positionType;
	}
	
}
