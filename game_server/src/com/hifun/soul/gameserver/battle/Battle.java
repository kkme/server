package com.hifun.soul.gameserver.battle;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.hifun.soul.common.constants.LangConstants;
import com.hifun.soul.common.constants.SharedConstants;
import com.hifun.soul.core.annotation.NotThreadSafe;
import com.hifun.soul.core.msg.SysInternalMessage;
import com.hifun.soul.core.util.MathUtils;
import com.hifun.soul.gameserver.battle.callback.IBattleCallback;
import com.hifun.soul.gameserver.battle.counter.BattleRoundCounter;
import com.hifun.soul.gameserver.battle.counter.IBattleRoundCounter;
import com.hifun.soul.gameserver.battle.counter.IRoundListener;
import com.hifun.soul.gameserver.battle.gem.ColNewGems;
import com.hifun.soul.gameserver.battle.gem.GemChessBoard;
import com.hifun.soul.gameserver.battle.gem.GemObject;
import com.hifun.soul.gameserver.battle.gem.GemPosition;
import com.hifun.soul.gameserver.battle.gem.GemType;
import com.hifun.soul.gameserver.battle.gem.Move;
import com.hifun.soul.gameserver.battle.gem.erase.EraseRangeType;
import com.hifun.soul.gameserver.battle.manager.schedule.WaitingBattleStartSchedule;
import com.hifun.soul.gameserver.battle.msg.ChessBoardSnap;
import com.hifun.soul.gameserver.battle.msg.GCBadNetEnvironment;
import com.hifun.soul.gameserver.battle.msg.GCBattleWaitingOtherUnitReady;
import com.hifun.soul.gameserver.battle.msg.GCCancelHostingBattleSucceed;
import com.hifun.soul.gameserver.battle.msg.GCChessBoardInfo;
import com.hifun.soul.gameserver.battle.msg.GCChooseActionTimeout;
import com.hifun.soul.gameserver.battle.msg.GCNormalAttack;
import com.hifun.soul.gameserver.battle.msg.GCStartBattleInfo;
import com.hifun.soul.gameserver.battle.msg.GCStartHostingBattle;
import com.hifun.soul.gameserver.battle.msg.GCUpdateChessBoard;
import com.hifun.soul.gameserver.battle.msg.schedule.ChooseActionTimeoutSchedule;
import com.hifun.soul.gameserver.battle.msg.schedule.RestChessBoardFeedbackSchedule;
import com.hifun.soul.gameserver.battle.msg.schedule.WaitingForAnimationFeedbackTimeoutSchedule;
import com.hifun.soul.gameserver.battle.msg.schedule.WaitingForAnimationFeedbackTimeoutSchedule.IBeforeAnimationFeedbackCallback;
import com.hifun.soul.gameserver.battle.processor.IBattleDispatcher;
import com.hifun.soul.gameserver.common.GameServerAssist;
import com.hifun.soul.gameserver.common.msg.GCMessage;
import com.hifun.soul.gameserver.gameworld.GameWorld;
import com.hifun.soul.gameserver.human.Human;
import com.hifun.soul.gameserver.player.state.PlayerState;
import com.hifun.soul.gameserver.skill.HpChangedType;
import com.hifun.soul.gameserver.skill.ISkill;
import com.hifun.soul.gameserver.skill.msg.GCBattleHpChanged;

/**
 * 一场战斗的抽象;
 * 
 * @author crazyjohn
 * 
 */
@NotThreadSafe
public class Battle {
	/** 战斗棋盘 */
	protected GemChessBoard chessBoard = new GemChessBoard(
			SharedConstants.GEM_MAX_ROW, SharedConstants.GEM_MAX_COL, this);
	// 战斗单元
	protected IBattleUnit oneGuy;
	protected IBattleUnit otherGuy;
	// 是否准备好
	protected boolean isOneReady;
	protected boolean isOtherReady;
	/** 当前状态;会有多线程读写问题,所以要保证可视性 */
	private volatile BattleState currentState;
	/** 战斗中的人物对象 */
	private Map<Long, Human> battleHumans = new HashMap<Long, Human>();

