package com.hifun.soul.gameserver.battle.gem;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.hifun.soul.core.util.MathUtils;
import com.hifun.soul.gameserver.battle.Battle;
import com.hifun.soul.gameserver.battle.BattleState;
import com.hifun.soul.gameserver.battle.msg.ChessBoardSnap;
import com.hifun.soul.gameserver.battle.msg.GCChangeGemsColor;

/**
 * 宝石棋盘;
 * 
 * @author crazyjohn
 * 
 */
public class GemChessBoard {
	private static Logger logger = LoggerFactory.getLogger(GemChessBoard.class);
	protected static final int CAN_ERASE = 2;
	protected GemObject[][] gems;
	protected int maxCol;
	protected int maxRow;
	/** 宝石生成器 */
	protected IGemGenerator gemGenarator = new GemRandomGenerator();
	private GemObject selectedGem;
	private static int eraseCounter = 0;
	/** 搜索可消除宝石的策略 */
	private ISearchErasableGemStrategy searchStrategy = new CommonSearchErasableGemStrategty(
			this);
	/** 战斗宿主 */
	private Battle battleHost;
	/** 上次行动的连击数 */
	private int lastActionCombo;
	/** 寻找Move的策略 */
	private ISearchMoveStrategy searchMoveStrategy = new CommonSearchMoveStrategy(
			this);
	/** 最后一次改变宝石颜色的个数 */
	private int lastChangeColorGemCount;

	public GemChessBoard(int maxRow, int maxCol, Battle battleHost) {
		this.maxCol = maxCol;
		this.maxRow = maxRow;
		this.gems = new GemObject[this.maxRow][this.maxCol];
		this.battleHost = battleHost;
	}

	public void setHostBattle(Battle hostBattle) {
		this.battleHost = hostBattle;
	}

	/**
	 * 初始化棋盘;
	 */
	public void initChessBoard() {
		// 先清理棋盘
		clearChessBoard();
		for (int i = 0; i < maxRow; i++) {
			for (int j = 0; j < maxCol; j++) {
				this.gems[i][j] = gemGenarator.generateOneGemObject();
				this.gems[i][j].setColumn(j);
				this.gems[i][j].setRow(i);
			}
		}
		if (logger.isDebugEnabled()) {
			logger.debug("ChessBoard init snap: " + toString());
		}
		// 更新棋盘
		updateChessBoard(false);
		// editby:crazyjohn 2014-05-07解决战斗刚开始的时候会出现无子可消的情况;
		while (this.getMoves().size() == 0) {
			initChessBoard();
		}
	}

	/**
	 * 更新棋盘;
	 */
	public List<ChessBoardSnap> updateChessBoard(boolean isTriggerByClientMove) {
		lastActionCombo = 0;
		List<ChessBoardSnap> snaps = new ArrayList<ChessBoardSnap>();
		Collection<GemPosition> erasableGems = null;
		// 当前快照是否触发四连消
		boolean isTriggerNewRound = false;
		while ((erasableGems = this.searchErasableGems()).size() != 0) {
			// 获取搜索到得消除条目数
			if (isTriggerByClientMove) {
				lastActionCombo += this.searchStrategy.getEraseCount();
			}
			Map<Integer, List<GemType>> generatedGems = eraseAndFillBlankPosition(erasableGems);
			// 设置快照
			ChessBoardSnap snap = buildSnap(erasableGems, generatedGems);
			snap.setEraseCount(this.searchStrategy.getEraseCount());
			// 指定快照四连消逻辑
			if (!isTriggerNewRound) {
				snap.setTriggerNewRound(this.searchStrategy.isTriggerNewRound());
				isTriggerNewRound = this.searchStrategy.isTriggerNewRound();
			}
			
			snap.addFunctionGemsList(this.searchStrategy
					.getCurrentSnapFunctionGems());
			logger.debug("Snap: " + snap);
			snaps.add(snap);

		}
		// 整体全部广播给客户端, 防止网络延时;
		if (battleHost != null
				&& this.battleHost.getCurrentState() == BattleState.STARTING) {
			battleHost.broadcastToBattleUnitsChessBoardSnap(snaps,
					isTriggerByClientMove);
		}
		// 每一帧的快照
		if (logger.isDebugEnabled()) {
			logger.debug("ChessBoard snap: " + toString());
		}
		return snaps;
	}

