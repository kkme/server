package com.hifun.soul.gameserver.predict.msg.info;

/**
 * 预见信息
 * 
 * @author yandajun
 * 
 */
public class PredictInfo implements Comparable<PredictInfo> {
	private int predictId;
	private int activateNeedLevel;
	private boolean activated;
	private int x;
	private int y;
	private int[] functions;

	public int getPredictId() {
		return predictId;
	}

	public void setPredictId(int predictId) {
		this.predictId = predictId;
	}

	public int getActivateNeedLevel() {
		return activateNeedLevel;
	}

	public void setActivateNeedLevel(int activateNeedLevel) {
		this.activateNeedLevel = activateNeedLevel;
	}

	public boolean getActivated() {
		return activated;
	}

	public void setActivated(boolean activated) {
		this.activated = activated;
	}

	public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}

	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
	}

	public int[] getFunctions() {
		return functions;
	}

	public void setFunctions(int[] functions) {
		this.functions = functions;
	}

	@Override
	public int compareTo(PredictInfo o) {
		return this.predictId - o.getPredictId();
	}
}
