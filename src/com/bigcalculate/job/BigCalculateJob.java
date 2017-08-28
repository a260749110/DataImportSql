package com.bigcalculate.job;

import java.util.ArrayList;
import java.util.List;

import com.sql.dao.CDataBaseDao;
import com.util.AppContextUtil;

public class BigCalculateJob {
	private List<Long> allIds;

	public void init() {

		allIds = loadIds();
	}

	private List<Long> loadIds() {
		List<Long> list = new ArrayList<>();
		CDataBaseDao dao = AppContextUtil.getContext().getBean(CDataBaseDao.class);
		List<Long> ids = dao.getAllId();
		for (Long id : ids) {
			if (id == 399001L || id > 100000)
				continue;
			list.add(id);
		}
		return list;
	}

}