	/**
	 * 构建快照;
	 * 
	 * @param erasableGems
	 * @param generatedGems
	 * @return
	 */
	public ChessBoardSnap buildSnap(Collection<GemPosition> erasableGems,
			Map<Integer, List<GemType>> generatedGems) {
		// 设置快照
		ChessBoardSnap snap = new ChessBoardSnap();
		List<GemPosition> positions = new ArrayList<GemPosition>();
		positions.addAll(erasableGems);
		snap.setErasableGems(positions.toArray(new GemPosition[0]));
		List<ColNewGems> newGemsList = new ArrayList<ColNewGems>();
		for (Map.Entry<Integer, List<GemType>> entry : generatedGems.entrySet()) {
			// 空的列就不发了
			if (entry.getValue().size() == 0) {
				continue;
			}
			ColNewGems colNewGems = new ColNewGems();
			colNewGems.setCol(entry.getKey());
			int index = 0;
			int[] types = new int[entry.getValue().size()];
			for (GemType type : entry.getValue()) {
				types[index] = type.getIndex();
				index++;
			}
			colNewGems.setGems(types);
			newGemsList.add(colNewGems);
		}
		snap.setGeneratedGems(newGemsList.toArray(new ColNewGems[0]));
		return snap;
	}

	/**
	 * 清空棋盘;
	 */
	private void clearChessBoard() {
		for (int i = 0; i < maxRow; i++) {
			for (int j = 0; j < maxCol; j++) {
				this.gems[i][j] = null;
			}
		}
	}

	/**
	 * 消除需要消除的宝石, 然后对空位进行填充;
	 * 
	 * @param erasableGems
	 * @return 返回新生成的宝石;
	 */
	public Map<Integer, List<GemType>> eraseAndFillBlankPosition(
			Collection<GemPosition> erasableGems) {
		eraseCounter++;
		// 清空需要消除的宝石位
		for (GemPosition eachGem : erasableGems) {
			// 指定宝石置为空;
			this.gems[eachGem.getRow()][eachGem.getCol()] = null;
		}
		return fillBlankChessBoardPosition();
	}

	/**
	 * 填充空白的棋盘位置;
	 * 
	 * @return
	 */
	private Map<Integer, List<GemType>> fillBlankChessBoardPosition() {
		if (logger.isDebugEnabled()) {
			logger.debug("Before fill: " + this.toString());
		}
		Map<Integer, List<GemType>> generatedGemCols = new HashMap<Integer, List<GemType>>();
		// 填充算法
		for (int i = 0; i < this.maxCol; i++) {
			int nullCount = 0;
			int row = 0;
			for (int j = 0; j < this.maxRow; j++) {
				if (this.gems[j][i] == null) {
					if (j >= row) {
						row = j;
						nullCount++;
					}
				}
			}
			List<GemType> rolGems = new ArrayList<GemType>();
			// 下落
			if (nullCount > 0) {
				for (int k = row; k >= 0 && nullCount <= this.maxRow; k--) {
					if (this.gems[k][i] == null) {
						int tempRow = k - 1;
						while (tempRow >= 0) {
							if (this.gems[tempRow][i] == null) {
								tempRow--;
							} else {
								this.gems[k][i] = this.gems[tempRow][i];
								this.gems[k][i].setRow(k);
								this.gems[tempRow][i] = null;
								break;
							}
						}
					}
				}
			}
			// 填充
			while (nullCount > 0) {
				GemObject gem = this.gemGenarator.generateOneGemObject();
				this.gems[nullCount - 1][i] = gem;
				gem.setColumn(i);
				gem.setRow(nullCount - 1);
				nullCount--;
				// 添加到列里
				rolGems.add(gem.getType());
			}
			// 添加到集合里
			generatedGemCols.put(i, rolGems);
		}
		if (logger.isDebugEnabled()) {
			logger.debug("End fill: " + this.toString());
		}
		return generatedGemCols;
	}

