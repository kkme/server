package com.hifun.soul.manageserver.service;

import java.util.Date;
import java.util.List;

import com.google.protobuf.RpcCallback;
import com.google.protobuf.RpcController;
import com.hifun.soul.core.util.TimeUtils;
import com.hifun.soul.manageserver.db.dao.BasicPlayerLogDaoImpl;
import com.hifun.soul.manageserver.db.dao.BattleLogDaoImpl;
import com.hifun.soul.manageserver.db.dao.ChatDaoImpl;
import com.hifun.soul.manageserver.db.dao.EnergyLogDaoImpl;
import com.hifun.soul.manageserver.db.dao.ExperienceLogDaoImpl;
import com.hifun.soul.manageserver.db.dao.FriendLogDaoImpl;
import com.hifun.soul.manageserver.db.dao.GMCommandLogDaoImpl;
import com.hifun.soul.manageserver.db.dao.HonourLogDaoImpl;
import com.hifun.soul.manageserver.db.dao.HoroscopeLogDaoImpl;
import com.hifun.soul.manageserver.db.dao.IBasicPlayerLogDao;
import com.hifun.soul.manageserver.db.dao.IBattleLogDao;
import com.hifun.soul.manageserver.db.dao.IChatLogDao;
import com.hifun.soul.manageserver.db.dao.IEnergyLogDao;
import com.hifun.soul.manageserver.db.dao.IExperienceLogDao;
import com.hifun.soul.manageserver.db.dao.IFriendLogDao;
import com.hifun.soul.manageserver.db.dao.IGMCommandLogDao;
import com.hifun.soul.manageserver.db.dao.IHonourLogDao;
import com.hifun.soul.manageserver.db.dao.IHoroscopeLogDao;
import com.hifun.soul.manageserver.db.dao.IItemLogDao;
import com.hifun.soul.manageserver.db.dao.IMoneyLogDao;
import com.hifun.soul.manageserver.db.dao.IOnlineTimeLogDao;
import com.hifun.soul.manageserver.db.dao.IOperationLogDao;
import com.hifun.soul.manageserver.db.dao.IPropertyLogDao;
import com.hifun.soul.manageserver.db.dao.IQuestLogDao;
import com.hifun.soul.manageserver.db.dao.IRechargeLogDao;
import com.hifun.soul.manageserver.db.dao.ISkillPointLogDao;
import com.hifun.soul.manageserver.db.dao.ITechPointLogDao;
import com.hifun.soul.manageserver.db.dao.ItemLogDaoImpl;
import com.hifun.soul.manageserver.db.dao.MoneyLogDaoImpl;
import com.hifun.soul.manageserver.db.dao.OnlineTimeLogDaoImpl;
import com.hifun.soul.manageserver.db.dao.OperationLogDaoImpl;
import com.hifun.soul.manageserver.db.dao.PropertyLogDaoImpl;
import com.hifun.soul.manageserver.db.dao.QuestLogDaoImpl;
import com.hifun.soul.manageserver.db.dao.RechargeLogDaoImpl;
import com.hifun.soul.manageserver.db.dao.SkillPointLogDaoImpl;
import com.hifun.soul.manageserver.db.dao.TechPointLogDaoImpl;
import com.hifun.soul.proto.services.LogServices.BasicPlayerLog;
import com.hifun.soul.proto.services.LogServices.BattleLog;
import com.hifun.soul.proto.services.LogServices.ChatLog;
import com.hifun.soul.proto.services.LogServices.EnergyLog;
import com.hifun.soul.proto.services.LogServices.ExperienceLog;
import com.hifun.soul.proto.services.LogServices.FriendLog;
import com.hifun.soul.proto.services.LogServices.GMCommandLog;
import com.hifun.soul.proto.services.LogServices.HonourLog;
import com.hifun.soul.proto.services.LogServices.HoroscopeLog;
import com.hifun.soul.proto.services.LogServices.ItemLog;
import com.hifun.soul.proto.services.LogServices.LogRpcService;
import com.hifun.soul.proto.services.LogServices.MoneyLog;
import com.hifun.soul.proto.services.LogServices.OnlineTimeLog;
import com.hifun.soul.proto.services.LogServices.OperationLog;
import com.hifun.soul.proto.services.LogServices.PropertyLog;
import com.hifun.soul.proto.services.LogServices.QueryBasicPlayerLogRequest;
import com.hifun.soul.proto.services.LogServices.QueryBasicPlayerLogResponse;
import com.hifun.soul.proto.services.LogServices.QueryBattleLogRequest;
import com.hifun.soul.proto.services.LogServices.QueryBattleLogResponse;
import com.hifun.soul.proto.services.LogServices.QueryChatLogRequest;
import com.hifun.soul.proto.services.LogServices.QueryChatLogResponse;
import com.hifun.soul.proto.services.LogServices.QueryEnergyLogRequest;
import com.hifun.soul.proto.services.LogServices.QueryEnergyLogResponse;
import com.hifun.soul.proto.services.LogServices.QueryExperienceLogRequest;
import com.hifun.soul.proto.services.LogServices.QueryExperienceLogResponse;
import com.hifun.soul.proto.services.LogServices.QueryFriendLogRequest;
import com.hifun.soul.proto.services.LogServices.QueryFriendLogResponse;
import com.hifun.soul.proto.services.LogServices.QueryGMCommandLogRequest;
import com.hifun.soul.proto.services.LogServices.QueryGMCommandLogResponse;
import com.hifun.soul.proto.services.LogServices.QueryHonourLogRequest;
import com.hifun.soul.proto.services.LogServices.QueryHonourLogResponse;
import com.hifun.soul.proto.services.LogServices.QueryHoroscopeLogRequest;
import com.hifun.soul.proto.services.LogServices.QueryHoroscopeLogResponse;
import com.hifun.soul.proto.services.LogServices.QueryItemLogRequest;
import com.hifun.soul.proto.services.LogServices.QueryItemLogResponse;
import com.hifun.soul.proto.services.LogServices.QueryMoneyLogRequest;
import com.hifun.soul.proto.services.LogServices.QueryMoneyLogResponse;
import com.hifun.soul.proto.services.LogServices.QueryOnlineTimeLogRequest;
import com.hifun.soul.proto.services.LogServices.QueryOnlineTimeLogResponse;
import com.hifun.soul.proto.services.LogServices.QueryOperationLogRequest;
import com.hifun.soul.proto.services.LogServices.QueryOperationLogResponse;
import com.hifun.soul.proto.services.LogServices.QueryPropertyLogRequest;
import com.hifun.soul.proto.services.LogServices.QueryPropertyLogResponse;
import com.hifun.soul.proto.services.LogServices.QueryQuestLogRequest;
import com.hifun.soul.proto.services.LogServices.QueryQuestLogResponse;
import com.hifun.soul.proto.services.LogServices.QueryRechargeLogRequest;
import com.hifun.soul.proto.services.LogServices.QueryRechargeLogResponse;
import com.hifun.soul.proto.services.LogServices.QuerySkillPointLogRequest;
import com.hifun.soul.proto.services.LogServices.QuerySkillPointLogResponse;
import com.hifun.soul.proto.services.LogServices.QueryTechPointLogRequest;
import com.hifun.soul.proto.services.LogServices.QueryTechPointLogResponse;
import com.hifun.soul.proto.services.LogServices.QuestLog;
import com.hifun.soul.proto.services.LogServices.RechargeLog;
import com.hifun.soul.proto.services.LogServices.SkillPointLog;
import com.hifun.soul.proto.services.LogServices.TechPointLog;

