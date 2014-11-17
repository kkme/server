package com.hifun.soul.manageweb.account.action;

import java.util.List;

import org.apache.struts2.ServletActionContext;

import com.google.protobuf.ServiceException;
import com.hifun.soul.manageweb.Managers;
import com.hifun.soul.manageweb.account.AccountModel;
import com.hifun.soul.manageweb.action.BaseAction;
import com.hifun.soul.manageweb.log.ManageLogType;
import com.hifun.soul.manageweb.log.ManageLogger;
import com.hifun.soul.manageweb.permission.PermissionType;
import com.hifun.soul.proto.data.entity.Entity.Account;
import com.hifun.soul.proto.services.Services.AccountRpcService;
import com.hifun.soul.proto.services.Services.GetAccountByIdRequest;
import com.hifun.soul.proto.services.Services.GetAccountByIdResponse;
import com.hifun.soul.proto.services.Services.GetAccountByNameRequest;
import com.hifun.soul.proto.services.Services.GetAccountByNameResponse;
import com.hifun.soul.proto.services.Services.GetAccountListRequest;
import com.hifun.soul.proto.services.Services.GetAccountListResponse;
import com.hifun.soul.proto.services.Services.LockAccountRequest;
import com.hifun.soul.proto.services.Services.LockAccountResponse;
import com.hifun.soul.proto.services.Services.UnlockAccountRequest;
import com.hifun.soul.proto.services.Services.UpdateAccountInfoRequest;

/**
 * 账号;
 * 
 * @author crazyjohn
 * 
 */
public class AccountAction extends BaseAction {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private AccountModel accountInfo;
	private long totalCount=0;
	private int currentPage=1;

	public AccountModel getAccountInfo() {
		return accountInfo;
	}

	public void setAccountInfo(AccountModel accountInfo) {
		this.accountInfo = accountInfo;
	}

	public long getTotalCount() {
		return totalCount;
	}

	public void setTotalCount(long totalCount) {
		this.totalCount = totalCount;
	}	

	public int getCurrentPage() {
		return currentPage;
	}

	public void setCurrentPage(int currentPage) {
		this.currentPage = currentPage;
	}

	public String queryAccount() throws Exception {
		String userName = ServletActionContext.getRequest()
				.getParameter("name");
		if (userName != null && !"".equals(userName)) {
			return getAccountByName(userName);
		}
		String id = ServletActionContext.getRequest().getParameter("pid");
		if (id != null && !"".equals(id)) {
			return getAccountById(Long.parseLong(id));
		}
		currentPage = getPageFromRequest();
		return getAccountList(currentPage);
	}

	private int getPageFromRequest() {
		return ServletActionContext.getRequest().getParameter("page") == null ? 1
				: Integer.parseInt(ServletActionContext.getRequest()
						.getParameter("page"));
	}

	/**
	 * 查询转向;
	 * 
	 * @return
	 * @throws Exception
	 */
	public String queryAccountForward() throws Exception {
		return "queryMain";
	}

	private String getAccountList(int page) throws ServiceException {
		AccountRpcService.BlockingInterface accountService = Managers
				.getServiceManager().getAccountService();
		GetAccountListResponse response = accountService.getAccountList(null,
				GetAccountListRequest.newBuilder().setStart((page - 1) * 20)
						.setMaxResult(20).build());
		List<Account> accountList = response.getAccountList();
		totalCount = response.getTotalCount();
		boolean result = accountList != null;
		ManageLogger.log(ManageLogType.ACCOUNT_QUERY_ACCOUNT,PermissionType.ACCOUNT_QUERY_ACCOUNT, "page:"+page, result);
		if (!result) {
			putErrorInfoToRequest("没有账号信息!");
			return "error";
		}		
		ServletActionContext.getRequest().setAttribute("accountList",
				accountList);
		return "accountList";
	}

