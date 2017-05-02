package com.check;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.alibaba.fastjson.JSON;
import com.check.cells.LycjssFlagData;
import com.check.job.LycjssFlagJob;
import com.sql.dao.CDataBaseDao;
import com.sql.domain.CDataBasePo;
import com.util.AppContextUtil;

public class DapanData {
	private static DapanData instance = new DapanData();
	private int size = 15;
	private List<LycjssFlagData> datas = new ArrayList<>();
	private Map<String, Boolean> flagsMap = new HashMap<>();

	private DapanData() {
		CDataBaseDao dao = AppContextUtil.getContext().getBean(CDataBaseDao.class);
		datas = new ArrayList<>();
		List<CDataBasePo> pos = dao.findById(399001);
		for (int i = 0; i < pos.size(); i++) {

			LycjssFlagData data = JSON.parseObject(pos.get(i).getDataBase(), LycjssFlagData.class);
			data.setDate(pos.get(i).getId().getDate());
			datas.add(data);
		}

	}

	public boolean canTri(LycjssFlagData data) {
		String date = LycjssFlagJob.dateFormat.format(data.getDate());
		if (1 > 0) {
			return true;
		}
		if (flagsMap.containsKey(date)) {
			return flagsMap.get(date);
		}

		for (int i = 0; i < datas.size(); i++) {
			LycjssFlagData dp = datas.get(i);
			if (dp.getDate().after(data.getDate())) {
				System.err.println("erro  date after :" + date);
				return false;
			}
			if (datas.get(i).getDate().compareTo(data.getDate()) == 0) {

				if (dp.getLycjssVpr() < dp.getLycjssVma()) {
					flagsMap.put(date, true);
					return true;
				} else {
					flagsMap.put(date, false);
					return false;
				}
			}
		}
		System.err.println("erro  date feature :" + date);
		return false;
	}

	public static DapanData getInstance() {

		return instance;
	}

}