public class LogService extends LogRpcService {

	@Override
	public void queryBasicPlayerLog(RpcController controller, QueryBasicPlayerLogRequest request,
			RpcCallback<QueryBasicPlayerLogResponse> done) {
		String filter = "";
		StringBuilder sb = new StringBuilder();
		if (request.getServerId() != 0) {
			sb.append(" and server_id=" + request.getServerId());
		}
		if (request.getAccountId() != 0) {
			sb.append(" and account_id=" + request.getAccountId());
		}
		if (request.getAccountName() != null && request.getAccountName().length() > 0) {
			sb.append(" and account_name like '%" + request.getAccountName() + "%' ");
		}
		if (request.getCharacterId() != 0) {
			sb.append(" and char_id=" + request.getCharacterId());
		}
		if (request.getCharacterName() != null && request.getCharacterName().length() > 0) {
			sb.append(" and char_name like '%" + request.getCharacterName() + "%' ");
		}
		if (request.getReason() != 0) {
			sb.append(" and reason=" + request.getReason());
		}
		if (sb.length() > 0) {
			filter = "1=1 " + sb.toString();
		}
		IBasicPlayerLogDao basicPlayerDao = new BasicPlayerLogDaoImpl();
		List<BasicPlayerLog> basicPlayerLogs = basicPlayerDao.queryBasicPlayerLogList(new Date(request.getBeginDate()),
				new Date(request.getEndDate()), filter, request.getStart(), request.getMaxResult());
		QueryBasicPlayerLogResponse.Builder response = QueryBasicPlayerLogResponse.newBuilder();
		response.addAllBasicPlayerLogs(basicPlayerLogs);
		response.setTotalCount(basicPlayerDao.queryCount(new Date(request.getBeginDate()),
				new Date(request.getEndDate()), filter));
		done.run(response.build());
	}

