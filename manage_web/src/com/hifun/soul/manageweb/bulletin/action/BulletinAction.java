package com.hifun.soul.manageweb.bulletin.action;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import org.apache.struts2.ServletActionContext;
import com.google.protobuf.ServiceException;
import com.hifun.soul.manageweb.Managers;
import com.hifun.soul.manageweb.action.BaseAction;
import com.hifun.soul.manageweb.bulletin.BulletinModel;
import com.hifun.soul.manageweb.common.PagingInfo;
import com.hifun.soul.manageweb.log.ManageLogType;
import com.hifun.soul.manageweb.log.ManageLogger;
import com.hifun.soul.manageweb.permission.PermissionType;
import com.hifun.soul.proto.services.Services.AddBulletinRequest;
import com.hifun.soul.proto.services.Services.AddBulletinResponse;
import com.hifun.soul.proto.services.Services.Bulletin;
import com.hifun.soul.proto.services.Services.BulletinRpcService;
import com.hifun.soul.proto.services.Services.GetBulletinListRequest;
import com.hifun.soul.proto.services.Services.GetBulletinListResponse;
import com.hifun.soul.proto.services.Services.RemoveBulletinRequest;
import com.hifun.soul.proto.services.Services.RemoveBulletinResponse;
import com.hifun.soul.proto.services.Services.UpdateBulletinRequest;
import com.hifun.soul.proto.services.Services.UpdateBulletinResponse;
import com.opensymphony.xwork2.ActionContext;

/**
 * 公告相关的控制器
 * 
 * @author magicstone
 * 
 */
public class BulletinAction extends BaseAction {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private List<BulletinModel> bulletins = new ArrayList<BulletinModel>();
	private BulletinModel queryCondition;
	private BulletinModel bulletinModel;
	private PagingInfo pagingInfo;
	private Date publishTime;
	private boolean editState = false;
	private static DateFormat dateTimeFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	private static DateFormat timeFormat = new SimpleDateFormat("HH:mm:ss");

	public BulletinAction() {
		queryCondition = new BulletinModel();
		queryCondition.setContent("");
		pagingInfo = new PagingInfo();
		pagingInfo.setPageIndex(0);
		pagingInfo.setPageSize(20);
		pagingInfo.setTotalCount(0);
	}

	public PagingInfo getPagingInfo() {
		return pagingInfo;
	}

	public void setPagingInfo(PagingInfo pagingInfo) {
		this.pagingInfo = pagingInfo;
	}

	public List<BulletinModel> getBulletins() {
		return bulletins;
	}

	public void setBulletins(List<BulletinModel> bulletins) {
		this.bulletins = bulletins;
	}

	public BulletinModel getQueryCondition() {
		return queryCondition;
	}

	public void setQueryCondition(BulletinModel queryCondition) {
		this.queryCondition = queryCondition;
	}

	public BulletinModel getBulletinModel() {
		return bulletinModel;
	}

	public void setBulletinModel(BulletinModel bulletinModel) {
		this.bulletinModel = bulletinModel;
	}

	public Date getPublishTime() {
		return publishTime;
	}

	public void setPublishTime(Date publishTime) {
		this.publishTime = publishTime;
	}

	public boolean isEditState() {
		return editState;
	}

	public void setEditState(boolean editState) {
		this.editState = editState;
	}

	public String queryBulletinList() throws ServiceException {
		if (ActionContext.getContext().getSession().containsKey("bulletinList")) {
			ActionContext.getContext().getSession().remove("bulletinList");
		}
		BulletinRpcService.BlockingInterface bulletinService = Managers.getServiceManager().getBulletinService();
		GetBulletinListResponse response = bulletinService.getBulletinList(null, GetBulletinListRequest.newBuilder()
				.setStart(pagingInfo.getPageIndex() * pagingInfo.getPageSize()).setMaxResult(pagingInfo.getPageSize())
				.setContentKey(queryCondition.getContent()).setBulltinType(queryCondition.getBulletinType())
				.setValidState(queryCondition.getValidState()).build());
		List<Bulletin> bulletinList = response.getBulletinsList();
		String params = "contentKey:" + queryCondition.getContent();
		ManageLogger.log(ManageLogType.BULLETIN_LIST, PermissionType.BULLETIN_LIST, params, true);
		bulletins.clear();
		for (Bulletin bulletin : bulletinList) {
			bulletins.add(convertBuilderToModel(bulletin));
		}
		this.pagingInfo.setTotalCount(response.getTotalCount());
		ActionContext.getContext().getSession().put("bulletinList", bulletins);
		return "success";
	}

	/**
	 * 添加公告的转向操作
	 * 
	 * @return
	 */
	public String addBulletinForward() {
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		if (bulletinModel == null) {
			bulletinModel = new BulletinModel();
		}
		bulletinModel.setPublishTimeString(df.format(new Date()));
		return "success";
	}

