package com.hifun.soul.manageserver.service;

import java.util.Date;
import java.util.List;

import com.google.protobuf.RpcCallback;
import com.google.protobuf.RpcController;
import com.hifun.soul.gamedb.agent.query.DataQueryConstants;
import com.hifun.soul.gamedb.entity.QuestionEntity;
import com.hifun.soul.manageserver.DBServiceManager;
import com.hifun.soul.proto.services.Services.AnswerQuestionRequest;
import com.hifun.soul.proto.services.Services.AnswerQuestionResponse;
import com.hifun.soul.proto.services.Services.QueryQuestionListRequest;
import com.hifun.soul.proto.services.Services.QueryQuestionListResponse;
import com.hifun.soul.proto.services.Services.QuestionInfo;
import com.hifun.soul.proto.services.Services.QuestionRpcService;

public class QuestionService extends QuestionRpcService {

	private DBServiceManager dbManager;
	public QuestionService(DBServiceManager dbManager){
		this.dbManager = dbManager;
	}
	
	@Override
	public void queryQuestionList(RpcController controller, QueryQuestionListRequest request,
			RpcCallback<QueryQuestionListResponse> done) {
		int questionType = 0;
		String contentKey="%%";
		String askerNameKey="%%";
		
		questionType=request.getQuestionType();
		if(request.getContent()!=null && request.getContent().length()>0){
			contentKey="%"+request.getContent()+"%";
		}
		if(request.getAskerName()!=null && request.getAskerName().length()>0){
			askerNameKey="%"+request.getAskerName()+"%";
		}
		
		String queryName=DataQueryConstants.QUERY_QUESTION;
		String queryCountName = DataQueryConstants.QUERY_QUESTION_COUNT;
		String[] args= new String[] {"content","askerName" };
		Object[] params = new Object[]{contentKey,askerNameKey};		
		if(request.getUnAnswered()){
			queryName=DataQueryConstants.QUERY_UNANSWERED_QUESTION;
			queryCountName = DataQueryConstants.QUERY_UNANSWERED_QUESTION_COUNT;
			args= new String[] {};
			params = new Object[]{};
		}
		else if(questionType>0){
			queryName=DataQueryConstants.QUERY_QUESTION_BY_TYPE;
			queryCountName = DataQueryConstants.QUERY_QUESTION_COUNT_BY_TYPE;
			args= new String[] {"questionType","content","askerName" };
			params = new Object[]{questionType,contentKey,askerNameKey};
		}
		List<?> resultList = this.dbManager.getGameDbService().findByNamedQueryAndNamedParam(
				queryName,
				args,
				params,
				request.getMaxResult(),
				request.getStart());
		QueryQuestionListResponse.Builder responseBuilder = QueryQuestionListResponse.newBuilder();
		if(resultList!=null && !resultList.isEmpty()){
			for(int i=0;i<resultList.size();i++){
				QuestionEntity questionEntity = (QuestionEntity)resultList.get(i);
				QuestionInfo questionInfo = convertToQuestionInfo(questionEntity);
				responseBuilder.addQuestion(questionInfo);
			}
		}
		List<?> countList = this.dbManager.getGameDbService().findByNamedQueryAndNamedParam(
				queryCountName,
				args,
				params,
				request.getMaxResult(),
				request.getStart());
		responseBuilder.setTotalCount((Long)countList.get(0));
		done.run(responseBuilder.build());

	}	

	

	@Override
	public void answerQuestion(RpcController controller, AnswerQuestionRequest request,
			RpcCallback<AnswerQuestionResponse> done) {
		QuestionInfo questionInfo = request.getQuestionInfo();
		QuestionEntity entity = convertToEntity(questionInfo);
		this.dbManager.getGameDbService().insert(entity);
		done.run(AnswerQuestionResponse.newBuilder().build());
	}
	
	/**
	 * 将数据库实体转换为传输对象
	 * @param entity
	 * @return
	 */
	private QuestionInfo convertToQuestionInfo(QuestionEntity entity) {
		QuestionInfo.Builder questionInfo = QuestionInfo.newBuilder();
		questionInfo.setAskerId(entity.getAskerId());
		questionInfo.setAskerName(entity.getAskerName());
		if (entity.getAskTime() != null) {
			questionInfo.setAskTime(entity.getAskTime().getTime());
		}
		questionInfo.setContent(entity.getContent());
		questionInfo.setId(entity.getId());
		questionInfo.setQuestionId(entity.getQuestionId());
		questionInfo.setQuestionType(entity.getQuestionType());
//		questionInfo.setReplyContent(entity.getReplyContent());
//		if (entity.getReplayTime() != null) {
//			questionInfo.setReplyTime(entity.getReplayTime().getTime());
//		}
		return questionInfo.build();
	}
	
	/**
	 * 将传输对象转换为数据库实体
	 * @param questionInfo
	 * @return
	 */
	private QuestionEntity convertToEntity(QuestionInfo questionInfo){
		QuestionEntity entity = new QuestionEntity();
		entity.setAskerId(questionInfo.getAskerId());
		entity.setAskerName(questionInfo.getAskerName());
		entity.setAskTime(new Date(questionInfo.getAskTime()));
		entity.setContent(questionInfo.getContent());
		entity.setId(questionInfo.getId());
		entity.setQuestionId(questionInfo.getQuestionId());
		entity.setQuestionType(questionInfo.getQuestionType());
//		entity.setReplyContent(questionInfo.getReplyContent());
//		entity.setReplayTime(new Date(questionInfo.getReplyTime()));
		return entity;
	}

}