	/** 战斗回调;保证线程的可视性 */
	private volatile IBattleCallback battleCallback;
	/** 等待客户端动画反馈超时 */
	private WaitingForAnimationFeedbackTimeoutSchedule animationTimeout;
	/** 等待客户端重置棋盘动画反馈超时 */
	private RestChessBoardFeedbackSchedule resetChessBoardAnimationTimeout;
	/** 选择行动超时 */
	private ChooseActionTimeoutSchedule chooseActionTimeout;
	/** 战斗回合计数器 */
	private BattleRoundCounter battleRoundCounter = new BattleRoundCounter(this);
	/** 战斗行动调度中心 */
	private IBattleActionScheduleCenter battleActionScheduleCenter;
	/** 战斗处理器 */
	private IBattleDispatcher battleProcessor;
	/** 战斗消息构建器 */
	private BattleMessageBuilder builder;
	/** 是否是跟镜像之间的战斗 */
	private boolean battleWithGuarder;
	/** 等待init-start的超时 */
	private WaitingBattleStartSchedule waitStartSchedule;
	private GameWorld sceneManager;
	/** 网络环境差的一方 */
	private IBattleUnit badNetGuy;
	/** 是否在引导中 */
	private boolean isGuideing;

	public Battle(IBattleUnit oneGuy, IBattleUnit otherGuy) {
		this.oneGuy = oneGuy;
		this.otherGuy = otherGuy;
		builder = new BattleMessageBuilder(oneGuy, otherGuy, chessBoard);
		sceneManager = GameServerAssist.getGameWorld();
	}

	/**
	 * 获取战斗类型;
	 * 
	 * @return 返回战斗类型; {@code BattleType}
	 * 
	 */
	public BattleType getBattleType() {
		return this.battleCallback.getBattleType();
	}

	/**
	 * 附加战斗处理器;
	 * 
	 * @param battleProcessor
	 */
	public void attachProcessor(IBattleDispatcher battleProcessor) {
		this.battleProcessor = battleProcessor;
	}

	/**
	 * 构建战斗;
	 * 
	 * @param oneGuy
	 * @param otherGuy
	 * @return
	 */
	public static Battle buildBattle(Human oneGuy, IBattleUnit otherGuy) {
		Battle battle = new Battle(oneGuy, otherGuy);
		// 添加到战斗任务映射
		battle.battleHumans.put(oneGuy.getHumanGuid(), oneGuy);
		if (otherGuy instanceof Human) {
			battle.battleHumans.put(otherGuy.getUnitGuid(), (Human) otherGuy);
		}
		// 战斗初始化
		battle.initBattle();
		return battle;
	}

	/**
	 * 战斗初始化;
	 */
	private void initBattle() {
		// 初始化棋盘;
		this.chessBoard.initChessBoard();
		// 初始化战斗状态;
		this.currentState = BattleState.INIT;
		// 构建战斗调度中心;
		this.battleActionScheduleCenter = new BattleActionScheduleCenter(
				oneGuy, otherGuy, this, this.battleRoundCounter);
	}

	/**
	 * 普通攻击;
	 * 
	 * @param actionUnit
	 *            行动单元;
	 * @param row1
	 * @param col1
	 * @param row2
	 * @param col2
	 */
	public void onNormalAttack(final IBattleUnit actionUnit, int row1,
			int col1, int row2, int col2) {
		// 是否轮到当前角色行动
		if (!isThisUnitTurn(actionUnit)) {
			return;
		}
		// 是不是一步合适的移动
		if (!this.isARightMove(row1, col1, row2, col2)) {
			// 如果移动非法, 执行回调
			actionUnit.onNormalActionInvalid(row1, col1, row2, col2);
			return;
		}
		// 发送普通攻击消息
		GCNormalAttack normalAttackMsg = new GCNormalAttack();
		normalAttackMsg.setCol1(col1);
		normalAttackMsg.setRow1(row1);
		normalAttackMsg.setCol2(col2);
		normalAttackMsg.setRow2(row2);
		// 广播给参加战斗的角色
		this.broadcastToBattleUnits(normalAttackMsg);
		List<ChessBoardSnap> snaps = this.chessBoard
				.trySwapGemAndUpdateChessBoard(row1, col1, row2, col2);
		// 战斗相关的判断 获取连击次数
		int combo = this.chessBoard.getLastActionCombo();
		ISkill skill = null;
		if (combo > 1) {
			skill = actionUnit.getComboAttackSkill();
		} else {
			skill = actionUnit.getNormalAttackSkill();
		}
		if (skill == null) {
			return;
		}
		boolean useResult = useSkill(actionUnit, skill, combo, snaps, -1, -1);
		if (useResult) {
			// 取消行动超时调度;
			cancelClientChooseActionTimeoutSchedule();
		}
	}

	/**
	 * 取消行动超时调度;
	 */
	private void cancelClientChooseActionTimeoutSchedule() {
		if (this.chooseActionTimeout != null) {
			this.chooseActionTimeout.cancel();
			this.chooseActionTimeout = null;
		}
	}

	/**
	 * 暂停行动,等待客户端重置棋盘动画播放完成;
	 */
	private void addResetChessBoardSchedule() {
		// 增加调度
		this.resetChessBoardAnimationTimeout = new RestChessBoardFeedbackSchedule(
				this);
		this.getBattleProcessor()
				.scheduleOnece(
						resetChessBoardAnimationTimeout,
						SharedConstants.BATTLE_WAITING_FOR_RESET_CHESS_BOARD_ANIMATION_TIMEOUT);
	}