	private String getAccountById(long id) throws ServiceException {
		AccountRpcService.BlockingInterface accountService = Managers
				.getServiceManager().getAccountService();
		GetAccountByIdResponse response = accountService.getAccountById(null,
				GetAccountByIdRequest.newBuilder().setId(id).build());
		ManageLogger.log(ManageLogType.ACCOUNT_QUERY_ACCOUNT,PermissionType.ACCOUNT_QUERY_ACCOUNT, "id:"+id, response.getResult());
		if (!response.getResult()) {
			putErrorInfoToRequest("没有指定ID的账号信息!");
			return "error";
		}
		this.accountInfo = new AccountModel(response.getAccount());	
		ServletActionContext.getContext().getSession().put("account", accountInfo);
		String edit = ServletActionContext.getRequest().getParameter("edit");
		if(edit != null && edit.equals("1")){
			return "accountEdit";
		}
		else{
			return "accountDetail";
		}
	}

	private String getAccountByName(String userName) throws ServiceException {
		AccountRpcService.BlockingInterface accountService = Managers
				.getServiceManager().getAccountService();
		GetAccountByNameResponse response = accountService.getAccountByName(
				null, GetAccountByNameRequest.newBuilder()
						.setUserName(userName).build());
		ManageLogger.log(ManageLogType.ACCOUNT_QUERY_ACCOUNT,PermissionType.ACCOUNT_QUERY_ACCOUNT, "userName:"+userName, response.getResult());
		if (!response.getResult()) {
			putErrorInfoToRequest("没有指定账户名的账号信息!");
			return "error";
		}
		this.accountInfo = new AccountModel(response.getAccount());
		ServletActionContext.getContext().getSession().put("account", accountInfo);
		return "accountDetail";
	}

	public String lockAccount() throws Exception {
		String id = ServletActionContext.getRequest().getParameter("pid");
		if (id == null || "".equals(id)) {
			return "error";
		}
		AccountRpcService.BlockingInterface accountService = Managers
				.getServiceManager().getAccountService();
		LockAccountResponse response = accountService.lockAccount(
				null,
				LockAccountRequest.newBuilder()
						.setPassportId(Long.parseLong(id)).build());
		ManageLogger.log(ManageLogType.ACCOUNT_LOCK_ACCOUNT,PermissionType.ACCOUNT_LOCK_ACCOUNT, "id:"+id, response.getResult());
		if (!response.getResult()) {
			return "error";
		}
		return "accountList";
	}

	/**
	 * 解锁账号;
	 * 
	 * @return
	 * @throws Exception
	 */
	public String unlockAccount() throws Exception {
		String id = ServletActionContext.getRequest().getParameter("pid");
		if (id == null || "".equals(id)) {
			return "error";
		}
		AccountRpcService.BlockingInterface accountService = Managers
				.getServiceManager().getAccountService();
		accountService.unlockAccount(null, UnlockAccountRequest.newBuilder()
				.setPassportId(Long.parseLong(id)).build());
		ManageLogger.log(ManageLogType.ACCOUNT_UNLOCK_ACCOUNT,PermissionType.ACCOUNT_UNLOCK_ACCOUNT, "id:"+id, true);
		return "accountList";
	}

	public String lockAccountForward() throws Exception {
		return "lockAccountForward";
	}
	
	public String updateAccountInfo() throws ServiceException{
		if(accountInfo == null){
			return "error";
		}
		AccountModel sessionModel = (AccountModel)ServletActionContext.getContext().getSession().get("account");
		if(sessionModel == null){
			return "error";
		}
		accountInfo.setPassportId(sessionModel.getPassportId());
		accountInfo.setPassword(sessionModel.getPassword());		
		accountInfo.setState(sessionModel.getState());
		accountInfo.setUserName(sessionModel.getUserName());		
		AccountRpcService.BlockingInterface accountService = Managers
				.getServiceManager().getAccountService();
		accountService.updateAccountInfo(null, UpdateAccountInfoRequest.newBuilder().setAccountInfo(accountInfo.toAccountBuiler()).build());
		return "success";
	}

}