	public int getMaxRows() {
		return this.maxRow;
	}

	public int getMaxColumns() {
		return this.maxCol;
	}

	/**
	 * 搜寻可以擦除的宝石;
	 * 
	 * @return 返回可以消除的宝石;
	 */
	protected Collection<GemPosition> searchErasableGems() {
		return searchStrategy.searchErasableGems();
	}

	public GemObject getSelectedGem() {
		return selectedGem;
	}

	/**
	 * 交换宝石;必须保证要执行的是正确的移动;
	 * 
	 * @param row1
	 * @param col1
	 * @param row2
	 * @param col2
	 * @return
	 */
	protected boolean swapGem(int row1, int col1, int row2, int col2) {
		// 是否相邻;
		if (!isAdjacent(row1, col1, row2, col2)) {
			return false;
		}
		doSwap(row1, col1, row2, col2);
		logger.debug("After swap: " + this.toString());
		// 不可交换
		if (this.searchErasableGems().isEmpty()) {
			doSwap(row1, col1, row2, col2);
			return false;
		} else {

			return true;
		}
	}

	private void doSwap(int row1, int col1, int row2, int col2) {
		GemObject one = this.gems[row1][col1];
		GemObject two = this.gems[row2][col2];
		logger.debug("Before Swap: one==" + this.gems[row1][col1] + ", two=="
				+ this.gems[row2][col2]);
		this.gems[row1][col1] = two;
		this.gems[row1][col1].setRow(row1);
		this.gems[row1][col1].setColumn(col1);
		this.gems[row2][col2] = one;
		this.gems[row2][col2].setRow(row2);
		this.gems[row2][col2].setColumn(col2);
		// logger.info("After Swap: one==" + this.gems[row1][col1] + ", two=="
		// + this.gems[row2][col2]);
	}

	@Deprecated
	public void doSwap(GemObject one, GemObject other) {
		GemObject temp = one;
		int row1 = one.getRow();
		int col1 = one.getColumn();
		GemType type1 = one.getType();
		int row2 = other.getRow();
		int col2 = other.getColumn();
		GemType type2 = other.getType();
		this.gems[row1][col1].setType(type2);
		other.setColumn(col1);
		other.setRow(row1);
		this.gems[row2][col2].setType(type1);
		this.gems[row2][col2].setRow(temp.getRow());
		this.gems[row2][col2].setColumn(temp.getColumn());
	}

	protected GemObject selectGemObject(int row, int col) {
		this.selectedGem = this.gems[row][col];
		return this.selectedGem;

	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("Current eraseCounter: " + eraseCounter + "\n");
		for (int i = 0; i < this.getMaxRows(); i++) {
			for (int j = 0; j < this.getMaxColumns(); j++) {
				if (this.gems[i][j] == null) {
					sb.append("空" + "  ");
					continue;
				}
				sb.append(this.gems[i][j].getType().getDesc() + "  ");
			}
			sb.append("\n");
		}
		return sb.toString();
	}

	/**
	 * 判断宝石是否相邻;
	 * 
	 * @param row1
	 * @param col1
	 * @param row2
	 * @param col2
	 * @return
	 */
	private boolean isAdjacent(int row1, int col1, int row2, int col2) {
		if (Math.abs(col1 - col2) + Math.abs(row1 - row2) != 1) {
			return false;
		}
		return true;
	}

	/**
	 * 尝试交换宝石, 交换成功以后更新棋盘;
	 * 
	 * @param row1
	 * @param col1
	 * @param row2
	 * @param col2
	 * @return 返回true表示宝石交换成功;
	 */
	public List<ChessBoardSnap> trySwapGemAndUpdateChessBoard(int row1,
			int col1, int row2, int col2) {
		if (!this.swapGem(row1, col1, row2, col2)) {
			return new ArrayList<ChessBoardSnap>();
		}
		// 更新棋盘
		return updateChessBoard(true);
	}

