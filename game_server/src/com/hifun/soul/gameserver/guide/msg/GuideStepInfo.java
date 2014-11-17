package com.hifun.soul.gameserver.guide.msg;

/**
 * 新手引导步骤信息
 * 
 * @author magicstone
 *
 */
public class GuideStepInfo {
	/**  引导类型 */
	private short guideTypeId;
	/** 蒙板颜色 */
	private int maskColorInt;
	/** 蒙板透明度 */
	private short maskAlpha;
	/** 引导文字 */
	private String guideText;
	/** 引导文字多语言 Id */
	private int guideTextLangId;
	/** 引导文字 X 坐标位置 */
	private short textPosX;
	/** 引导文字 Y 坐标位置 */
	private short textPosY;
	/** 引导文字显示方式 */
	private short textMode;
	/** 高亮区域 1 X 坐标位置 */
	private short highlite1PosX;
	/** 高亮区域 1 Y 坐标位置 */
	private short highlite1PosY;
	/** 高亮区域 1 高度 */
	private short highlite1Width;
	/** 高亮区域 1 宽度 */
	private short highlite1Height;
	/** 高亮区域 1 边框颜色 */
	private int highlite1BorderColor;
	/** 高亮区域 1 边框厚度 */
	private short highlite1BorderThickness;

	/** 高亮区域 2 X 坐标位置 */
	private short highlite2PosX;
	/** 高亮区域 2 Y 坐标位置 */
	private short highlite2PosY;
	/** 高亮区域 2 高度 */
	private short highlite2Width;
	/** 高亮区域 2 宽度 */
	private short highlite2Height;
	/** 高亮区域 2 边框颜色 */
	private int highlite2BorderColor;
	/** 高亮区域 2 边框厚度 */
	private short highlite2BorderThickness;

	/** 高亮区域 3 X 坐标位置 */
	private short highlite3PosX;
	/** 高亮区域 3 Y 坐标位置 */
	private short highlite3PosY;
	/** 高亮区域 3 高度 */
	private short highlite3Width;
	/** 高亮区域 3 宽度 */
	private short highlite3Height;
	/** 高亮区域 3 边框颜色 */
	private int highlite3BorderColor;
	/** 高亮区域 3 边框厚度 */
	private short highlite3BorderThickness;
	
	/** 高亮区域 4 X 坐标位置 */
	private short highlite4PosX;
	/** 高亮区域 4 Y 坐标位置 */
	private short highlite4PosY;
	/** 高亮区域 4 高度 */
	private short highlite4Width;
	/** 高亮区域 4 宽度 */
	private short highlite4Height;
	/** 高亮区域 4 边框颜色 */
	private int highlite4BorderColor;
	/** 高亮区域 4 边框厚度 */
	private short highlite4BorderThickness;

	/** 高亮区域 5 X 坐标位置 */
	private short highlite5PosX;
	/** 高亮区域 5 Y 坐标位置 */
	private short highlite5PosY;
	/** 高亮区域 5 高度 */
	private short highlite5Width;
	/** 高亮区域 5 宽度 */
	private short highlite5Height;
	/** 高亮区域 5 边框颜色 */
	private int highlite5BorderColor;
	/** 高亮区域 5 边框厚度 */
	private short highlite5BorderThickness;

	/** 箭头 1 位置 */
	private short arrow1PosX;
	private short arrow1PosY;
	/** 箭头 2 位置 */
	private short arrow2PosX;
	private short arrow2PosY;
	/** 箭头 3 位置 */
	private short arrow3PosX;
	private short arrow3PosY;