	/**
	 * 添加公告
	 * 
	 * @return
	 * @throws ServiceException
	 */
	public String addBulletin() throws ServiceException {
		if (this.editState) {
			return updateBulletin();
		} else {
			BulletinRpcService.BlockingInterface bulletinService = Managers.getServiceManager().getBulletinService();
			Bulletin.Builder builder = convertModelToBuilder(bulletinModel);
			builder.setValid(true);
			builder.setCreateTime(new Date().getTime());
			builder.setLastPublishTime(builder.getPublishTime());
			AddBulletinRequest addRequest = AddBulletinRequest.newBuilder().setBulletin(builder).build();
			AddBulletinResponse response = bulletinService.addBulletin(null, addRequest);
			boolean result = response.getResult();
			String params = "id:" + bulletinModel.getId() + " type:" + bulletinModel.getBulletinType() + " content:"
					+ bulletinModel.getContent();
			ManageLogger.log(ManageLogType.BULLETIN_ADD, PermissionType.BULLETIN_ADD, params, result);
			if (result) {				
				return "success";
			} else {
				return "failed";
			}
		}
	}

	/**
	 * 移除有效公告
	 * 
	 * @return
	 * @throws ServiceException
	 */
	public String removeBulletin() throws ServiceException {
		String strId = ServletActionContext.getRequest().getParameter("id");
		int bulletinId = Integer.parseInt(strId);
		BulletinRpcService.BlockingInterface bulletinService = Managers.getServiceManager().getBulletinService();
		RemoveBulletinResponse response = bulletinService.removeBulletin(null, RemoveBulletinRequest.newBuilder()
				.setBulletinId(bulletinId).build());
		boolean result = response.getResult();
		ManageLogger.log(ManageLogType.BULLETIN_REMOVE, PermissionType.BULLETIN_REMOVE, "id:" + strId, result);
		if (result) {
			
			return "success";
		} else {
			return "failed";
		}
	}

	public String copyBulletin() {
		String strId = ServletActionContext.getRequest().getParameter("id");
		int bulletinId = Integer.parseInt(strId);
		@SuppressWarnings("unchecked")
		List<BulletinModel> bulletinList = (List<BulletinModel>) ActionContext.getContext().getSession()
				.get("bulletinList");
		boolean findFlag = false;
		for (BulletinModel model : bulletinList) {
			if (model.getId() == bulletinId) {
				findFlag = true;
				bulletinModel = model;
				bulletinModel.setPublishTimeString(dateTimeFormat.format(bulletinModel.getPublishTime()));
				bulletinModel.setStartTimeString(timeFormat.format(bulletinModel.getStartTime()));
				bulletinModel.setEndTimeString(timeFormat.format(bulletinModel.getEndTime()));
			}
		}
		if (findFlag) {
			return "success";
		} else {
			return "failed";
		}
	}

	public String editBulletin() {
		String strId = ServletActionContext.getRequest().getParameter("id");
		int bulletinId = Integer.parseInt(strId);
		@SuppressWarnings("unchecked")
		List<BulletinModel> bulletinList = (List<BulletinModel>) ActionContext.getContext().getSession()
				.get("bulletinList");
		boolean findFlag = false;
		for (BulletinModel model : bulletinList) {
			if (model.getId() == bulletinId) {
				findFlag = true;
				bulletinModel = model;
				bulletinModel.setPublishTimeString(dateTimeFormat.format(bulletinModel.getPublishTime()));
				bulletinModel.setStartTimeString(timeFormat.format(bulletinModel.getStartTime()));
				bulletinModel.setEndTimeString(timeFormat.format(bulletinModel.getEndTime()));
				editState = true;
			}
		}
		if (findFlag) {
			return "success";
		} else {
			return "failed";
		}
	}

	public String updateBulletin() throws ServiceException {
		BulletinRpcService.BlockingInterface bulletinService = Managers.getServiceManager().getBulletinService();
		Bulletin.Builder builder = convertModelToBuilder(bulletinModel);
		builder.setValid(true);
		builder.setLastPublishTime(builder.getPublishTime());
		UpdateBulletinRequest updateRequest = UpdateBulletinRequest.newBuilder().setBulletin(builder).build();
		UpdateBulletinResponse response = bulletinService.updateBulletin(null, updateRequest);
		boolean result = response.getResult();
		String params = "id:" + bulletinModel.getId() + " type:" + bulletinModel.getBulletinType() + " content:"
				+ bulletinModel.getContent();
		ManageLogger.log(ManageLogType.BULLETIN_ADD, PermissionType.BULLETIN_ADD, params, result);
		if (result) {
			return "success";
		} else {
			return "failed";
		}
	}