	@Override
	public void queryBattleLog(RpcController controller, QueryBattleLogRequest request,
			RpcCallback<QueryBattleLogResponse> done) {
		String filter = "";
		StringBuilder sb = new StringBuilder();
		if (request.getServerId() != 0) {
			sb.append(" and server_id=" + request.getServerId());
		}
		if (request.getAccountId() != 0) {
			sb.append(" and account_id=" + request.getAccountId());
		}
		if (request.getAccountName() != null && request.getAccountName().length() > 0) {
			sb.append(" and account_name like '%" + request.getAccountName() + "%' ");
		}
		if (request.getCharacterId() != 0) {
			sb.append(" and char_id=" + request.getCharacterId());
		}
		if (request.getCharacterName() != null && request.getCharacterName().length() > 0) {
			sb.append(" and char_name like '%" + request.getCharacterName() + "%' ");
		}
		if (request.getReason() != 0) {
			sb.append(" and reason=" + request.getReason());
		}
		if (sb.length() > 0) {
			filter = "1=1 " + sb.toString();
		}
		IBattleLogDao battleDao = new BattleLogDaoImpl();
		List<BattleLog> battleLogs = battleDao.queryBattleLogList(new Date(request.getBeginDate()),
				new Date(request.getEndDate()), filter, request.getStart(), request.getMaxResult());
		QueryBattleLogResponse.Builder responseBuilder = QueryBattleLogResponse.newBuilder();
		long totalCount = battleDao
				.queryCount(new Date(request.getBeginDate()), new Date(request.getEndDate()), filter);
		responseBuilder.setTotalCount(totalCount);
		responseBuilder.addAllBattleLogs(battleLogs);
		QueryBattleLogResponse response = responseBuilder.build();
		done.run(response);

	}

	@Override
	public void queryChatLog(RpcController controller, QueryChatLogRequest request,
			RpcCallback<QueryChatLogResponse> done) {
		String filter = "";
		StringBuilder sb = new StringBuilder();
		if (request.getServerId() != 0) {
			sb.append(" and server_id=" + request.getServerId());
		}
		if (request.getAccountId() != 0) {
			sb.append(" and account_id=" + request.getAccountId());
		}
		if (request.getAccountName() != null && request.getAccountName().length() > 0) {
			sb.append(" and account_name like '%" + request.getAccountName() + "%' ");
		}
		if (request.getCharacterId() != 0) {
			sb.append(" and char_id=" + request.getCharacterId());
		}
		if (request.getCharacterName() != null && request.getCharacterName().length() > 0) {
			sb.append(" and char_name like '%" + request.getCharacterName() + "%' ");
		}
		if (request.getReason() != 0) {
			sb.append(" and reason=" + request.getReason());
		}
		if (sb.length() > 0) {
			filter = "1=1 " + sb.toString();
		}
		IChatLogDao dao = new ChatDaoImpl();
		List<ChatLog> logs = dao.queryChatLogList(new Date(request.getBeginDate()), new Date(request.getEndDate()),
				filter, request.getStart(), request.getMaxResult());
		QueryChatLogResponse.Builder response = QueryChatLogResponse.newBuilder();
		response.addAllChatLogs(logs);
		response.setTotalCount(dao.queryCount(new Date(request.getBeginDate()), new Date(request.getEndDate()), filter));
		done.run(response.build());
	}

	@Override
	public void queryFriendLog(RpcController controller, QueryFriendLogRequest request,
			RpcCallback<QueryFriendLogResponse> done) {
		String filter = "";
		StringBuilder sb = new StringBuilder();
		if (request.getServerId() != 0) {
			sb.append(" and server_id=" + request.getServerId());
		}
		if (request.getAccountId() != 0) {
			sb.append(" and account_id=" + request.getAccountId());
		}
		if (request.getAccountName() != null && request.getAccountName().length() > 0) {
			sb.append(" and account_name like '%" + request.getAccountName() + "%' ");
		}
		if (request.getCharacterId() != 0) {
			sb.append(" and char_id=" + request.getCharacterId());
		}
		if (request.getCharacterName() != null && request.getCharacterName().length() > 0) {
			sb.append(" and char_name like '%" + request.getCharacterName() + "%' ");
		}
		if (request.getReason() != 0) {
			sb.append(" and reason=" + request.getReason());
		}
		if (sb.length() > 0) {
			filter = "1=1 " + sb.toString();
		}
		IFriendLogDao dao = new FriendLogDaoImpl();
		List<FriendLog> logs = dao.queryFriendLogList(new Date(request.getBeginDate()), new Date(request.getEndDate()),
				filter, request.getStart(), request.getMaxResult());
		QueryFriendLogResponse.Builder response = QueryFriendLogResponse.newBuilder();
		response.addAllFriendLogs(logs);
		response.setTotalCount(dao.queryCount(new Date(request.getBeginDate()), new Date(request.getEndDate()), filter));
		done.run(response.build());

	}

