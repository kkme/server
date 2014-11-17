package com.hifun.soul.gameserver.battle.gem;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.hifun.soul.common.constants.SharedConstants;
import com.hifun.soul.gameserver.battle.gem.function.IFunctionGems;

/**
 * 基本的搜索可擦除的宝石的策略;<br>
 * 来自于dick的客户端的蛮力算法;<br>
 * FIXME: crazyjohn badsmell 重复代码过多, 需要重构;
 * @author crazyjohn
 * 
 */
public class CommonSearchErasableGemStrategty implements
		ISearchErasableGemStrategy {
	/** 棋盘 */
	GemChessBoard chessBoard;
	/** 上次搜索到得连击次数 */
	private int eraseCount;
	private boolean isTriggerNewRound;
	private List<IFunctionGems> functionGemsList = new ArrayList<IFunctionGems>();

	public CommonSearchErasableGemStrategty(GemChessBoard chessBoard) {
		this.chessBoard = chessBoard;
	}

	/**
	 * 获取上次搜索到得可消除的数目;
	 * 
	 * @return
	 */
	@Override
	public int getEraseCount() {
		return this.eraseCount;
	}

	@Override
	public Collection<GemPosition> searchErasableGems() {
		// 先把消除数量置空;
		this.eraseCount = 0;
		this.isTriggerNewRound = false;
		functionGemsList.clear();
		Map<Long, GemPosition> result = new HashMap<Long, GemPosition>();
		// 横向相同的宝石
		for (int i = 0; i < chessBoard.maxRow; i++) {
			int sameCount = 0;
			for (int j = 0; j < chessBoard.maxCol; j++) {
				while (true) {
					if ((j + 1) < chessBoard.maxCol) {
						if (chessBoard.gems[i][j].getType() == chessBoard.gems[i][j + 1]
								.getType()) {
							sameCount++;
							j++;
							continue;
						}

					}
					if (sameCount < GemChessBoard.CAN_ERASE) {
						sameCount = 0;
						break;
					}
					if (sameCount >= GemChessBoard.CAN_ERASE) {
						// 消除增加
						eraseCount++;
						// 如果相同数量大于指定的值，则当前行动玩家，多一回合
						if (sameCount >= SharedConstants.ADDED_ROUND_COUNT) {
							this.isTriggerNewRound = true;
						}
						GemType type = chessBoard.gems[i][j - sameCount].getType();
						// 创建消除function
						IFunctionGems func = type.createFunction();
						if (func != null) {
							this.getCurrentSnapFunctionGems().add(func);
						}
						for (; sameCount >= 0; sameCount--) {
							GemPosition pos = new GemPosition();
							pos.setRow(i);
							pos.setCol(j - sameCount);
							pos.setType(chessBoard.gems[i][j - sameCount]
									.getType().getIndex());
							result.put(
									chessBoard.gems[i][j - sameCount].getId(),
									pos);
						}

						break;
					}
				}
			}
		}
		// 纵向相同的宝石
		for (int i = 0; i < chessBoard.maxCol; i++) {
			int sameCount = 0;
			for (int j = 0; j < chessBoard.maxRow; j++) {
				while (true) {
					if (j < chessBoard.maxRow - 1
							&& chessBoard.gems[j][i].getType() == chessBoard.gems[j + 1][i]
									.getType()) {
						sameCount++;
						j++;
						continue;
					}
					if (sameCount < GemChessBoard.CAN_ERASE) {
						sameCount = 0;
						break;
					}
					if (sameCount >= GemChessBoard.CAN_ERASE) {
						// 消除增加
						eraseCount++;
						// 如果相同数量大于指定的值，则当前行动玩家，多一回合
						if (sameCount >= SharedConstants.ADDED_ROUND_COUNT) {
							this.isTriggerNewRound = true;
						}
						GemType type = chessBoard.gems[j - sameCount][i].getType();
						// 创建消除function
						IFunctionGems func = type.createFunction();
						if (func != null) {
							this.getCurrentSnapFunctionGems().add(func);
						}
						for (; sameCount >= 0; sameCount--) {
							GemPosition pos = new GemPosition();
							pos.setRow(j - sameCount);
							pos.setCol(i);
							pos.setType(chessBoard.gems[j - sameCount][i]
									.getType().getIndex());
							result.put(
									chessBoard.gems[j - sameCount][i].getId(),
									pos);
						}
						break;
					}

				}
			}
		}
		return result.values();
	}

	@Override
	public boolean isTriggerNewRound() {
		return isTriggerNewRound;
	}

	@Override
	public List<IFunctionGems> getCurrentSnapFunctionGems() {
		return this.functionGemsList;
	}

}