	/**
	 * 重置棋盘回馈;
	 */
	public void onResetChessboardFeedback() {
		if (resetChessBoardAnimationTimeout == null) {
			return;
		}
		if (this.resetChessBoardAnimationTimeout.isCanceled()) {
			return;
		}
		this.resetChessBoardAnimationTimeout.cancel();
		continueBattleAction();
	}

	/**
	 * 继续战斗行动;
	 */
	public void continueBattleAction() {
		this.resetChessBoardAnimationTimeout = null;
		// 检查是否有玩家挂了
		doClientAnimationOverCheck();
		this.turnToNextUnitAction();
	}

	/**
	 * 使用除基础攻击技能以外的技能;
	 * 
	 * @param actionUnit
	 * @param chosedSkill
	 * @param combo
	 * @param snaps
	 * @param row
	 *            `
	 * @param col
	 */
	public void useOtherSkill(IBattleUnit actionUnit, ISkill chosedSkill,
			int combo, ArrayList<ChessBoardSnap> snaps, int row, int col) {
		// 是否禁魔
		if (actionUnit.forbidMagic()) {
			return;
		}
		// 是否能量足够
		if (!actionUnit.hasEnoughMagicToUseSuchSkill(chosedSkill)) {
			if (actionUnit instanceof Human) {
				Human actionHuman = (Human) actionUnit;
				actionHuman
						.sendErrorMessage(LangConstants.SKILL_ENERGY_NOT_ENOUGH);
				return;
			}
			return;
		}
		// 是否轮到当前角色行动
		if (!isThisUnitTurn(actionUnit)) {
			return;
		}
		// 技能是否使用成功;
		boolean useResult = useSkill(actionUnit, chosedSkill, combo, snaps,
				row, col);
		if (useResult) {
			// 去掉选择超时
			this.cancelClientChooseActionTimeoutSchedule();
		}
	}

	/**
	 * 使用技能;(包括普通攻击技能和其他技能)
	 * 
	 * @param actionUnit
	 *            行动单元;
	 * @param skill
	 *            使用的技能;
	 * @param combo
	 *            连击次数;
	 * @param snaps
	 *            消除引起的快照;
	 * @param row
	 *            选中宝石的行;
	 * @param col
	 *            选中宝石的列;
	 * @return 返回技能是否使用成功, true为成功, false表示不成功;
	 */
	public boolean useSkill(IBattleUnit actionUnit, ISkill skill, int combo,
			List<ChessBoardSnap> snaps, int row, int col) {
		// 检查技能是否可用
		if (!skill.canUseSkill()) {
			if (actionUnit instanceof Human) {
				Human actionHuman = (Human) actionUnit;
				actionHuman.sendWarningMessage(
						LangConstants.SKILL_NOT_COOL_DOWN,
						skill.getCooldownRound());
			}
			return false;
		}
		boolean useResult = skill.useSkill(actionUnit,
				(this.oneGuy == actionUnit ? this.otherGuy : this.oneGuy),
				combo, snaps, row, col);
		if (!useResult) {
			return false;
		}
		// 记录行动单元
		this.battleRoundCounter.recordActionUnit(actionUnit);
		// 是否结束本回合
		actionUnit.finishCurrentAction();
		// 添加客户端动画反馈调度
		addActionAnimationSchedule(actionUnit);
		return true;
	}

	/**
	 * 添加行动动画反馈调度;
	 */
	private void addActionAnimationSchedule(IBattleUnit actionUnit) {
		// 构建动画超时调度消息;
		this.animationTimeout = new WaitingForAnimationFeedbackTimeoutSchedule(
				actionUnit, this);
		if (this.chessBoard.getMoves().isEmpty() /* Math.random() < 0.5 */) {
			// 看看棋盘是否没有可移动的step, 然后重置棋盘
			this.animationTimeout
					.registBeforeAnimationFeedbackCallback(new IBeforeAnimationFeedbackCallback() {
						@Override
						public void doCall() {
							// 重置棋盘
							doResetChessBoard();
						}

					});
		}
		// 增加客户端行动动画反馈调度
		this.getBattleProcessor().scheduleOnece(animationTimeout,
				SharedConstants.BATTLE_WAITING_FOR_ANIMATION_TIMEOUT);
	}