	/** 可操作区域 X 坐标位置 */
	private short operationalPosX;
	/** 可操作区域 Y 坐标位置 */
	private short operationalPosY;
	/** 可操作区域宽度 */
	private short operationalWidth;
	/** 可操作区域高度 */
	private short operationalHeight;
	/** 可操作区域宽度 */
	private int operationalBorderColor;
	/** 可操作区域高度 */
	private short operationalBorderThickness;
	/** 事件 */
	private String event;
	/** 控件名称 */
	private String controlName;
	/** 动画资源名称 */
	private String movie;
	/** 在指定延迟时间后自动提交 */
	private int autoCommit;
	/** 引导场景 */
	private int sceneType;
	/** 技能id */
	private int skillId = -1;
	/** 行 */
	private short row1 = -1;
	/** 列 */
	private short col1 = -1;
	/** 行 */
	private short row2 = -1;
	/** 列 */
	private short col2 = -1;
	/** 引导图标 */
	private int guideIcon;
	/** 引导图标x */
	private int guideIconX;
	/** 引导图标y */
	private int guideIconY;
	/** 是否需要设置宝石位置 */
	private boolean needSetGemPos;
	/** 是否需要设置技能id */
	private boolean needSetSkill;

	public short getGuideTypeId() {
		return guideTypeId;
	}
	
	public void setGuideTypeId(short guideTypeId) {
		this.guideTypeId = guideTypeId;
	}
	
	public int getMaskColorInt() {
		return maskColorInt;
	}
	
	public void setMaskColorInt(int maskColorInt) {
		this.maskColorInt = maskColorInt;
	}
	
	public short getMaskAlpha() {
		return maskAlpha;
	}
	
	public void setMaskAlpha(short maskAlpha) {
		this.maskAlpha = maskAlpha;
	}
	
	public String getGuideText() {
		return guideText;
	}
	
	public void setGuideText(String guideText) {
		this.guideText = guideText;
	}
	
	public int getGuideTextLangId() {
		return guideTextLangId;
	}
	
	public void setGuideTextLangId(int guideTextLangId) {
		this.guideTextLangId = guideTextLangId;
	}
	
	public short getTextPosX() {
		return textPosX;
	}
	
	public void setTextPosX(short textPosX) {
		this.textPosX = textPosX;
	}
	
	public short getTextPosY() {
		return textPosY;
	}
	
	public void setTextPosY(short textPosY) {
		this.textPosY = textPosY;
	}
	
	public short getTextMode() {
		return textMode;
	}
	
	public void setTextMode(short textMode) {
		this.textMode = textMode;
	}
	
	public short getHighlite1PosX() {
		return highlite1PosX;
	}
	
	public void setHighlite1PosX(short highlite1PosX) {
		this.highlite1PosX = highlite1PosX;
	}
	
	public short getHighlite1PosY() {
		return highlite1PosY;
	}
	
	public void setHighlite1PosY(short highlite1PosY) {
		this.highlite1PosY = highlite1PosY;
	}
	
	public short getHighlite1Width() {
		return highlite1Width;
	}
	
	public void setHighlite1Width(short highlite1Width) {
		this.highlite1Width = highlite1Width;
	}
	
	public short getHighlite1Height() {
		return highlite1Height;
	}
	
	public void setHighlite1Height(short highlite1Height) {
		this.highlite1Height = highlite1Height;
	}
	
	public int getHighlite1BorderColor() {
		return highlite1BorderColor;
	}
	
	public void setHighlite1BorderColor(int highlite1BorderColor) {
		this.highlite1BorderColor = highlite1BorderColor;
	}
	
	public short getHighlite1BorderThickness() {
		return highlite1BorderThickness;
	}
	
	public void setHighlite1BorderThickness(short highlite1BorderThickness) {
		this.highlite1BorderThickness = highlite1BorderThickness;
	}
	
	public short getHighlite2PosX() {
		return highlite2PosX;
	}
	
	public void setHighlite2PosX(short highlite2PosX) {
		this.highlite2PosX = highlite2PosX;
	}
	
	public short getHighlite2PosY() {
		return highlite2PosY;
	}
	
	public void setHighlite2PosY(short highlite2PosY) {
		this.highlite2PosY = highlite2PosY;
	}
	
	public short getHighlite2Width() {
		return highlite2Width;
	}
	
