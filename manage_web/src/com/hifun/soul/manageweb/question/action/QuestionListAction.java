package com.hifun.soul.manageweb.question.action;

import java.util.ArrayList;
import java.util.List;

import com.google.protobuf.ServiceException;
import com.hifun.soul.manageweb.Managers;
import com.hifun.soul.manageweb.action.BaseAction;
import com.hifun.soul.manageweb.common.PagingInfo;
import com.hifun.soul.manageweb.log.ManageLogType;
import com.hifun.soul.manageweb.log.ManageLogger;
import com.hifun.soul.manageweb.permission.PermissionType;
import com.hifun.soul.manageweb.question.QuestionModel;
import com.hifun.soul.proto.services.Services.QueryQuestionListRequest;
import com.hifun.soul.proto.services.Services.QueryQuestionListResponse;
import com.hifun.soul.proto.services.Services.QuestionInfo;
import com.hifun.soul.proto.services.Services.QuestionRpcService;
import com.opensymphony.xwork2.ActionContext;

/**
 * 查询玩家反馈信息
 * @author magicstone
 *
 */
public class QuestionListAction extends BaseAction{

	/**
	 * 
	 */
	private static final long serialVersionUID = -6444203151978845662L;
	
	private List<QuestionModel> questionList = new ArrayList<QuestionModel>();
	private int questionType;
	private String contentKey="";
	private String askerNameKey="";
	private boolean onlyUnAnswered=false;
	private PagingInfo pagingInfo;
	
	public QuestionListAction(){
		pagingInfo = new PagingInfo();
		pagingInfo.setPageIndex(0);
		pagingInfo.setPageSize(20);
	}
	
	public List<QuestionModel> getQuestionList() {
		return questionList;
	}
	public void setQuestionList(List<QuestionModel> questionList) {
		this.questionList = questionList;
	}
	public int getQuestionType() {
		return questionType;
	}
	public void setQuestionType(int questionType) {
		this.questionType = questionType;
	}
	public String getContentKey() {
		return contentKey;
	}
	public void setContentKey(String contentKey) {
		this.contentKey = contentKey;
	}
	public String getAskerNameKey() {
		return askerNameKey;
	}
	public void setAskerNameKey(String askerNameKey) {
		this.askerNameKey = askerNameKey;
	}
	public boolean isOnlyUnAnswered() {
		return onlyUnAnswered;
	}

	public void setOnlyUnAnswered(boolean onlyUnAnswered) {
		this.onlyUnAnswered = onlyUnAnswered;
	}

	public PagingInfo getPagingInfo() {
		return pagingInfo;
	}
	public void setPagingInfo(PagingInfo pagingInfo) {
		this.pagingInfo = pagingInfo;
	}
	
	public String queryQuestionList() throws ServiceException{
		this.questionList.clear();
		QuestionRpcService.BlockingInterface service = Managers.getServiceManager().getQuestionService();
		QueryQuestionListRequest.Builder requestBuilder = QueryQuestionListRequest.newBuilder();
		requestBuilder.setAskerName(askerNameKey);
		requestBuilder.setContent(contentKey);
		requestBuilder.setQuestionType(questionType);
		requestBuilder.setUnAnswered(onlyUnAnswered);
		requestBuilder.setMaxResult(pagingInfo.getPageSize());
		requestBuilder.setStart(pagingInfo.getPageIndex() * pagingInfo.getPageSize());
		QueryQuestionListResponse response = service.queryQuestionList(null, requestBuilder.build());
		for(QuestionInfo questionInfo : response.getQuestionList()){
			questionList.add(new QuestionModel(questionInfo));
		}
		ManageLogger.log(ManageLogType.QUERY_QUESTION_LIST,PermissionType.QUERY_QUESTION_LIST, "", true);
		ActionContext.getContext().getSession().put("questionList", questionList);
		return "success";
	}
	
}