	@Override
	public void queryGMCommandLog(RpcController controller, QueryGMCommandLogRequest request,
			RpcCallback<QueryGMCommandLogResponse> done) {
		String filter = "";
		StringBuilder sb = new StringBuilder();
		if (request.getServerId() != 0) {
			sb.append(" and server_id=" + request.getServerId());
		}
		if (request.getAccountId() != 0) {
			sb.append(" and account_id=" + request.getAccountId());
		}
		if (request.getAccountName() != null && request.getAccountName().length() > 0) {
			sb.append(" and account_name like '%" + request.getAccountName() + "%' ");
		}
		if (request.getCharacterId() != 0) {
			sb.append(" and char_id=" + request.getCharacterId());
		}
		if (request.getCharacterName() != null && request.getCharacterName().length() > 0) {
			sb.append(" and char_name like '%" + request.getCharacterName() + "%' ");
		}
		if (request.getReason() != 0) {
			sb.append(" and reason=" + request.getReason());
		}
		if (sb.length() > 0) {
			filter = "1=1 " + sb.toString();
		}
		IGMCommandLogDao dao = new GMCommandLogDaoImpl();
		List<GMCommandLog> logs = dao.queryGMCommandLogList(new Date(request.getBeginDate()),
				new Date(request.getEndDate()), filter, request.getStart(), request.getMaxResult());
		QueryGMCommandLogResponse.Builder response = QueryGMCommandLogResponse.newBuilder();
		response.addAllGmCommandLogs(logs);
		response.setTotalCount(dao.queryCount(new Date(request.getBeginDate()), new Date(request.getEndDate()), filter));
		done.run(response.build());

	}

	@Override
	public void queryHoroscopeLog(RpcController controller, QueryHoroscopeLogRequest request,
			RpcCallback<QueryHoroscopeLogResponse> done) {
		String filter = "";
		StringBuilder sb = new StringBuilder();
		if (request.getServerId() != 0) {
			sb.append(" and server_id=" + request.getServerId());
		}
		if (request.getAccountId() != 0) {
			sb.append(" and account_id=" + request.getAccountId());
		}
		if (request.getAccountName() != null && request.getAccountName().length() > 0) {
			sb.append(" and account_name like '%" + request.getAccountName() + "%' ");
		}
		if (request.getCharacterId() != 0) {
			sb.append(" and char_id=" + request.getCharacterId());
		}
		if (request.getCharacterName() != null && request.getCharacterName().length() > 0) {
			sb.append(" and char_name like '%" + request.getCharacterName() + "%' ");
		}
		if (request.getReason() != 0) {
			sb.append(" and reason=" + request.getReason());
		}
		if (sb.length() > 0) {
			filter = "1=1 " + sb.toString();
		}
		IHoroscopeLogDao dao = new HoroscopeLogDaoImpl();
		List<HoroscopeLog> logs = dao.queryHoroscopeLogList(new Date(request.getBeginDate()),
				new Date(request.getEndDate()), filter, request.getStart(), request.getMaxResult());
		QueryHoroscopeLogResponse.Builder response = QueryHoroscopeLogResponse.newBuilder();
		response.addAllHoroscopeLogs(logs);
		response.setTotalCount(dao.queryCount(new Date(request.getBeginDate()), new Date(request.getEndDate()), filter));
		done.run(response.build());

	}