	/**
	 * 获取最后一次行动所引发的连击次数;
	 * 
	 * @return
	 */
	public int getLastActionCombo() {
		return this.lastActionCombo;
	}

	/**
	 * 获取所有可选的{@code Move};<br>
	 * 
	 * @return 不会返回NULL;
	 */
	public List<Move> getMoves() {
		return searchMoveStrategy.getMoves();
	}
	
	/**
	 * 直接可以导致四连消的移动，不考虑combo造成的四连
	 * @return
	 */
	public List<Move> getFourBombsMoves() {
		return searchMoveStrategy.getFourBombsMoves();
	}

	/**
	 * 重置棋盘;
	 */
	public void reset() {
		// 直到可以移动的move不为空
		while (this.getMoves().isEmpty()) {
			initChessBoard();
		}
	}

	/**
	 * 获取棋盘所有列的宝石类型信息;
	 * 
	 * @return
	 */
	public List<ColNewGems> getAllColGems() {
		List<ColNewGems> gemsList = new ArrayList<ColNewGems>();
		for (int i = 0; i < this.maxCol; i++) {
			ColNewGems colGems = new ColNewGems();
			colGems.setCol(i);
			int[] types = new int[this.maxRow];
			int index = 0;
			for (int j = 0; j < this.maxRow; j++) {
				types[index] = this.gems[j][i].getType().getIndex();
				index++;
			}
			colGems.setGems(types);
			gemsList.add(colGems);
		}
		return gemsList;
	}

	public Collection<GemPosition> getGemsByColNum(int col) {
		List<GemPosition> result = new ArrayList<GemPosition>();
		for (int i = 0; i < this.maxRow; i++) {
			GemPosition pos = new GemPosition();
			pos.setCol(col);
			pos.setRow(i);
			pos.setType(this.gems[i][col].getType().getIndex());
			result.add(pos);
		}
		return result;
	}

	public Collection<GemPosition> getGemsByRowNum(int row) {
		List<GemPosition> result = new ArrayList<GemPosition>();
		for (int i = 0; i < this.maxCol; i++) {
			GemPosition pos = new GemPosition();
			pos.setCol(i);
			pos.setRow(row);
			pos.setType(this.gems[row][i].getType().getIndex());
			result.add(pos);
		}
		return result;
	}

	public Collection<GemPosition> getGemsByCrossNum(int row, int col) {
		List<GemPosition> result = new ArrayList<GemPosition>();
		if ((row + 1) < this.maxRow) {
			GemPosition pos = new GemPosition();
			pos.setCol(col);
			pos.setRow(row + 1);
			pos.setType(this.gems[row + 1][col].getType().getIndex());
			result.add(pos);
		}
		if ((row - 1) >= 0) {
			GemPosition pos = new GemPosition();
			pos.setCol(col);
			pos.setRow(row - 1);
			pos.setType(this.gems[row - 1][col].getType().getIndex());
			result.add(pos);
		}
		if ((col - 1) >= 0) {
			GemPosition pos = new GemPosition();
			pos.setCol(col - 1);
			pos.setRow(row);
			pos.setType(this.gems[row][col - 1].getType().getIndex());
			result.add(pos);
		}
		if ((col + 1) < this.maxCol) {
			GemPosition pos = new GemPosition();
			pos.setCol(col + 1);
			pos.setRow(row);
			pos.setType(this.gems[row][col + 1].getType().getIndex());
			result.add(pos);
		}
		// 添加自己
		GemPosition pos = new GemPosition();
		pos.setCol(col);
		pos.setRow(row);
		pos.setType(this.gems[row][col].getType().getIndex());
		result.add(pos);
		return result;
	}

