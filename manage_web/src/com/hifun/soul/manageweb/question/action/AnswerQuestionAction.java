package com.hifun.soul.manageweb.question.action;

import java.text.MessageFormat;
import java.util.Date;
import java.util.List;

import org.apache.struts2.ServletActionContext;

import com.google.protobuf.ServiceException;
import com.hifun.soul.manageweb.Managers;
import com.hifun.soul.manageweb.action.BaseAction;
import com.hifun.soul.manageweb.log.ManageLogType;
import com.hifun.soul.manageweb.log.ManageLogger;
import com.hifun.soul.manageweb.permission.PermissionType;
import com.hifun.soul.manageweb.question.QuestionModel;
import com.hifun.soul.proto.services.Services.AnswerQuestionRequest;
import com.hifun.soul.proto.services.Services.QuestionRpcService;
import com.opensymphony.xwork2.ActionContext;

/**
 * 回答玩家反馈
 * @author magicstone
 *
 */
public class AnswerQuestionAction extends BaseAction {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3668933565051865969L;
	
	private QuestionModel questionModel = new QuestionModel();
	private QuestionModel answerModel ;
	
	public QuestionModel getQuestionModel() {
		return questionModel;
	}

	public void setQuestionModel(QuestionModel questionModel) {
		this.questionModel = questionModel;
	}

	public QuestionModel getAnswerModel() {
		return answerModel;
	}

	public void setAnswerModel(QuestionModel answerModel) {
		this.answerModel = answerModel;
	}
	
	public String answerQuestionForward(){
		@SuppressWarnings("unchecked")
		List<QuestionModel> questionList = (List<QuestionModel>)ActionContext.getContext().getSession().get("questionList");
		String id = ServletActionContext.getRequest().getParameter("id");
		if(questionList==null){
			return INPUT;
		}
		for(QuestionModel model : questionList){
			if(String.valueOf(model.getId()).equals(id)){
				this.questionModel = model;
				ActionContext.getContext().getSession().put("questionModel", questionModel);
				break;
			}
		}
		ActionContext.getContext().getSession().remove("questionList");
		return "success";
	}

	public String answerQuestion() throws ServiceException{
		questionModel = (QuestionModel)ActionContext.getContext().getSession().get("questionModel");
		answerModel.setQuestionId(questionModel.getQuestionId());
		answerModel.setQuestionType(questionModel.getQuestionType());
		answerModel.setAskTime(new Date());
		answerModel.setAskerId(0);
		answerModel.setAskerName("GM");
		QuestionRpcService.BlockingInterface questionService = Managers.getServiceManager().getQuestionService();
		AnswerQuestionRequest.Builder requestBuilder = AnswerQuestionRequest.newBuilder();
		requestBuilder.setQuestionInfo(answerModel.convertToQuestionInfo());
		questionService.answerQuestion(null, requestBuilder.build());
		String param = MessageFormat.format("questionId:{0},type:{1},content:{2}", answerModel.getQuestionId(),answerModel.getQuestionType(),answerModel.getContent());
		ManageLogger.log(ManageLogType.SEND_MAIL,PermissionType.SEND_MAIL, param, true);
		return "success";
	}
	
	public void validateAnswerQuestion(){
		if(questionModel==null){
			this.addFieldError("questionModel", "没有提问者信息");
		}
		if(answerModel.getContent()==null ||answerModel.getContent().trim().length()==0){
			this.addFieldError("answerModel.content", "回复内容不能为空");
		}
	}
}
