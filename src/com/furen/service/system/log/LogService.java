package com.furen.service.system.log;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;
import org.springframework.stereotype.Service;

import com.furen.dao.DaoSupport;
import com.furen.entity.Page;
import com.furen.entity.system.User;
import com.furen.util.Const;
import com.furen.util.PageData;

@Service("logService")
public class LogService {
	
	@Resource(name = "daoSupport")
	private DaoSupport dao;
	
	/**
	 * 机构列表
	 * @param pd
	 * @return
	 * @throws Exception
	 */
	public List<PageData> listLogs(Page page) throws Exception  {
		return (List<PageData>) dao.findForList("LogMapper.logslistPage", page);
	}
	
	public void insertOneLog(String menuUrl, int systemType, int operatorType, String remark){
		//1、准备log数据
		Subject currentUser = SecurityUtils.getSubject();  
		Session session = currentUser.getSession();
		User user = (User) session.getAttribute(Const.SESSION_USER);
		Map<String,Object> logParam = new HashMap<String,Object>();
		logParam.put("userName", user.getUSERNAME());
		logParam.put("systemType",systemType);
		logParam.put("operatorType",operatorType);
		logParam.put("menuUrl", menuUrl);
		logParam.put("remark", remark);
		logParam.put("userId", user.getUSER_ID());
		try {
			dao.save("LogMapper.insertOneLog", logParam);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
}