	/**
	 * 转换为传输对象
	 * 
	 * @param model
	 * @return
	 */
	private Bulletin.Builder convertModelToBuilder(BulletinModel model) {
		Bulletin.Builder builder = Bulletin.newBuilder();
		builder.setId(model.getId());
		builder.setBulletinType(model.getBulletinType());
		builder.setContent(model.getContent());
		if (model.getEndDate() != null) {
			builder.setEndDate(model.getEndDate().getTime());
		}
		if (model.getEndTime() != null) {
			builder.setEndTime(model.getEndTime().getTime());
		}
		if (model.getLastPublishTime() != null) {
			builder.setLastPublishTime(model.getLastPublishTime().getTime());
		}
		builder.setLevel(model.getLevel());
		builder.setPublishInterval(model.getPublishInterval());
		if (model.getPublishTime() != null) {
			builder.setPublishTime(model.getPublishTime().getTime());
		}
		builder.setShowTime(model.getShowTime());
		if (model.getStartDate() != null) {
			builder.setStartDate(model.getStartDate().getTime());
		}
		if (model.getStartTime() != null) {
			builder.setStartTime(model.getStartTime().getTime());
		}
		builder.setValid(model.getValidState() == 1);
		if (model.getCreateTime() != null) {
			builder.setCreateTime(model.getCreateTime().getTime());
		}
		return builder;
	}

	/**
	 * 转换为传输对象
	 * 
	 * @param model
	 * @return
	 */
	private BulletinModel convertBuilderToModel(Bulletin bulletin) {
		BulletinModel model = new BulletinModel();
		model.setId(bulletin.getId());
		model.setBulletinType(bulletin.getBulletinType());
		model.setContent(bulletin.getContent());
		model.setEndDate(new Date(bulletin.getEndDate()));
		model.setEndTime(new Date(bulletin.getEndTime()));
		model.setLastPublishTime(new Date(bulletin.getLastPublishTime()));
		model.setLevel(bulletin.getLevel());
		model.setPublishInterval(bulletin.getPublishInterval());
		model.setPublishTime(new Date(bulletin.getPublishTime()));
		model.setShowTime(bulletin.getShowTime());
		model.setStartDate(new Date(bulletin.getStartDate()));
		model.setStartTime(new Date(bulletin.getStartTime()));
		int validState = bulletin.getValid() == true ? 1 : 0;
		model.setValidState(validState);
		model.setCreateTime(new Date(bulletin.getCreateTime()));
		return model;
	}

	/**
	 * 验证输入信息的合法性
	 */
	public void validateUpdateBulletin() {
		bulletinValidate();
	}

	/**
	 * 验证输入信息的合法性
	 */
	public void validateAddBulletin() {
		bulletinValidate();
	}

	public void bulletinValidate() {
		if (bulletinModel == null) {
			this.addFieldError("bulletinEdit", "添加的公告对象没有实例化");
			return;
		}
		if (bulletinModel.getBulletinType() < 1 || bulletinModel.getBulletinType() > 3) {
			this.addFieldError("bulletinEdit.bulletinType", "公告类型错误");
		}
		if (bulletinModel.getContent() == null || bulletinModel.getContent().length() == 0) {
			this.addFieldError("bulletin.content", "公告内容不能为空");
		}

		DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String publishTimeString = bulletinModel.getPublishTimeString();
		if (publishTimeString == null || publishTimeString.length() == 0) {
			this.addFieldError("publishTime", "发布时间不能为空");
		} else {
			try {
				this.bulletinModel.setPublishTime(df.parse(publishTimeString));
			} catch (ParseException e) {
				this.addFieldError("publishTime", "发布时间输入格式错误");
				e.printStackTrace();
			}
		}
		if (bulletinModel.getBulletinType() != 1) {
			if (bulletinModel.getStartDate() == null) {
				this.addFieldError("startDate", "公告有效开始日期不能为空");
			}
			if (bulletinModel.getEndDate() == null) {
				this.addFieldError("endDate", "公告有效截止日期不能为空");
			}
		}
		if (bulletinModel.getBulletinType() == 3) {
			String startTimeString = bulletinModel.getStartTimeString();
			if (startTimeString != null && startTimeString.length() == 0) {
				this.addFieldError("startTime", "每日开始时间不能为空");
			} else {
				try {
					this.bulletinModel.setStartTime(df.parse("2012-12-20 " + startTimeString));
				} catch (ParseException e) {
					this.addFieldError("startTime", "每日开始时间输入格式错误");
					e.printStackTrace();
				}
			}

			String endTimeString = bulletinModel.getEndTimeString();
			if (endTimeString != null && endTimeString.length() == 0) {
				this.addFieldError("endTime", "每日截止时间不能为空");
			} else {
				try {
					this.bulletinModel.setEndTime(df.parse("2012-12-20 " + endTimeString));
				} catch (ParseException e) {
					this.addFieldError("endTime", "每日截止时间输入格式错误");
					e.printStackTrace();
				}
			}
			if (bulletinModel.getPublishInterval() <= 0) {
				this.addFieldError("publishInterval", "发布间隔时间不能小于或等于0");
			}
		}
	}

}
