package com.check;

import java.util.List;

import com.check.job.HistoryCheckJob;
import com.sql.dao.CDataBaseDao;
import com.util.AppContextUtil;

public class CheckByHistory {
	public static void main(String[] args) {
		HistoryCheckJob job=new HistoryCheckJob();

		CDataBaseDao dao = AppContextUtil.getContext().getBean(CDataBaseDao.class);
		List<Long> ids = dao.getAllId();
		
	
		job.load(ids);
	}
}
