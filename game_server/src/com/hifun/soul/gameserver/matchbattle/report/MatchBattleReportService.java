package com.hifun.soul.gameserver.matchbattle.report;

import com.hifun.soul.common.constants.LangConstants;
import com.hifun.soul.gameserver.common.GameServerAssist;
import com.hifun.soul.gameserver.common.text.LinkType;
import com.hifun.soul.gameserver.common.text.RichTextHelper;
import com.hifun.soul.gameserver.matchbattle.MatchBattleRole;

/**
 * 
 * @author magicstone
 *
 */
public class MatchBattleReportService {
	private static final String OUT_OF_TURN_ROLE_COLOR="FFFF00";
	private static final String WINNER_COLOR="009E0F";
	private static final String LOSER_COLOR="CC0000";
	private static final String NEW_LINE_FLAG="\n";
	
	public static String generateOutOfTurnReport(MatchBattleRole outOfTurnRole,int coinReward){		
		String linkedRoleName = getLinkedRoleName(outOfTurnRole,OUT_OF_TURN_ROLE_COLOR);
		String reportContent = GameServerAssist.getSysLangService().read(LangConstants.MATCH_BATTLE_OUT_OF_TURN,
									linkedRoleName,
									coinReward);
		return reportContent+NEW_LINE_FLAG;
	}
	
	public static String generateSelfOutOfTurnReport(int coinReward){
		String reportContent = GameServerAssist.getSysLangService().read(LangConstants.MATCH_BATTLE_OUT_OF_TURN,
				GameServerAssist.getSysLangService().read(LangConstants.YOU),
				coinReward);
		return reportContent+NEW_LINE_FLAG;
	}
	
	public static String generateBattleReport(MatchBattleRole winner,MatchBattleRole loser,MatchBattleReportInfo battleReportInfo){		
		String winnerName = getLinkedRoleName(winner,WINNER_COLOR);
		String loserName = getLinkedRoleName(loser,LOSER_COLOR);
		String reportContent= formatBattleReport(winnerName,loserName,winner.getConsecutiveWinCount(),battleReportInfo);		
		return reportContent;
	}
	
	public static String generateSelfBattleReport(MatchBattleRole self,MatchBattleRole other,boolean isWin,MatchBattleReportInfo battleReportInfo){
		String selfName = GameServerAssist.getSysLangService().read(LangConstants.YOU);		
		String winnerName = "";
		String loserName = "";
		int winnerConsecutiveWinCount = 0;
		if(isWin){
			winnerName = selfName;
			loserName = getLinkedRoleName(other,LOSER_COLOR);
			winnerConsecutiveWinCount = self.getConsecutiveWinCount();
		}else{
			winnerName = getLinkedRoleName(other,WINNER_COLOR);
			loserName = selfName;
			winnerConsecutiveWinCount = other.getConsecutiveWinCount();
		}
		String reportContent=formatBattleReport(winnerName,loserName,winnerConsecutiveWinCount,battleReportInfo);		
		return reportContent;
	}
	
	private static String getLinkedRoleName(MatchBattleRole role,String color){
		String linkedRoleName = RichTextHelper.addLink(role.getRoleName(),
				LinkType.CHARACTER.getIndex(), Long.toHexString(role.getRoleId()),
				Long.toHexString(role.getRoleId()), color);
		return linkedRoleName; 
	}
	
	private static String formatBattleReport(String winnerName,String loserName,int winnerConsecutiveWinCount,MatchBattleReportInfo battleReportInfo){
		String reportContent="";
		if(battleReportInfo.isTerminate() && winnerConsecutiveWinCount>1){
			reportContent = GameServerAssist.getSysLangService().read(LangConstants.MATCH_BATTLE_TERMINATE_AND_STREAK_WIN,
					winnerName,
					loserName,
					battleReportInfo.getLastConsecutiveWinCount(),
					winnerConsecutiveWinCount,
					battleReportInfo.getHonourReward(),
					battleReportInfo.getCoinReward()
					);
		}else if(battleReportInfo.isTerminate()){
			reportContent = GameServerAssist.getSysLangService().read(LangConstants.MATCH_BATTLE_TERMINATE_WIN,
					winnerName,
					loserName,
					battleReportInfo.getLastConsecutiveWinCount(),					
					battleReportInfo.getHonourReward(),
					battleReportInfo.getCoinReward()
					);
		}else if(winnerConsecutiveWinCount>1){
			reportContent = GameServerAssist.getSysLangService().read(LangConstants.MATCH_BATTLE_STREAK_WIN,
					winnerName,
					loserName,
					winnerConsecutiveWinCount,			
					battleReportInfo.getHonourReward(),
					battleReportInfo.getCoinReward()
					);
		}else {
			reportContent = GameServerAssist.getSysLangService().read(LangConstants.MATCH_BATTLE_WIN,
					winnerName,
					loserName,					
					battleReportInfo.getHonourReward(),
					battleReportInfo.getCoinReward()
					);
		}	
		return reportContent+NEW_LINE_FLAG;
	}
	
	
}