	/**
	 * 执行重置棋盘, 并且通知客户端新的棋盘信息;
	 */
	public void doResetChessBoard() {
		resetChessBoard();
		// 通知客户端棋盘重置
		GCChessBoardInfo chessBoardInfo = new GCChessBoardInfo();
		// 广播棋盘信息
		List<ColNewGems> gemsList = chessBoard.getAllColGems();
		chessBoardInfo.setChessBoardCols(gemsList.toArray(new ColNewGems[0]));
		broadcastToBattleUnits(chessBoardInfo);
		// 暂停当前的行动单元;等待客户端重置棋盘动画反馈;
		addResetChessBoardSchedule();
	}

	/**
	 * 是不是正确的移动;
	 * 
	 * @param row1
	 * @param col1
	 * @param row2
	 * @param col2
	 * @return
	 */
	private boolean isARightMove(int row1, int col1, int row2, int col2) {
		List<Move> moves = this.getMoves();
		if (moves.isEmpty()) {
			return false;
		}
		for (Move move : moves) {
			if (move.rightMove(row1, col1, row2, col2)) {
				return true;
			}
		}
		return false;
	}

	/**
	 * 等到当前战斗单元行动完成以后的回调;<br>
	 * 
	 * <pre>
	 * 1. 判断被攻击者是否死亡;
	 * 2. 特殊的判断, 比如是否可以接着行动等;
	 * </pre>
	 * 
	 * @param actionUnit
	 * @param waitAnimation
	 *            是否需要等待客户端动画完成反馈;
	 */
	private void afterUnitAction(IBattleUnit actionUnit, boolean waitAnimation) {
		// 转到下一个行动
		// 如果不需要等待动画反馈, 则直接调用超时接口;
		if (!waitAnimation) {
			onAnimationFeedbackTimeout();
			return;
		}
		addActionAnimationSchedule(actionUnit);
	}

	/**
	 * 获取关联的战斗处理器;
	 * 
	 * @return
	 */
	public IBattleDispatcher getBattleProcessor() {
		return this.battleProcessor;
	}

	/**
	 * 换到下一个单元行动;
	 */
	private void turnToNextUnitAction() {
		// 下一个战斗单元行动的时候通知新回合开始
		// notifyNewRound();
		this.battleActionScheduleCenter.turnToNextUnitAction();
	}

	/**
	 * 是否轮流到了当前角色行动;
	 * 
	 * @param unit
	 * @return true表示轮到当前角色行动;
	 */
	public boolean isThisUnitTurn(IBattleUnit unit) {
		return this.battleActionScheduleCenter.isThisUnitTurn(unit);
	}

	/**
	 * 把战斗消息,广播给战斗单元;
	 * 
	 * @param msg
	 */
	public void broadcastToBattleUnits(GCMessage msg) {
		for (Human human : this.battleHumans.values()) {
			human.sendMessage(msg);
		}
	}

	/**
	 * 获取当前战斗的状态;
	 * 
	 * @return
	 */
	public BattleState getCurrentState() {
		return currentState;
	}

	/**
	 * 下发战斗面板信息;
	 */
	public void sendBattleInfo(Human challenger) {
		// 通知客户端进入战斗场景, 并且下发战斗初始信息;
		GCStartBattleInfo battleInfoMsg = builder
				.buildBattleInfoMessage(challenger);
		broadcastToBattleUnits(battleInfoMsg);
	}

	/**
	 * 开始战斗;
	 */
	public void startBattle() {
		if (this.currentState != BattleState.INIT) {
			return;
		}
		// 取消init-start调度
		if (this.waitStartSchedule != null) {
			this.waitStartSchedule.cancel();
			this.waitStartSchedule = null;
		}
		this.currentState = BattleState.STARTING;
		// 玩家和怪打, 玩家先开始行动
		IBattleUnit firstActionUnit = this.getFirstActionUnit();
		this.battleActionScheduleCenter.startFirstAction(firstActionUnit);
		// 注册事件监听
		addRoundListenerForBattleUnits();
		// 通知可以开始行动了
		firstActionUnit.notifyAction();
	}

	/**
	 * 为战斗单元添加事件监听;
	 */
	private void addRoundListenerForBattleUnits() {
		this.addRoundListener(this.oneGuy.getBattleContext().getBuffManager());
		this.addRoundListener(this.otherGuy.getBattleContext().getBuffManager());
	}

	/**
	 * 获取先攻的战斗单元;<br>
	 * PVE直接取出挑战者;PVP到时候看规则;
	 * 
	 * @return
	 */
	private IBattleUnit getFirstActionUnit() {
		// 获取第一个行动者的时候通知新回合开始;
		this.battleRoundCounter.notifyClientNewRound();
		return this.battleCallback.getFirstActionUnit();
	}

	/**
	 * 获得所有正确的移动;
	 * 
	 * @return
	 */
	public List<Move> getMoves() {
		// 是否有可移动的move
		if (this.chessBoard.getMoves().isEmpty()) {
			// 重置棋盘
			resetChessBoard();
		}
		return this.chessBoard.getMoves();
	}