	public Collection<GemPosition> getGemsBySudokuNum(int row, int col) {
		// 先获取十字
		Collection<GemPosition> result = this.getGemsByCrossNum(row, col);
		if ((row + 1) < this.maxRow && (col + 1) < this.maxCol) {
			GemPosition pos = new GemPosition();
			pos.setCol(col + 1);
			pos.setRow(row + 1);
			pos.setType(this.gems[row + 1][col + 1].getType().getIndex());
			result.add(pos);
		}
		if ((row - 1) >= 0 && (col - 1) >= 0) {
			GemPosition pos = new GemPosition();
			pos.setCol(col - 1);
			pos.setRow(row - 1);
			pos.setType(this.gems[row - 1][col - 1].getType().getIndex());
			result.add(pos);
		}
		if ((col - 1) >= 0 && (row + 1) < this.maxRow) {
			GemPosition pos = new GemPosition();
			pos.setCol(col - 1);
			pos.setRow(row + 1);
			pos.setType(this.gems[row + 1][col - 1].getType().getIndex());
			result.add(pos);
		}
		if ((col + 1) < this.maxCol && (row - 1) >= 0) {
			GemPosition pos = new GemPosition();
			pos.setCol(col + 1);
			pos.setRow(row - 1);
			pos.setType(this.gems[row - 1][col + 1].getType().getIndex());
			result.add(pos);
		}
		// 添加自己
//		GemPosition self = new GemPosition();
//		self.setCol(col);
//		self.setRow(row);
//		self.setType(this.gems[row][col].getType().getIndex());
//		result.add(self);
		return result;
	}

	/**
	 * 消除指定的宝石, 并且更新棋盘;
	 * 
	 * @param erasableGems
	 * @return
	 */
	public List<ChessBoardSnap> eraseAssignPositionGemsAndUpdateChessBoard(
			Collection<GemPosition> erasableGems) {
		List<ChessBoardSnap> snaps = new ArrayList<ChessBoardSnap>();
		// 构建本次消除的快照
		Map<Integer, List<GemType>> generatedGems = this
				.eraseAndFillBlankPosition(erasableGems);
		ChessBoardSnap snap = this.buildSnap(erasableGems, generatedGems);
		snaps.add(snap);
		// 发送本次消除的快照;
		if (this.battleHost != null
				&& this.battleHost.getCurrentState() == BattleState.STARTING) {
			this.battleHost.broadcastToBattleUnitsChessBoardSnap(snaps, false);
		}
		// 更新棋盘
		snaps.addAll(this.updateChessBoard(false));
		return snaps;
	}

	/**
	 * 根据宝石类型获取宝石;
	 * 
	 * @param gemType
	 * @return
	 */
	public Collection<GemPosition> getEraseGemsByGemType(GemType gemType) {
		List<GemPosition> result = new ArrayList<GemPosition>();
		for (int i = 0; i < this.maxRow; i++) {
			for (int j = 0; j < this.maxCol; j++) {
				if (this.gems[i][j].getType() == gemType) {
					GemPosition pos = new GemPosition();
					pos.setRow(i);
					pos.setCol(j);
					pos.setType(gemType.getIndex());
					result.add(pos);
				}
			}
		}
		return result;
	}

	/**
	 * 获取棋盘中某种指定颜色宝石的数目;
	 * 
	 * @param type
	 * @return
	 */
	public int getGemCountByType(GemType type) {
		int count = 0;
		for (int i = 0; i < maxRow; i++) {
			for (int j = 0; j < maxCol; j++) {
				if (this.gems[i][j].getType() == type) {
					count++;
				}
			}
		}
		return count;
	}