	@Override
	public void queryItemLog(RpcController controller, QueryItemLogRequest request,
			RpcCallback<QueryItemLogResponse> done) {
		String filter = "";
		StringBuilder sb = new StringBuilder();
		if (request.getServerId() != 0) {
			sb.append(" and server_id=" + request.getServerId());
		}
		if (request.getAccountId() != 0) {
			sb.append(" and account_id=" + request.getAccountId());
		}
		if (request.getAccountName() != null && request.getAccountName().length() > 0) {
			sb.append(" and account_name like '%" + request.getAccountName() + "%' ");
		}
		if (request.getCharacterId() != 0) {
			sb.append(" and char_id=" + request.getCharacterId());
		}
		if (request.getCharacterName() != null && request.getCharacterName().length() > 0) {
			sb.append(" and char_name like '%" + request.getCharacterName() + "%' ");
		}
		if (request.getReason() != 0) {
			sb.append(" and reason=" + request.getReason());
		}
		if (sb.length() > 0) {
			filter = "1=1 " + sb.toString();
		}
		IItemLogDao dao = new ItemLogDaoImpl();
		List<ItemLog> logs = dao.queryItemLogList(new Date(request.getBeginDate()), new Date(request.getEndDate()),
				filter, request.getStart(), request.getMaxResult());
		QueryItemLogResponse.Builder response = QueryItemLogResponse.newBuilder();
		response.addAllItemLogs(logs);
		response.setTotalCount(dao.queryCount(new Date(request.getBeginDate()), new Date(request.getEndDate()), filter));
		done.run(response.build());

	}

	@Override
	public void queryMoneyLog(RpcController controller, QueryMoneyLogRequest request,
			RpcCallback<QueryMoneyLogResponse> done) {
		String filter = "";
		StringBuilder sb = new StringBuilder();
		if (request.getServerId() != 0) {
			sb.append(" and server_id=" + request.getServerId());
		}
		if (request.getAccountId() != 0) {
			sb.append(" and account_id=" + request.getAccountId());
		}
		if (request.getAccountName() != null && request.getAccountName().length() > 0) {
			sb.append(" and account_name like '%" + request.getAccountName() + "%' ");
		}
		if (request.getCharacterId() != 0) {
			sb.append(" and char_id=" + request.getCharacterId());
		}
		if (request.getCharacterName() != null && request.getCharacterName().length() > 0) {
			sb.append(" and char_name like '%" + request.getCharacterName() + "%' ");
		}
		if (request.getReason() != 0) {
			sb.append(" and reason=" + request.getReason());
		}
		if (request.getCurrencyType() != 0) {
			sb.append(" and currency_type=" + request.getCurrencyType());
		}
		if (sb.length() > 0) {
			filter = "1=1 " + sb.toString();
		}
		IMoneyLogDao dao = new MoneyLogDaoImpl();
		List<MoneyLog> logs = dao.queryMoneyLogList(new Date(request.getBeginDate()), new Date(request.getEndDate()),
				filter, request.getStart(), request.getMaxResult());
		QueryMoneyLogResponse.Builder response = QueryMoneyLogResponse.newBuilder();
		response.addAllMoneyLogs(logs);
		response.setTotalCount(dao.queryCount(new Date(request.getBeginDate()), new Date(request.getEndDate()), filter));
		done.run(response.build());

	}

	@Override
	public void queryOnlineTimeLog(RpcController controller, QueryOnlineTimeLogRequest request,
			RpcCallback<QueryOnlineTimeLogResponse> done) {
		String filter = "";
		StringBuilder sb = new StringBuilder();
		if (request.getServerId() != 0) {
			sb.append(" and server_id=" + request.getServerId());
		}
		if (request.getAccountId() != 0) {
			sb.append(" and account_id=" + request.getAccountId());
		}
		if (request.getAccountName() != null && request.getAccountName().length() > 0) {
			sb.append(" and account_name like '%" + request.getAccountName() + "%' ");
		}
		if (request.getCharacterId() != 0) {
			sb.append(" and char_id=" + request.getCharacterId());
		}
		if (request.getCharacterName() != null && request.getCharacterName().length() > 0) {
			sb.append(" and char_name like '%" + request.getCharacterName() + "%' ");
		}
		if (request.getReason() != 0) {
			sb.append(" and reason=" + request.getReason());
		}
		if (sb.length() > 0) {
			filter = "1=1 " + sb.toString();
		}
		IOnlineTimeLogDao dao = new OnlineTimeLogDaoImpl();
		List<OnlineTimeLog> logs = dao.queryOnlineTimeLogList(new Date(request.getBeginDate()),
				new Date(request.getEndDate()), filter, request.getStart(), request.getMaxResult());
		QueryOnlineTimeLogResponse.Builder response = QueryOnlineTimeLogResponse.newBuilder();
		response.addAllOnlineTimeLogs(logs);
		response.setTotalCount(dao.queryCount(new Date(request.getBeginDate()), new Date(request.getEndDate()), filter));
		done.run(response.build());

	}