	/**
	 * 重置棋盘;
	 */
	private void resetChessBoard() {
		// 切换战斗状态
		this.currentState = BattleState.RESETING;
		this.chessBoard.reset();
		// 切换战斗状态
		this.currentState = BattleState.STARTING;
	}

	/**
	 * 把快照广播给客户端
	 * 
	 * @param snaps
	 */
	public void broadcastToBattleUnitsChessBoardSnap(
			List<ChessBoardSnap> snaps, boolean isTriggerByClientMove) {
		GCUpdateChessBoard updateChessBoardMsg = new GCUpdateChessBoard();
		updateChessBoardMsg.setChessBoardSnaps(snaps
				.toArray(new ChessBoardSnap[0]));
		updateChessBoardMsg.setIsTriggerByClientMove(isTriggerByClientMove);
		this.broadcastToBattleUnits(updateChessBoardMsg);
	}

	/**
	 * 当等待客户端动画反馈超时以后直接转入下一个行动;
	 */
	public void onAnimationFeedbackTimeout() {
		// 如果在等待棋盘重置反馈
		this.animationTimeout = null;
		// 如果还有棋盘重置调度, 则先挂起等待反馈,或者超时;
		if (this.resetChessBoardAnimationTimeout != null
				&& !this.resetChessBoardAnimationTimeout.isCanceled()) {
			return;
		}
		// 这里判断是否有角色死亡; 然后做相应的处理;
		this.doClientAnimationOverCheck();
		this.turnToNextUnitAction();
	}

	/**
	 * 是否有单元死亡;
	 * 
	 * @return true 表示有单元死亡并且调用了战斗结束的回调; false 表示没有死亡,且没有调用战斗结束的回调;
	 */
	public boolean isAnyBattleUnitDead() {
		if (this.oneGuy == null) {
			this.onBattleFinished();
			return true;
		}
		if (this.otherGuy == null) {
			this.onBattleFinished();
			return true;
		}
		if (this.oneGuy.isDead() || this.otherGuy.isDead()) {
			this.onBattleFinished();
			return true;
		}
		return false;
	}

	/**
	 * 当接收到客户端的动画播放完成通知;
	 */
	public void onNotifyAnimationOver(IBattleUnit actionUnit) {
		// 先检查;
		doClientAnimationOverCheck();
		// 取消客户端动画反馈调度;
		if (animationTimeout == null) {
			return;
		}
		if (this.animationTimeout.isCanceled()) {
			return;
		}
		if (!this.animationTimeout.allUnitReady(actionUnit)) {
			return;
		}
		this.animationTimeout.cancel();
		// 执行关联的回调; 执行重置棋盘动作;
		IBeforeAnimationFeedbackCallback callback = this.animationTimeout
				.getBeforeAnimationFeedbackCallback();
		if (callback != null) {
			callback.doCall();
		}
		// 判断是否是当前的行动单元;
		// FIXME: crazyjohn 这里有问题啊???不能听客户端的cmd啊
		this.onAnimationFeedbackTimeout();

	}

	/**
	 * 进行客户单动画反馈或者动画反馈超时的检查;
	 */
	private void doClientAnimationOverCheck() {
		// 1. 先判断角色是否死亡; 2. 然后判断是否回合超时;
		// 这里判断是否有角色死亡; 然后做相应的处理;
		if (isAnyBattleUnitDead()) {
			return;
		}
		// 判断是否达到最大回合数
		if (this.battleRoundCounter.isReachMaxBattleRound()) {
			// 回合超时的时候通知新回合开始
			// notifyNewRound();
			this.onRoundout();
		}
	}

	/**
	 * 取消客户端动画反馈调度;
	 */
	private void cancelClientAnimationTimeoutSchedule() {
		if (animationTimeout != null) {
			this.animationTimeout.cancel();
			this.animationTimeout = null;
		}
	}

	/**
	 * 开始行动选择超时计时;
	 * 
	 * @param actionUnit
	 *            当前行动的单元;
	 */
	public void startChooseActionTimeoutSchedule(IBattleUnit actionUnit) {
		chooseActionTimeout = new ChooseActionTimeoutSchedule(this, actionUnit);
		// 增加调度
		this.battleProcessor.scheduleOnece(chooseActionTimeout,
				SharedConstants.BATTLE_ACTION_TIMEOUT);
	}