	public void setHighlite2Width(short highlite2Width) {
		this.highlite2Width = highlite2Width;
	}
	
	public short getHighlite2Height() {
		return highlite2Height;
	}
	
	public void setHighlite2Height(short highlite2Height) {
		this.highlite2Height = highlite2Height;
	}
	
	public int getHighlite2BorderColor() {
		return highlite2BorderColor;
	}
	
	public void setHighlite2BorderColor(int highlite2BorderColor) {
		this.highlite2BorderColor = highlite2BorderColor;
	}
	
	public short getHighlite2BorderThickness() {
		return highlite2BorderThickness;
	}
	
	public void setHighlite2BorderThickness(short highlite2BorderThickness) {
		this.highlite2BorderThickness = highlite2BorderThickness;
	}
	
	public short getHighlite3PosX() {
		return highlite3PosX;
	}
	
	public void setHighlite3PosX(short highlite3PosX) {
		this.highlite3PosX = highlite3PosX;
	}
	public short getHighlite3PosY() {
		return highlite3PosY;
	}
	
	public void setHighlite3PosY(short highlite3PosY) {
		this.highlite3PosY = highlite3PosY;
	}
	
	public short getHighlite3Width() {
		return highlite3Width;
	}
	
	public void setHighlite3Width(short highlite3Width) {
		this.highlite3Width = highlite3Width;
	}
	
	public short getHighlite3Height() {
		return highlite3Height;
	}
	
	public void setHighlite3Height(short highlite3Height) {
		this.highlite3Height = highlite3Height;
	}
	
	public int getHighlite3BorderColor() {
		return highlite3BorderColor;
	}
	
	public void setHighlite3BorderColor(int highlite3BorderColor) {
		this.highlite3BorderColor = highlite3BorderColor;
	}
	
	public short getHighlite3BorderThickness() {
		return highlite3BorderThickness;
	}
	
	public void setHighlite3BorderThickness(short highlite3BorderThickness) {
		this.highlite3BorderThickness = highlite3BorderThickness;
	}

	public short getHighlite4PosX() {
		return highlite4PosX;
	}

	public void setHighlite4PosX(short highlite4PosX) {
		this.highlite4PosX = highlite4PosX;
	}

	public short getHighlite4PosY() {
		return highlite4PosY;
	}

	public void setHighlite4PosY(short highlite4PosY) {
		this.highlite4PosY = highlite4PosY;
	}

	public short getHighlite4Width() {
		return highlite4Width;
	}

	public void setHighlite4Width(short highlite4Width) {
		this.highlite4Width = highlite4Width;
	}

	public short getHighlite4Height() {
		return highlite4Height;
	}

	public void setHighlite4Height(short highlite4Height) {
		this.highlite4Height = highlite4Height;
	}

	public int getHighlite4BorderColor() {
		return highlite4BorderColor;
	}

	public void setHighlite4BorderColor(int highlite4BorderColor) {
		this.highlite4BorderColor = highlite4BorderColor;
	}

	public short getHighlite4BorderThickness() {
		return highlite4BorderThickness;
	}

	public void setHighlite4BorderThickness(short highlite4BorderThickness) {
		this.highlite4BorderThickness = highlite4BorderThickness;
	}

	public short getHighlite5PosX() {
		return highlite5PosX;
	}

	public void setHighlite5PosX(short highlite5PosX) {
		this.highlite5PosX = highlite5PosX;
	}

	public short getHighlite5PosY() {
		return highlite5PosY;
	}

	public void setHighlite5PosY(short highlite5PosY) {
		this.highlite5PosY = highlite5PosY;
	}

	public short getHighlite5Width() {
		return highlite5Width;
	}

	public void setHighlite5Width(short highlite5Width) {
		this.highlite5Width = highlite5Width;
	}

	public short getHighlite5Height() {
		return highlite5Height;
	}

	public void setHighlite5Height(short highlite5Height) {
		this.highlite5Height = highlite5Height;
	}

	public int getHighlite5BorderColor() {
		return highlite5BorderColor;
	}