	@Override
	public void queryPropertyLog(RpcController controller, QueryPropertyLogRequest request,
			RpcCallback<QueryPropertyLogResponse> done) {
		String filter = "";
		StringBuilder sb = new StringBuilder();
		if (request.getServerId() != 0) {
			sb.append(" and server_id=" + request.getServerId());
		}
		if (request.getAccountId() != 0) {
			sb.append(" and account_id=" + request.getAccountId());
		}
		if (request.getAccountName() != null && request.getAccountName().length() > 0) {
			sb.append(" and account_name like '%" + request.getAccountName() + "%' ");
		}
		if (request.getCharacterId() != 0) {
			sb.append(" and char_id=" + request.getCharacterId());
		}
		if (request.getCharacterName() != null && request.getCharacterName().length() > 0) {
			sb.append(" and char_name like '%" + request.getCharacterName() + "%' ");
		}
		if (request.getReason() != 0) {
			sb.append(" and reason=" + request.getReason());
		}
		if (sb.length() > 0) {
			filter = "1=1 " + sb.toString();
		}
		IPropertyLogDao dao = new PropertyLogDaoImpl();
		List<PropertyLog> logs = dao.queryPropertyLogList(new Date(request.getBeginDate()),
				new Date(request.getEndDate()), filter, request.getStart(), request.getMaxResult());
		QueryPropertyLogResponse.Builder response = QueryPropertyLogResponse.newBuilder();
		response.addAllPropertyLogs(logs);
		response.setTotalCount(dao.queryCount(new Date(request.getBeginDate()), new Date(request.getEndDate()), filter));
		done.run(response.build());
	}

	@Override
	public void queryQuestLog(RpcController controller, QueryQuestLogRequest request,
			RpcCallback<QueryQuestLogResponse> done) {
		String filter = "";
		StringBuilder sb = new StringBuilder();
		if (request.getServerId() != 0) {
			sb.append(" and server_id=" + request.getServerId());
		}
		if (request.getAccountId() != 0) {
			sb.append(" and account_id=" + request.getAccountId());
		}
		if (request.getAccountName() != null && request.getAccountName().length() > 0) {
			sb.append(" and account_name like '%" + request.getAccountName() + "%' ");
		}
		if (request.getCharacterId() != 0) {
			sb.append(" and char_id=" + request.getCharacterId());
		}
		if (request.getCharacterName() != null && request.getCharacterName().length() > 0) {
			sb.append(" and char_name like '%" + request.getCharacterName() + "%' ");
		}
		if (request.getReason() != 0) {
			sb.append(" and reason=" + request.getReason());
		}
		if (sb.length() > 0) {
			filter = "1=1 " + sb.toString();
		}
		IQuestLogDao dao = new QuestLogDaoImpl();
		List<QuestLog> logs = dao.queryQuestLogList(new Date(request.getBeginDate()), new Date(request.getEndDate()),
				filter, request.getStart(), request.getMaxResult());
		QueryQuestLogResponse.Builder response = QueryQuestLogResponse.newBuilder();
		response.addAllQuestLogs(logs);
		response.setTotalCount(dao.queryCount(new Date(request.getBeginDate()), new Date(request.getEndDate()), filter));
		done.run(response.build());
	}

	@Override
	public void queryRechargeLog(RpcController controller, QueryRechargeLogRequest request,
			RpcCallback<QueryRechargeLogResponse> done) {
		String filter = "";
		StringBuilder sb = new StringBuilder();
		if (request.getServerId() != 0) {
			sb.append(" and server_id=" + request.getServerId());
		}
		if (request.getAccountId() != 0) {
			sb.append(" and account_id=" + request.getAccountId());
		}
		if (request.getAccountName() != null && request.getAccountName().length() > 0) {
			sb.append(" and account_name like '%" + request.getAccountName() + "%' ");
		}
		if (request.getCharacterId() != 0) {
			sb.append(" and char_id=" + request.getCharacterId());
		}
		if (request.getCharacterName() != null && request.getCharacterName().length() > 0) {
			sb.append(" and char_name like '%" + request.getCharacterName() + "%' ");
		}
		if (request.getReason() != 0) {
			sb.append(" and reason=" + request.getReason());
		}
		if (sb.length() > 0) {
			filter = "1=1 " + sb.toString();
		}
		IRechargeLogDao dao = new RechargeLogDaoImpl();
		List<RechargeLog> logs = dao.queryRechargeLogList(new Date(request.getBeginDate()),
				new Date(request.getEndDate()), filter, request.getStart(), request.getMaxResult());
		QueryRechargeLogResponse.Builder response = QueryRechargeLogResponse.newBuilder();
		response.addAllRechargeLogs(logs);
		response.setTotalCount(dao.queryCount(new Date(request.getBeginDate()), new Date(request.getEndDate()), filter));
		done.run(response.build());

	}