	/**
	 * 当前行动用户超时;
	 */
	public void onChooseActionTimeout(IBattleUnit actionUnit) {
		// 记录行动超时
		this.battleRoundCounter.recoredActionTimeout(actionUnit);
		GCChooseActionTimeout chooseActionTimeoutMsg = new GCChooseActionTimeout();
		chooseActionTimeoutMsg.setActionUnitGuid(actionUnit.getUnitGuid());
		chooseActionTimeoutMsg
				.setChangeHp(SharedConstants.BATTLE_ACTION_TIMEOUT_DEDUCT_HP);
		this.broadcastToBattleUnits(chooseActionTimeoutMsg);
		// 超时按照策划规则需要扣血
		actionUnit.deductHp(SharedConstants.BATTLE_ACTION_TIMEOUT_DEDUCT_HP);
		actionUnit.finishCurrentAction();
		// 通知客户端扣血
		GCBattleHpChanged hpChangedMsg = new GCBattleHpChanged();
		hpChangedMsg.setTargetId(actionUnit.getUnitGuid());
		hpChangedMsg
				.setChangeHp(SharedConstants.BATTLE_ACTION_TIMEOUT_DEDUCT_HP);
		hpChangedMsg.setChangeType(HpChangedType.OTHER.getIndex());
		actionUnit.getBattleContext().getBattle()
				.broadcastToBattleUnits(hpChangedMsg);
		// 这里判断是否有角色死亡; 然后做相应的处理;
		if (isAnyBattleUnitDead()) {
			return;
		}
		this.afterUnitAction(actionUnit, false);
	}

	// TODO: crazyjohn 此方法需要重构;
	public Collection<GemPosition> getEraseGemsByRange(boolean selected,
			GemPosition selectedGemPos, EraseRangeType eraseRangeType) {
		int col = 0;
		int row = 0;
		// 是否是随机的指定
		if (selected) {
			col = selectedGemPos.getCol();
			row = selectedGemPos.getRow();
		} else {
			col = MathUtils.random(0, SharedConstants.GEM_MAX_ROW - 1);
			row = MathUtils.random(0, SharedConstants.GEM_MAX_COL - 1);
		}
		if (eraseRangeType == EraseRangeType.COL) {
			return this.chessBoard.getGemsByColNum(col);
		} else if (eraseRangeType == EraseRangeType.ROW) {
			return this.chessBoard.getGemsByRowNum(row);
		} else if (eraseRangeType == EraseRangeType.CROSS) {
			return this.chessBoard.getGemsByCrossNum(row, col);
		} else if (eraseRangeType == EraseRangeType.SUDOKU) {
			return this.chessBoard.getGemsBySudokuNum(row, col);
		}
		return null;
	}

	/**
	 * 消除指定位置的宝石, 并且更新棋盘;
	 * 
	 * @param erasableGems
	 */
	public List<ChessBoardSnap> eraseAssignPositionGemsAndUpdateChessBoard(
			Collection<GemPosition> erasableGems) {
		return this.chessBoard
				.eraseAssignPositionGemsAndUpdateChessBoard(erasableGems);
	}

	public Collection<GemPosition> getEraseGemsByGemType(GemType gemTpe) {
		return this.chessBoard.getEraseGemsByGemType(gemTpe);
	}

	/**
	 * 改变宝石颜色;
	 * 
	 * @param srcType
	 *            原来的颜色;
	 * @param desType
	 *            目标颜色;
	 * @return
	 */
	public List<ChessBoardSnap> changeGemColor(GemType srcType, GemType desType) {
		return this.chessBoard.changeGemColor(srcType, desType);
	}

	/**
	 * 添加回合监听器;
	 * 
	 * @param listener
	 */
	public void addRoundListener(IRoundListener listener) {
		this.battleRoundCounter.addRoundListener(listener);
	}

	/**
	 * 移除回合监听器;
	 * 
	 * @param listener
	 */
	public void removeRoundListener(IRoundListener listener) {
		this.battleRoundCounter.removeRoundListener(listener);
	}

	public void registerBattleFinishCallback(
			IBattleCallback battleFinishCallback) {
		this.battleCallback = battleFinishCallback;
	}

	/**
	 * 为战斗单元选择一此行动;
	 * 
	 * @param human
	 */
	public void chooseActionForUnit(Human human) {
		human.doBattleAction();
	}

	/**
	 * 为战斗单元添加一次额外行动机会;
	 * 
	 * @param unit
	 */
	public void addNewRoundToUnit(IBattleUnit unit) {
		this.battleActionScheduleCenter.addActionTimes(unit);
		// editby: crazyjohn 注释掉这里使用 BattleActionScheduleCenter的逻辑;
		// 在添加额外回合的时候记录行动单元;相当于记录对方无法行动;
		// IBattleUnit canNotActionUnit = ((unit == this.oneGuy) ? this.otherGuy
		// : this.oneGuy);
		// this.battleRoundCounter.recoredCanNotAction(canNotActionUnit);
	}

