package com.check;


import java.util.List;

import com.check.job.LycjssFlagJob;
import com.sql.dao.CDataBaseDao;
import com.util.AppContextUtil;

public class CheckMain {
	public static void main(String[] args) {
		CDataBaseDao dao=AppContextUtil.getContext().getBean(CDataBaseDao.class);
		List<Long> ids=dao.getAllId();
		for (int i = 0; i < ids.size(); i++) {
			LycjssFlagJob job=new LycjssFlagJob();
			job.run(Long.valueOf(ids.get(i)+"").longValue());
		}
	}

}
