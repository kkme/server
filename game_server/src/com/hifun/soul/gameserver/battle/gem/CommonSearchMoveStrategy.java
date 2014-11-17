package com.hifun.soul.gameserver.battle.gem;

import java.util.ArrayList;
import java.util.List;

/**
 * 蛮力搜索正确的移动;
 * 
 * @author crazyjohn
 * 
 */
public class CommonSearchMoveStrategy implements ISearchMoveStrategy {
	/** 棋盘 */
	GemChessBoard chessBoard;

	CommonSearchMoveStrategy(GemChessBoard chessBoard) {
		this.chessBoard = chessBoard;
	}

	@Override
	public List<Move> getMoves() {
		List<Move> result = new ArrayList<Move>();
		// 横向搜索先
		for (int i = 0; i < this.chessBoard.maxRow; i++) {
			for (int j = 0; j < this.chessBoard.maxCol; j++) {
				// FIXME: crazyjohn 会有数组越界的危险
				// 注意： Move的x1和y1是移动之后消除的宝石
				// 结构1
				// 1121
				if ((j + 3) < this.chessBoard.maxCol
						&& this.chessBoard.gems[i][j].getType() == this.chessBoard.gems[i][j + 1]
								.getType()
						&& this.chessBoard.gems[i][j].getType() != this.chessBoard.gems[i][j + 2]
								.getType()
						&& this.chessBoard.gems[i][j].getType() == this.chessBoard.gems[i][j + 3]
								.getType()) {
					Move move = new Move(i, j + 3, i, j + 2);
					result.add(move);
				}
				// 结构2
				// $$1
				// 112$
				if ((j + 2) < this.chessBoard.maxCol
						&& (i - 1) >= 0
						&& this.chessBoard.gems[i][j].getType() == this.chessBoard.gems[i][j + 1]
								.getType()
						&& this.chessBoard.gems[i][j].getType() != this.chessBoard.gems[i][j + 2]
								.getType()
						&& this.chessBoard.gems[i][j].getType() == this.chessBoard.gems[i - 1][j + 2]
								.getType()) {
					Move move = new Move(i - 1, j + 2, i, j + 2);
					result.add(move);
				}
				// 结构3
				// 112$
				// $$1
				if ((j + 2) < this.chessBoard.maxCol
						&& (i + 1) < this.chessBoard.maxRow
						&& this.chessBoard.gems[i][j].getType() == this.chessBoard.gems[i][j + 1]
								.getType()
						&& this.chessBoard.gems[i][j].getType() != this.chessBoard.gems[i][j + 2]
								.getType()
						&& this.chessBoard.gems[i][j].getType() == this.chessBoard.gems[i + 1][j + 2]
								.getType()) {
					Move move = new Move(i + 1, j + 2, i, j + 2);
					result.add(move);
				}

				// 结构4
				// 1211
				if ((j + 3) < this.chessBoard.maxCol
						&& this.chessBoard.gems[i][j].getType() != this.chessBoard.gems[i][j + 1]
								.getType()
						&& this.chessBoard.gems[i][j].getType() == this.chessBoard.gems[i][j + 2]
								.getType()
						&& this.chessBoard.gems[i][j].getType() == this.chessBoard.gems[i][j + 3]
								.getType()) {
					Move move = new Move(i, j, i, j + 1);
					result.add(move);
				}
				// 结构5
				// 121$
				// $1$$
				if ((j + 2) < chessBoard.maxCol
						&& (i + 1) < chessBoard.maxRow
						&& chessBoard.gems[i][j].getType() != chessBoard.gems[i][j + 1]
								.getType()
						&& chessBoard.gems[i][j].getType() == chessBoard.gems[i][j + 2]
								.getType()
						&& chessBoard.gems[i][j].getType() == chessBoard.gems[i + 1][j + 1]
								.getType()) {
					Move move = new Move(i + 1, j + 1, i, j + 1);
					result.add(move);
				}
				// 结构6
				// $1$$
				// 121$
				if ((j + 2) < chessBoard.maxCol
						&& (i - 1) >= 0
						&& chessBoard.gems[i][j].getType() != chessBoard.gems[i][j + 1]
								.getType()
						&& chessBoard.gems[i][j].getType() == chessBoard.gems[i][j + 2]
								.getType()
						&& chessBoard.gems[i][j].getType() == chessBoard.gems[i - 1][j + 1]
								.getType()) {
					Move move = new Move(i - 1, j + 1, i, j + 1);
					result.add(move);
				}
				// 结构7
				// 211$
				// 1$$$
				if ((j + 2) < chessBoard.maxCol
						&& (i + 1) < chessBoard.maxRow
						&& chessBoard.gems[i][j].getType() != chessBoard.gems[i][j + 1]
								.getType()
						&& chessBoard.gems[i][j].getType() != chessBoard.gems[i][j + 2]
								.getType()
						&& chessBoard.gems[i][j + 1].getType() == chessBoard.gems[i][j + 2]
								.getType()
						&& chessBoard.gems[i][j + 1].getType() == chessBoard.gems[i + 1][j]
								.getType()) {
					Move move = new Move(i + 1, j, i, j);
					result.add(move);
				}
				// 结构8
				// 1$$$
				// 211$
				if ((j + 2) < chessBoard.maxCol
						&& (i - 1) >= 0
						&& chessBoard.gems[i][j].getType() != chessBoard.gems[i][j + 1]
								.getType()
						&& chessBoard.gems[i][j].getType() != chessBoard.gems[i][j + 2]
								.getType()
						&& chessBoard.gems[i][j + 1].getType() == chessBoard.gems[i][j + 2]
								.getType()
						&& chessBoard.gems[i][j + 1].getType() == chessBoard.gems[i - 1][j]
								.getType()) {
					Move move = new Move(i - 1, j, i, j);
					result.add(move);
				}
			}
		}
		// 纵向搜索
		for (int i = 0; i < this.chessBoard.maxCol; i++) {
			for (int j = 0; j < this.chessBoard.maxRow; j++) {
				// 结构1
				// $1
				// $1
				// $2
				// $1
				if ((j + 3) < this.chessBoard.maxCol
						&& this.chessBoard.gems[j][i].getType() == this.chessBoard.gems[j + 1][i]
								.getType()
						&& this.chessBoard.gems[j][i].getType() != this.chessBoard.gems[j + 2][i]
								.getType()
						&& this.chessBoard.gems[j][i].getType() == this.chessBoard.gems[j + 3][i]
								.getType()) {
					Move move = new Move(j + 3, i, j + 2, i);
					result.add(move);
				}
				// 结构2
				// $1
				// $1
				// 12
				// $$
				if ((j + 2) < chessBoard.maxCol
						&& (i - 1) >= 0
						&& chessBoard.gems[j][i].getType() == chessBoard.gems[j + 1][i]
								.getType()
						&& chessBoard.gems[j][i].getType() != chessBoard.gems[j + 2][i]
								.getType()
						&& chessBoard.gems[j][i].getType() == chessBoard.gems[j + 2][i - 1]
								.getType()) {
					Move move = new Move(j + 2, i - 1, j + 2, i);
					result.add(move);
				}
				// 结构3
				// $1$
				// $1$
				// $21
				// $$$
				if ((j + 2) < chessBoard.maxCol
						&& (i + 1) < chessBoard.maxRow
						&& chessBoard.gems[j][i].getType() == chessBoard.gems[j + 1][i]
								.getType()
						&& chessBoard.gems[j][i].getType() != chessBoard.gems[j + 2][i]
								.getType()
						&& chessBoard.gems[j][i].getType() == chessBoard.gems[j + 2][i + 1]
								.getType()) {
					Move move = new Move(j + 2, i + 1, j + 2, i);
					result.add(move);
				}
				// 结构4
				// 1
				// 2
				// 1
				// 1
				if ((j + 3) < chessBoard.maxCol
						&& chessBoard.gems[j][i].getType() != chessBoard.gems[j + 1][i]
								.getType()
						&& chessBoard.gems[j][i].getType() == chessBoard.gems[j + 2][i]
								.getType()
						&& chessBoard.gems[j][i].getType() == chessBoard.gems[j + 3][i]
								.getType()) {
					Move move = new Move(j, i, j + 1, i);
					result.add(move);
				}

				// 结构5
				// $1
				// 12
				// $1
				// $$
				if ((j + 2) < chessBoard.maxCol
						&& (i - 1) >= 0
						&& chessBoard.gems[j][i].getType() != chessBoard.gems[j + 1][i]
								.getType()
						&& chessBoard.gems[j][i].getType() == chessBoard.gems[j + 2][i]
								.getType()
						&& chessBoard.gems[j][i].getType() == chessBoard.gems[j + 1][i - 1]
								.getType()) {
					Move move = new Move(j + 1, i - 1, j + 1, i);
					result.add(move);
				}

				// 结构6
				// $1
				// $21
				// $1
				// $$
				if ((j + 2) < chessBoard.maxCol
						&& (i + 1) < chessBoard.maxRow
						&& chessBoard.gems[j][i].getType() != chessBoard.gems[j + 1][i]
								.getType()
						&& chessBoard.gems[j][i].getType() == chessBoard.gems[j + 2][i]
								.getType()
						&& chessBoard.gems[j][i].getType() == chessBoard.gems[j + 1][i + 1]
								.getType()) {
					Move move = new Move(j + 1, i + 1, j + 1, i);
					result.add(move);
				}
				// 结构7
				// 12
				// $1
				// $1
				// $$
				if ((j + 2) < chessBoard.maxCol
						&& (i - 1) >= 0
						&& chessBoard.gems[j][i].getType() != chessBoard.gems[j + 1][i]
								.getType()
						&& chessBoard.gems[j][i].getType() != chessBoard.gems[j + 2][i]
								.getType()
						&& chessBoard.gems[j + 1][i].getType() == chessBoard.gems[j + 2][i]
								.getType()
						&& chessBoard.gems[j + 1][i].getType() == chessBoard.gems[j][i - 1]
								.getType()) {
					Move move = new Move(j, i - 1, j, i);
					result.add(move);
				}

				// 结构8
				// 21
				// 1$
				// 1$
				// $$
				if ((j + 2) < chessBoard.maxCol
						&& (i + 1) < chessBoard.maxRow
						&& chessBoard.gems[j][i].getType() != chessBoard.gems[j + 1][i]
								.getType()
						&& chessBoard.gems[j][i].getType() != chessBoard.gems[j + 2][i]
								.getType()
						&& chessBoard.gems[j + 1][i].getType() == chessBoard.gems[j + 2][i]
								.getType()
						&& chessBoard.gems[j + 1][i].getType() == chessBoard.gems[j][i + 1]
								.getType()) {
					Move move = new Move(j, i + 1, j, i);
					result.add(move);
				}
			}
		}
		return result;
	}