	/**
	 * 本次战斗结束的回调;
	 */
	@NotThreadSafe
	public void onBattleFinished() {
		// 如果已经结束了直接退出;
		if (this.currentState == BattleState.FINISHED) {
			return;
		}
		// 取消所有调度;
		cancelAllSchedule();
		this.currentState = BattleState.FINISHED;
		// 添加一个场景调度
		GameServerAssist.getGameWorld().putMessage(new SysInternalMessage() {
			@Override
			public void execute() {
				executeBttleFinishedCallback();
			}

		});

	}

	/**
	 * 回合达到最大回合数的处理;
	 */
	public void onRoundout() {
		// 如果是PVE就直接失败; 如果PVP则挑战者失败;
		this.battleCallback.onBattleRoundout();
		this.onBattleFinished();

	}

	/**
	 * 战斗过程中有战斗单元退出;<br>
	 * 此方法一定要保证由场景线程调度;
	 * 
	 * @param unit
	 */
	public void onBattleUnitQuit(IBattleUnit unit) {
		// 战斗中如果有玩家退出(比如掉线之类的);退出的人都按照战斗失败处理;
		whoQuitIsLoser(unit);
		// 如果已经结束了直接退出;
		if (this.currentState == BattleState.FINISHED) {
			return;
		}
		// 取消所有调度;
		cancelAllSchedule();
		this.currentState = BattleState.FINISHED;
		// 执行战斗完成回调;
		executeBttleFinishedCallback();

	}

	/**
	 * 执行战斗完成回调;
	 */
	private void executeBttleFinishedCallback() {
		// 取消超时调度;
		if (chooseActionTimeout != null) {
			chooseActionTimeout.cancel();
			chooseActionTimeout = null;
		}
		// 调用callback
		battleCallback.onBattleFinished();
		oneGuy.onExitBattle();
		otherGuy.onExitBattle();
		battleCallback.onBattleExited();
		// 需要移除掉战斗管理器中的此次战斗;
		GameServerAssist.getBattleManager().removeBattle(Battle.this);
	}

	/**
	 * 取消所有调度;
	 */
	private void cancelAllSchedule() {
		this.cancelClientAnimationTimeoutSchedule();
		this.cancelClientChooseActionTimeoutSchedule();
		if (this.resetChessBoardAnimationTimeout != null) {
			this.resetChessBoardAnimationTimeout.cancel();
			this.resetChessBoardAnimationTimeout = null;
		}
	}

	/**
	 * 谁退出谁就输;
	 * 
	 * @param unit
	 */
	private void whoQuitIsLoser(IBattleUnit unit) {
		this.battleCallback.middleQuitGuy(unit);
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("Battle: ").append(System.identityHashCode(this))
				.append(", oneGuy: ").append(this.oneGuy.getUnitName())
				.append("- otherGuy: ").append(this.otherGuy.getUnitName())
				.append("\n");
		builder.append(this.chessBoard.toString());
		return builder.toString();
	}

	/**
	 * 调试使用, 战斗因棋盘不同步暂停;
	 * 
	 * @param unit
	 */
	public void pauseForInvalidMove(Human unit) {
		this.cancelAllSchedule();
	}

	/**
	 * 获取战斗棋盘中只能宝石类型的数目;
	 * 
	 * @param energyType
	 * @return
	 */
	public int getChessBoardGemCounts(GemType type) {
		return this.chessBoard.getGemCountByType(type);
	}

	public List<ChessBoardSnap> generateGemByType(GemType desType, int count) {
		return this.chessBoard.generateGemByType(desType, count);
	}

	public int getLastChangeGemColorCount() {
		return this.chessBoard.getLastChangeGemColorCount();
	}

	public IBattleRoundCounter getBattleRoundCounter() {
		return this.battleRoundCounter;
	}

	/**
	 * 战斗单元就绪;
	 * 
	 * @param human
	 */
	public void unitReady(IBattleUnit unit) {
		// 判断战斗类型
		if (isPVEBattle()) {
			this.isOneReady = true;
			this.isOtherReady = true;
		} else {
			if (unit == this.oneGuy) {
				this.isOneReady = true;
				if (!isOtherReady) {
					// 发送等待对方准备好的消息
					GCBattleWaitingOtherUnitReady readyMsg = new GCBattleWaitingOtherUnitReady();
					oneGuy.sendMessage(readyMsg);
					// 添加超时调度
					addStartBattleSchedule();
					// 设置网络环境差的玩家
					this.badNetGuy = this.otherGuy;
				}
			} else {
				this.isOtherReady = true;
				if (!isOneReady) {
					// 发送等待对方准备好的消息
					GCBattleWaitingOtherUnitReady readyMsg = new GCBattleWaitingOtherUnitReady();
					otherGuy.sendMessage(readyMsg);
					// 添加超时调度
					addStartBattleSchedule();
					// 设置网络环境差的玩家
					this.badNetGuy = this.oneGuy;
				}
			}
		}
	}

