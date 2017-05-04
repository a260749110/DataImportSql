package com.check;

import java.text.ParseException;
import java.util.List;

import com.check.job.LycjssFlagJob;
import com.sql.dao.CDataBaseDao;
import com.util.AppContextUtil;

public class CheckMain {
	public static void main(String[] args) {
		refresh();
		try {
			LycjssFlagJob.allHistory.toSql();
		} catch (ParseException e) {
			e.printStackTrace();
		}
		System.err.println("success:" + LycjssFlagJob.success + "   unSuccess:" + LycjssFlagJob.unSuccess);
	}

	public static void refresh() {
		CDataBaseDao dao = AppContextUtil.getContext().getBean(CDataBaseDao.class);
		List<Long> ids = dao.getAllId();
		for (int i = 0; i < ids.size(); i++) {
			long id = Long.valueOf(ids.get(i) + "").longValue();
			if (id == 399001L)
				continue;
			LycjssFlagJob job = new LycjssFlagJob();
			job.run(id);
		}
	}
}