	public void setHighlite5BorderColor(int highlite5BorderColor) {
		this.highlite5BorderColor = highlite5BorderColor;
	}

	public short getHighlite5BorderThickness() {
		return highlite5BorderThickness;
	}

	public void setHighlite5BorderThickness(short highlite5BorderThickness) {
		this.highlite5BorderThickness = highlite5BorderThickness;
	}

	public short getArrow1PosX() {
		return arrow1PosX;
	}

	public void setArrow1PosX(short value) {
		this.arrow1PosX = value;
	}

	public short getArrow1PosY() {
		return arrow1PosY;
	}

	public void setArrow1PosY(short value) {
		this.arrow1PosY = value;
	}

	public short getArrow2PosX() {
		return arrow2PosX;
	}

	public void setArrow2PosX(short value) {
		this.arrow2PosX = value;
	}

	public short getArrow2PosY() {
		return arrow2PosY;
	}

	public void setArrow2PosY(short value) {
		this.arrow2PosY = value;
	}

	public short getArrow3PosX() {
		return arrow3PosX;
	}

	public void setArrow3PosX(short value) {
		this.arrow3PosX = value;
	}

	public short getArrow3PosY() {
		return arrow3PosY;
	}

	public void setArrow3PosY(short Arrow3PosY) {
		this.arrow3PosY = Arrow3PosY;
	}

	public String getEvent() {
		return event;
	}

	public void setEvent(String event) {
		this.event = event;
	}

	public String getControlName() {
		return controlName;
	}

	public void setControlName(String controlName) {
		this.controlName = controlName;
	}

	public short getOperationalPosX() {
		return operationalPosX;
	}
	
	public void setOperationalPosX(short operationalPosX) {
		this.operationalPosX = operationalPosX;
	}
	
	public short getOperationalPosY() {
		return operationalPosY;
	}
	
	public void setOperationalPosY(short operationalPosY) {
		this.operationalPosY = operationalPosY;
	}
	
	public short getOperationalWidth() {
		return operationalWidth;
	}
	
	public void setOperationalWidth(short operationalWidth) {
		this.operationalWidth = operationalWidth;
	}
	
	public short getOperationalHeight() {
		return operationalHeight;
	}
	
	public void setOperationalHeight(short operationalHeight) {
		this.operationalHeight = operationalHeight;
	}
	
	public int getOperationalBorderColor() {
		return operationalBorderColor;
	}
	
	public void setOperationalBorderColor(int operationalBorderColor) {
		this.operationalBorderColor = operationalBorderColor;
	}
	
	public short getOperationalBorderThickness() {
		return operationalBorderThickness;
	}
	
	public void setOperationalBorderThickness(short operationalBorderThickness) {
		this.operationalBorderThickness = operationalBorderThickness;
	}

	public String getMovie() {
		return movie;
	}

	public void setMovie(String movie) {
		this.movie = movie;
	}

	public int getAutoCommit() {
		return autoCommit;
	}

	public void setAutoCommit(int autoCommit) {
		this.autoCommit = autoCommit;
	}
	
	public int getSceneType() {
		return sceneType;
	}

	public int getSkillId() {
		return skillId;
	}

	public void setSkillId(int skillId) {
		this.skillId = skillId;
	}

	public short getRow1() {
		return row1;
	}

	public void setRow1(short row1) {
		this.row1 = row1;
	}

	public short getCol1() {
		return col1;
	}

	public void setCol1(short col1) {
		this.col1 = col1;
	}

	public short getRow2() {
		return row2;
	}

	public void setRow2(short row2) {
		this.row2 = row2;
	}

	public short getCol2() {
		return col2;
	}

	public void setCol2(short col2) {
		this.col2 = col2;
	}

	public void setSceneType(int sceneType) {
		this.sceneType = sceneType;
	}
	
	public int getGuideIcon() {
		return guideIcon;
	}

	public void setGuideIcon(int guideIcon) {
		this.guideIcon = guideIcon;
	}
	