	/**
	 * 开始等待战斗开始调度;
	 */
	private void addStartBattleSchedule() {
		WaitingBattleStartSchedule waitStart = new WaitingBattleStartSchedule(
				this);
		this.registerWaitStartSchedule(waitStart);
		sceneManager.scheduleOnece(waitStart, 60 * 1000/** TODO: crazyjohn 参数化 */
		);
	}

	/**
	 * 是否pve战斗;
	 * 
	 * @return
	 */
	private boolean isPVEBattle() {
		// 跟镜像打也认为是pve;
		return this.battleCallback.getBattleType().isPVE()
				|| this.battleWithGuarder;
	}

	/**
	 * 所有单元都就绪;
	 * 
	 * @return
	 */
	public boolean allUnitReady() {
		return this.isOneReady && this.isOtherReady;
	}

	public void setBattleWithGuarder() {
		this.battleWithGuarder = true;
	}

	/**
	 * 等待战斗从init-start的切换超时;
	 */
	public void waitingBattleStartTimeout() {
		// 双方都退出战斗状态;
		// FIXME: crazyjohn 这里现在的处理方式是直接退出到主场景, 这样处理对用户来说很糙, 需要根据策划要求进行细化;
		oneGuy.onExitBattle();
		otherGuy.onExitBattle();

		// 通知网络环境差
		GCBadNetEnvironment badMsg = new GCBadNetEnvironment();
		badMsg.setBadGuid(this.badNetGuy.getUnitGuid());
		this.broadcastToBattleUnits(badMsg);
		// 需要移除掉战斗管理器中的此次战斗;
		GameServerAssist.getBattleManager().removeBattle(Battle.this);
		// 通知回调超时了
		this.battleCallback.onEnterBattleFailed();
	}

	public void registerWaitStartSchedule(WaitingBattleStartSchedule waitStart) {
		this.waitStartSchedule = waitStart;
	}

	/**
	 * 战斗单元请求托管战斗;
	 * 
	 * @param human
	 */
	public void unitRequestHostingBattle(Human human) {
		// 是否可以进行状态切换;
		if (!human.getPlayer().canTransferStateTo(PlayerState.HOSTING_BATTLING)) {
			return;
		}
		human.getPlayer().transferStateTo(PlayerState.HOSTING_BATTLING);
		GCStartHostingBattle hostingBattle = new GCStartHostingBattle();
		hostingBattle.setBeHostedGuid(human.getHumanGuid());
		this.broadcastToBattleUnits(hostingBattle);
		// 如果是自己回合就開始行動
		if (this.isThisUnitTurn(human)) {
			human.getBattleAI().action(
					SharedConstants.BATTLE_MONSTER_THINK_TIMES);
		}
	}

	/**
	 * 战斗单元请求取消托管状态;
	 * 
	 * @param human
	 */
	public void unitRequestCancelHostingBattle(Human human) {
		// 是否可以进行状态切换;
		if (!human.getPlayer().canTransferStateTo(PlayerState.BATTLING)) {
			return;
		}
		human.getPlayer().transferStateTo(PlayerState.BATTLING);
		GCCancelHostingBattleSucceed cancelHostingBattle = new GCCancelHostingBattleSucceed();
		cancelHostingBattle.setBeHostedGuid(human.getHumanGuid());
		this.broadcastToBattleUnits(cancelHostingBattle);
	}

	/**
	 * 是否正在进行战斗引导
	 * 
	 * @return
	 */
	public boolean isGuideing() {
		return isGuideing;
	}

	/**
	 * 设置是否正在进行战斗引导
	 * 
	 * @param isGuideing
	 */
	public void setIsGuideing(boolean isGuideing) {
		this.isGuideing = isGuideing;
	}

	/**
	 * 可以导致四连消的操作
	 * 
	 * @return
	 */
	public List<Move> getFourBombsMoves() {
		return this.chessBoard.getFourBombsMoves();
	}

	/**
	 * 获取棋盘某位置的宝石
	 * 
	 * @param row
	 * @param col
	 * @return
	 */
	public GemObject getGemObject(int row, int col) {
		return this.chessBoard.getGemObject(row, col);
	}

	/**
	 * 是否只有一个人是活人;
	 * 
	 * @return
	 */
	public boolean isOneGuyFeedBackBattle() {
		if ((this.oneGuy instanceof Human) && (this.otherGuy instanceof Human)) {
			return false;
		}
		return true;
	}

}