	@Override
	public void queryOperationLog(RpcController controller, QueryOperationLogRequest request,
			RpcCallback<QueryOperationLogResponse> done) {
		String filter = "";
		StringBuilder sb = new StringBuilder();
		if (request.getUserId() != 0) {
			sb.append(" and userId=" + request.getUserId());
		}
		if (request.getUserName() != null && request.getUserName().length() > 0) {
			sb.append(" and userName like '%" + request.getUserName() + "%'");
		}
		if (request.getOperationType() != 0) {
			sb.append(" and operationType=" + request.getOperationType());
		}
		if (sb.length() > 0) {
			filter = "1=1 " + sb.toString();
		}
		long beginTime = TimeUtils.getBeginOfDay(request.getBeginDate());
		long endTime = TimeUtils.getBeginOfDay(request.getEndDate()) + TimeUtils.DAY;
		IOperationLogDao dao = new OperationLogDaoImpl();
		List<OperationLog> logs = dao.queryOperationLogList(beginTime, endTime, filter, request.getStart(),
				request.getMaxResult());
		QueryOperationLogResponse.Builder response = QueryOperationLogResponse.newBuilder();
		response.addAllOperationLogs(logs);
		response.setTotalCount(dao.queryCount(beginTime, endTime, filter));
		done.run(response.build());
	}

	@Override
	public void queryEnergyLog(RpcController controller, QueryEnergyLogRequest request,
			RpcCallback<QueryEnergyLogResponse> done) {
		String filter = "";
		StringBuilder sb = new StringBuilder();
		if (request.getServerId() != 0) {
			sb.append(" and server_id=" + request.getServerId());
		}
		if (request.getAccountId() != 0) {
			sb.append(" and account_id=" + request.getAccountId());
		}
		if (request.getAccountName() != null && request.getAccountName().length() > 0) {
			sb.append(" and account_name like '%" + request.getAccountName() + "%' ");
		}
		if (request.getCharacterId() != 0) {
			sb.append(" and char_id=" + request.getCharacterId());
		}
		if (request.getCharacterName() != null && request.getCharacterName().length() > 0) {
			sb.append(" and char_name like '%" + request.getCharacterName() + "%' ");
		}
		if (request.getReason() != 0) {
			sb.append(" and reason=" + request.getReason());
		}
		if (sb.length() > 0) {
			filter = "1=1 " + sb.toString();
		}
		IEnergyLogDao dao = new EnergyLogDaoImpl();
		List<EnergyLog> logs = dao.queryEnergyLogList(new Date(request.getBeginDate()), new Date(request.getEndDate()),
				filter, request.getStart(), request.getMaxResult());
		QueryEnergyLogResponse.Builder response = QueryEnergyLogResponse.newBuilder();
		response.addAllLogs(logs);
		response.setTotalCount(dao.queryCount(new Date(request.getBeginDate()), new Date(request.getEndDate()), filter));
		done.run(response.build());

	}

	@Override
	public void queryHonourLog(RpcController controller, QueryHonourLogRequest request,
			RpcCallback<QueryHonourLogResponse> done) {
		String filter = "";
		StringBuilder sb = new StringBuilder();
		if (request.getServerId() != 0) {
			sb.append(" and server_id=" + request.getServerId());
		}
		if (request.getAccountId() != 0) {
			sb.append(" and account_id=" + request.getAccountId());
		}
		if (request.getAccountName() != null && request.getAccountName().length() > 0) {
			sb.append(" and account_name like '%" + request.getAccountName() + "%' ");
		}
		if (request.getCharacterId() != 0) {
			sb.append(" and char_id=" + request.getCharacterId());
		}
		if (request.getCharacterName() != null && request.getCharacterName().length() > 0) {
			sb.append(" and char_name like '%" + request.getCharacterName() + "%' ");
		}
		if (request.getReason() != 0) {
			sb.append(" and reason=" + request.getReason());
		}
		if (sb.length() > 0) {
			filter = "1=1 " + sb.toString();
		}
		IHonourLogDao dao = new HonourLogDaoImpl();
		List<HonourLog> logs = dao.queryHonourLogList(new Date(request.getBeginDate()), new Date(request.getEndDate()),
				filter, request.getStart(), request.getMaxResult());
		QueryHonourLogResponse.Builder response = QueryHonourLogResponse.newBuilder();
		response.addAllLogs(logs);
		response.setTotalCount(dao.queryCount(new Date(request.getBeginDate()), new Date(request.getEndDate()), filter));
		done.run(response.build());
	}