	@Override
	public List<Move> getFourBombsMoves() {
		List<Move> result = new ArrayList<Move>();
		for (int i = 0; i < this.chessBoard.maxRow; i++) {
			for (int j = 0; j < this.chessBoard.maxCol; j++) {
				// 8*8的棋盘只要保证四个角在棋盘内就不会越界,
				// chessBoard[i][j]为左上角第一个棋盘位置,不会为null
				// 结构1
				// $ $ 1 $
				// 1 1 2 1
				if(j+3<chessBoard.maxCol
						&& i+1<chessBoard.maxRow
						&& chessBoard.gems[i][j+2].getType() != chessBoard.gems[i+1][j+2].getType()
						&& chessBoard.gems[i][j+2].getType() == chessBoard.gems[i+1][j].getType()
						&& chessBoard.gems[i][j+2].getType() == chessBoard.gems[i+1][j+1].getType()
						&& chessBoard.gems[i][j+2].getType() == chessBoard.gems[i+1][j+3].getType()){
					Move move = new Move(i,j+2,i+1,j+2);
					result.add(move);
				}
				// 结构2
				// 1 1 2 1
				// $ $ 1 $
				if(j+3<chessBoard.maxCol
						&& i+1<chessBoard.maxRow
						&& chessBoard.gems[i+1][j+2].getType() != chessBoard.gems[i][j+2].getType()
						&& chessBoard.gems[i+1][j+2].getType() == chessBoard.gems[i][j].getType()
						&& chessBoard.gems[i+1][j+2].getType() == chessBoard.gems[i][j+1].getType()
						&& chessBoard.gems[i+1][j+2].getType() == chessBoard.gems[i][j+3].getType()){
					Move move = new Move(i+1,j+2,i,j+2);
					result.add(move);
				}
				// 结构3
				// $ 1 $ $
				// 1 2 1 1
				if(j+3<chessBoard.maxCol
						&& i+1<chessBoard.maxRow
						&& chessBoard.gems[i][j+1].getType() != chessBoard.gems[i+1][j+1].getType()
						&& chessBoard.gems[i][j+1].getType() == chessBoard.gems[i+1][j].getType()
						&& chessBoard.gems[i][j+1].getType() == chessBoard.gems[i+1][j+2].getType()
						&& chessBoard.gems[i][j+1].getType() == chessBoard.gems[i+1][j+3].getType()){
					Move move = new Move(i,j+1,i+1,j+1);
					result.add(move);
				}
				// 结构4
				// 1 2 1 1
				// $ 1 $ $
				if(j+3<chessBoard.maxCol
						&& i+1<chessBoard.maxRow
						&& chessBoard.gems[i+1][j+1].getType() != chessBoard.gems[i][j+1].getType()
						&& chessBoard.gems[i+1][j+1].getType() == chessBoard.gems[i][j].getType()
						&& chessBoard.gems[i+1][j+1].getType() == chessBoard.gems[i][j+2].getType()
						&& chessBoard.gems[i+1][j+1].getType() == chessBoard.gems[i][j+3].getType()){
					Move move = new Move(i+1,j+1,i,j+1);
					result.add(move);
				}
				// 结构5
				// 1 $
				// 1 $
				// 2 1
				// 1 $
				if(j+1<chessBoard.maxCol
						&& i+3<chessBoard.maxRow
						&& chessBoard.gems[i+2][j+1].getType() != chessBoard.gems[i+2][j].getType()
						&& chessBoard.gems[i+2][j+1].getType() == chessBoard.gems[i][j].getType()
						&& chessBoard.gems[i+2][j+1].getType() == chessBoard.gems[i+1][j].getType()
						&& chessBoard.gems[i+2][j+1].getType() == chessBoard.gems[i+3][j].getType()
						){
					Move move = new Move(i+2,j+1,i+2,j);
					result.add(move);
				}
				// 结构6
				// $ 1
				// $ 1
				// 1 2
				// $ 1
				if(j+1<chessBoard.maxCol
						&& i+3<chessBoard.maxRow
						&& chessBoard.gems[i+2][j].getType() != chessBoard.gems[i+2][j+1].getType()
						&& chessBoard.gems[i+2][j].getType() == chessBoard.gems[i][j+1].getType()
						&& chessBoard.gems[i+2][j].getType() == chessBoard.gems[i+1][j+1].getType()
						&& chessBoard.gems[i+2][j].getType() == chessBoard.gems[i+3][j+1].getType()
						){
					Move move = new Move(i+2,j,i+2,j+1);
					result.add(move);
				}
				// 结构7
				// 1 $
				// 2 1
				// 1 $
				// 1 $
				if(j+1<chessBoard.maxCol
						&& i+3<chessBoard.maxRow
						&& chessBoard.gems[i+1][j+1].getType() != chessBoard.gems[i+1][j].getType()
						&& chessBoard.gems[i+1][j+1].getType() == chessBoard.gems[i][j].getType()
						&& chessBoard.gems[i+1][j+1].getType() == chessBoard.gems[i+2][j].getType()
						&& chessBoard.gems[i+1][j+1].getType() == chessBoard.gems[i+3][j].getType()
						){
					Move move = new Move(i+1,j+1,i+1,j);
					result.add(move);
				}
				// 结构8
				// $ 1
				// 1 2
				// $ 1
				// $ 1
				if(j+1<chessBoard.maxCol
						&& i+3<chessBoard.maxRow
						&& chessBoard.gems[i+1][j].getType() != chessBoard.gems[i+1][j+1].getType()
						&& chessBoard.gems[i+1][j].getType() == chessBoard.gems[i][j+1].getType()
						&& chessBoard.gems[i+1][j].getType() == chessBoard.gems[i+2][j+1].getType()
						&& chessBoard.gems[i+1][j].getType() == chessBoard.gems[i+3][j+1].getType()
						){
					Move move = new Move(i+1,j,i+1,j+1);
					result.add(move);
				}
			}
		}
		return result;
	}

}