	/**
	 * 改变棋盘中宝石的颜色;
	 * 
	 * @param srcType
	 *            源颜色;
	 * @param desType
	 *            目标颜色;
	 */
	public List<ChessBoardSnap> changeGemColor(GemType srcType, GemType desType) {
		this.lastChangeColorGemCount = 0;
		List<GemPosition> result = new ArrayList<GemPosition>();
		for (int i = 0; i < this.maxCol; i++) {
			for (int j = 0; j < this.maxRow; j++) {
				if (this.gems[j][i].getType() == srcType) {
					this.gems[j][i].setType(desType);
					GemPosition pos = new GemPosition();
					pos.setCol(i);
					pos.setRow(j);
					pos.setType(desType.getIndex());
					result.add(pos);
				}
			}
		}
		if (this.battleHost != null
				&& this.battleHost.getCurrentState() == BattleState.STARTING) {
			// 广播棋盘颜色改变消息;
			GCChangeGemsColor changColorMsg = new GCChangeGemsColor();
			changColorMsg.setChangedGems(result.toArray(new GemPosition[0]));
			this.battleHost.broadcastToBattleUnits(changColorMsg);
			lastChangeColorGemCount = result.size();
		}
		return this.updateChessBoard(false);
	}

	/**
	 * 根据宝石类型,在棋盘随机生成指定数目的该类型宝石;
	 * 
	 * @param desType
	 * @param count
	 * @return
	 */
	public List<ChessBoardSnap> generateGemByType(GemType desType, int count) {
		List<GemPosition> result = randomSelectGems(count);
		for (GemPosition pos : result) {
			GemObject gem = this.gems[pos.getRow()][pos.getCol()];
			gem.setType(desType);
			pos.setType(desType.getIndex());
		}
		if (this.battleHost != null
				&& this.battleHost.getCurrentState() == BattleState.STARTING) {
			// 广播棋盘颜色改变消息;
			GCChangeGemsColor changColorMsg = new GCChangeGemsColor();
			changColorMsg.setChangedGems(result.toArray(new GemPosition[0]));
			this.battleHost.broadcastToBattleUnits(changColorMsg);
		}
		return this.updateChessBoard(false);
	}

	protected GemObject randomSelectGem() {
		GemObject[] col = MathUtils.randomFromArray(this.gems);
		GemObject obj = MathUtils.randomFromArray(col);
		return obj;
	}

	/**
	 * 随机选择指定数据的宝石;<br>
	 * 使用时空权衡, 算法先对于其他算法效率较高;<br>
	 * FIXME: crazyjohn 可以进行优化, 不必每次都进行创建;<br>
	 * @param count
	 * @return
	 */
	public List<GemPosition> randomSelectGems(int count) {
		List<GemPosition> result = new ArrayList<GemPosition>(count);
		Map<Integer, LinkedList<Integer>> positions = new HashMap<Integer, LinkedList<Integer>>();
		for (int i = 0; i < this.maxRow; i++) {
			LinkedList<Integer> row = new LinkedList<Integer>();
			for (int j = 0; j < this.maxCol; j++) {
				row.add(j);
			}
			positions.put(i, row);
		}
		// 初始化行号;
		LinkedList<Integer> rowNums = new LinkedList<Integer>();
		for (int i = 0; i < this.maxRow; i++) {
			rowNums.add(i);
		}
		// 开始随机指定数目的位置;
		for (int i = 0; i < count; i++) {
			Integer row = MathUtils.randomFromArray(rowNums.toArray(new Integer[0]));
			LinkedList<Integer> rowElements = positions.get(row);
			while (rowElements.isEmpty()) {
				// 移除指定行
				positions.remove(row);
				// 移除行号
				rowNums.remove(row);
				// 随机新的行号
				row = MathUtils.randomFromArray(rowNums.toArray(new Integer[0]));
				// 获取新的行
				rowElements = positions.get(row);
			}
			// 获取新的列号
			Integer col = MathUtils.randomFromArray(rowElements.toArray(new Integer[0]));
			GemPosition pos = new GemPosition();
			pos.setRow(row);
			pos.setCol(col);
			// 移除指定列号
			rowElements.remove(col);
			result.add(pos);
		}
		return result;
	}

	public int getLastChangeGemColorCount() {
		return lastChangeColorGemCount;
	}
	
	/**
	 * 获取某个位置的宝石
	 * @param row
	 * @param col
	 * @return
	 */
	public GemObject getGemObject(int row, int col) {
		if(row >= getMaxRows()
				|| col >= getMaxColumns()){
			return null;
		}
		return gems[row][col];
	}
}