	public int getGuideIconX() {
		return guideIconX;
	}

	public void setGuideIconX(int guideIconX) {
		this.guideIconX = guideIconX;
	}

	public int getGuideIconY() {
		return guideIconY;
	}

	public void setGuideIconY(int guideIconY) {
		this.guideIconY = guideIconY;
	}

	public boolean isNeedSetGemPos() {
		return needSetGemPos;
	}

	public void setNeedSetGemPos(boolean needSetGemPos) {
		this.needSetGemPos = needSetGemPos;
	}

	public boolean isNeedSetSkill() {
		return needSetSkill;
	}

	public void setNeedSetSkill(boolean needSetSkill) {
		this.needSetSkill = needSetSkill;
	}

	@Override
	public String toString() {
		return "GuideStepInfo [ guideText = " + this.guideText 
			+ ", textPosX = " + this.textPosX 
			+ ", textPosY = " + this.textPosY 
			+ ", textMode = " + this.textMode
			
			+ ", highlight1PosX = " + this.highlite1PosX
			+ ", highlight1PosY = " + this.highlite1PosY
			+ ", highlight1Width = " + this.highlite1Width
			+ ", highlight1Height = " + this.highlite1Height
			+ ", highlight1BorderColor = " + this.highlite1BorderColor
			+ ", highlight1BorderThickness = " + this.highlite1BorderThickness
			
			+ ", highlight2PosX = " + this.highlite2PosX
			+ ", highlight2PosY = " + this.highlite2PosY
			+ ", highlight2Width = " + this.highlite2Width
			+ ", highlight2Height = " + this.highlite2Height
			+ ", highlight2BorderColor = " + this.highlite2BorderColor
			+ ", highlight2BorderThickness = " + this.highlite2BorderThickness
			
			+ ", highlight3PosX = " + this.highlite3PosX
			+ ", highlight3PosY = " + this.highlite3PosY
			+ ", highlight3Width = " + this.highlite3Width
			+ ", highlight3Height = " + this.highlite3Height
			+ ", highlight3BorderColor = " + this.highlite3BorderColor
			+ ", highlight3BorderThickness = " + this.highlite3BorderThickness

			+ ", highlight4PosX = " + this.highlite4PosX
			+ ", highlight4PosY = " + this.highlite4PosY
			+ ", highlight4Width = " + this.highlite4Width
			+ ", highlight4Height = " + this.highlite4Height
			+ ", highlight4BorderColor = " + this.highlite4BorderColor
			+ ", highlight4BorderThickness = " + this.highlite4BorderThickness

			+ ", highlight5PosX = " + this.highlite5PosX
			+ ", highlight5PosY = " + this.highlite5PosY
			+ ", highlight5Width = " + this.highlite5Width
			+ ", highlight5Height = " + this.highlite5Height
			+ ", highlight5BorderColor = " + this.highlite5BorderColor
			+ ", highlight5BorderThickness = " + this.highlite5BorderThickness

			+ ", arrow1PosX = " + this.arrow1PosX
			+ ", arrow1PosY = " + this.arrow1PosY
			+ ", arrow2PosX = " + this.arrow2PosX
			+ ", arrow2PosY = " + this.arrow2PosY
			+ ", arrow3PosX = " + this.arrow3PosX
			+ ", arrow3PosY = " + this.arrow3PosY
			
			+ ", operationalPosX = " + this.operationalPosX
			+ ", operationalPosY = " + this.operationalPosY
			+ ", operationalWidth = " + this.operationalWidth
			+ ", operationalHeight = " + this.operationalHeight 
			+ ", operationalBorderColor = " + this.operationalBorderColor
			+ ", operationalBorderThickness = " + this.operationalBorderThickness
			+ ", event = " + this.event
			+ ", controlName = " + this.controlName
			+ ", movie = " + this.movie
			+ ", autoCommit = " + this.autoCommit
			+ ", sceneType = " + this.sceneType + " ]";
	}
}