	@Override
	public void queryExperienceLog(RpcController controller, QueryExperienceLogRequest request,
			RpcCallback<QueryExperienceLogResponse> done) {
		String filter = "";
		StringBuilder sb = new StringBuilder();
		if (request.getServerId() != 0) {
			sb.append(" and server_id=" + request.getServerId());
		}
		if (request.getAccountId() != 0) {
			sb.append(" and account_id=" + request.getAccountId());
		}
		if (request.getAccountName() != null && request.getAccountName().length() > 0) {
			sb.append(" and account_name like '%" + request.getAccountName() + "%' ");
		}
		if (request.getCharacterId() != 0) {
			sb.append(" and char_id=" + request.getCharacterId());
		}
		if (request.getCharacterName() != null && request.getCharacterName().length() > 0) {
			sb.append(" and char_name like '%" + request.getCharacterName() + "%' ");
		}
		if (request.getReason() != 0) {
			sb.append(" and reason=" + request.getReason());
		}
		if (sb.length() > 0) {
			filter = "1=1 " + sb.toString();
		}
		IExperienceLogDao dao = new ExperienceLogDaoImpl();
		List<ExperienceLog> logs = dao.queryExperienceLogList(new Date(request.getBeginDate()),
				new Date(request.getEndDate()), filter, request.getStart(), request.getMaxResult());
		QueryExperienceLogResponse.Builder response = QueryExperienceLogResponse.newBuilder();
		response.addAllLogs(logs);
		response.setTotalCount(dao.queryCount(new Date(request.getBeginDate()), new Date(request.getEndDate()), filter));
		done.run(response.build());

	}

	@Override
	public void queryTechPointLog(RpcController controller, QueryTechPointLogRequest request,
			RpcCallback<QueryTechPointLogResponse> done) {
		String filter = "";
		StringBuilder sb = new StringBuilder();
		if (request.getServerId() != 0) {
			sb.append(" and server_id=" + request.getServerId());
		}
		if (request.getAccountId() != 0) {
			sb.append(" and account_id=" + request.getAccountId());
		}
		if (request.getAccountName() != null && request.getAccountName().length() > 0) {
			sb.append(" and account_name like '%" + request.getAccountName() + "%' ");
		}
		if (request.getCharacterId() != 0) {
			sb.append(" and char_id=" + request.getCharacterId());
		}
		if (request.getCharacterName() != null && request.getCharacterName().length() > 0) {
			sb.append(" and char_name like '%" + request.getCharacterName() + "%' ");
		}
		if (request.getReason() != 0) {
			sb.append(" and reason=" + request.getReason());
		}
		if (sb.length() > 0) {
			filter = "1=1 " + sb.toString();
		}
		ITechPointLogDao dao = new TechPointLogDaoImpl();
		List<TechPointLog> logs = dao.queryTechPointLogList(new Date(request.getBeginDate()),
				new Date(request.getEndDate()), filter, request.getStart(), request.getMaxResult());
		QueryTechPointLogResponse.Builder response = QueryTechPointLogResponse.newBuilder();
		response.addAllLogs(logs);
		response.setTotalCount(dao.queryCount(new Date(request.getBeginDate()), new Date(request.getEndDate()), filter));
		done.run(response.build());
	}

	@Override
	public void querySkillPointLog(RpcController controller, QuerySkillPointLogRequest request,
			RpcCallback<QuerySkillPointLogResponse> done) {
		String filter = "";
		StringBuilder sb = new StringBuilder();
		if (request.getServerId() != 0) {
			sb.append(" and server_id=" + request.getServerId());
		}
		if (request.getAccountId() != 0) {
			sb.append(" and account_id=" + request.getAccountId());
		}
		if (request.getAccountName() != null && request.getAccountName().length() > 0) {
			sb.append(" and account_name like '%" + request.getAccountName() + "%' ");
		}
		if (request.getCharacterId() != 0) {
			sb.append(" and char_id=" + request.getCharacterId());
		}
		if (request.getCharacterName() != null && request.getCharacterName().length() > 0) {
			sb.append(" and char_name like '%" + request.getCharacterName() + "%' ");
		}
		if (request.getReason() != 0) {
			sb.append(" and reason=" + request.getReason());
		}
		if (sb.length() > 0) {
			filter = "1=1 " + sb.toString();
		}
		ISkillPointLogDao dao = new SkillPointLogDaoImpl();
		List<SkillPointLog> logs = dao.querySkillPointLogList(new Date(request.getBeginDate()),
				new Date(request.getEndDate()), filter, request.getStart(), request.getMaxResult());
		QuerySkillPointLogResponse.Builder response = QuerySkillPointLogResponse.newBuilder();
		response.addAllLogs(logs);
		response.setTotalCount(dao.queryCount(new Date(request.getBeginDate()), new Date(request.getEndDate()), filter));
		done.run(response.build());
	}

}
