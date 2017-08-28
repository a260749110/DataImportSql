package com.buy;

import java.util.ArrayList;
import java.util.List;

import com.alibaba.fastjson.JSON;
import com.check.EcheckType;
import com.check.cells.AllHistory;
import com.check.cells.History;
import com.check.cells.LycjssFlagData;
import com.check.job.LycjssFlagJob;
import com.sql.dao.CDataBaseDao;
import com.sql.dao.CDataBaseResultDao;
import com.sql.domain.CDataBasePo;
import com.sql.domain.CDataResultPo;
import com.sql.domain.CDataResultPoPK;
import com.util.AppContextUtil;

public class Buy {

	public String date;
	public double allMoney;
	public double baseBuy;
	public double nowMoney;
	public List<LycjssFlagData> datas;
	public double size = 20;

	public Buy(String date, double allMoney, double baseBuy, double nowMoney) {
		super();
		this.date = date;
		this.allMoney = allMoney;
		this.baseBuy = baseBuy; 
		this.nowMoney = nowMoney;

		init();
	}

	public static void main(String[] args) {
		// CheckMain.refresh();

		Buy buy = new Buy("2017/08/18", 200713, 10000, 22419);
		buy.run();
	}

	private void init() {
		CDataBaseDao dao = AppContextUtil.getContext().getBean(CDataBaseDao.class);
		datas = new ArrayList<>();
		List<CDataBasePo> pos = dao.findByDate(date);
		for (int i = 0; i < pos.size(); i++) {

			LycjssFlagData data = JSON.parseObject(pos.get(i).getDataBase(), LycjssFlagData.class);
			data.setDate(pos.get(i).getId().getDate());
			data.setId(pos.get(i).getId().getId());
			datas.add(data);
		}
	}

	public List<TriData> triDatas = new ArrayList<>();
	public List<History> trihDatas = new ArrayList<>();
	public void run() {
		CDataBaseResultDao dao = AppContextUtil.getContext().getBean(CDataBaseResultDao.class);
		CDataBaseDao dataDao = AppContextUtil.getContext().getBean(CDataBaseDao.class);
		for (LycjssFlagData data : datas) {
			// System.err.println("id:"+data.getId());
			LycjssFlagData bfData = null;
	
			if (data.getId() > 300000)
				continue;
			try {
				bfData = getBF(dataDao, data.getId(), date);
		
			} catch (Exception e) {
				System.err.println("can no read: " + data.getId());
				continue;
			}
			if (LycjssFlagJob.canBuy(data, bfData)) {
				CDataResultPoPK pk = new CDataResultPoPK();
				pk.setId(data.getId());
				pk.setType(EcheckType.LYCJSS_FLAG.toString());
				CDataResultPo po = dao.findOne(pk);
				if (po == null) {
					System.err.println("can not find" + data.getId());
					continue;
				}
				TriData tdata = new TriData();
				tdata.id = data.getId();
				tdata.score = po.getScore();
				
				
				History h=new History();
				h.setId(data.getId());
				h.setScore(po.getScore());
				h.bf=data;
				h.bbf=bfData;
				if (LycjssFlagJob.canBuy(tdata.score)) {
					triDatas.add(tdata);
					trihDatas.add(h);
				}

			}
		}
		triDatas.sort((a, b) -> {
			return a.score > b.score ? 1 : a.score == b.score ? 0 : -1;
		});
		
		List<History> buyList=AllHistory.sortList(trihDatas, 9999);
		for (History h : buyList) {
			System.err.println(h.getId());
		}
//		for (TriData d : triDatas) {
//			System.err.println(d.id);
//		}

	}

	private LycjssFlagData getBF(CDataBaseDao dataDao, long id, String date) {
		CDataBasePo po = dataDao.findBfByIdAndDate(id, date);

		LycjssFlagData result = JSON.parseObject(po.getDataBase(), LycjssFlagData.class);
		result.setDate(po.getId().getDate());
		result.setId(po.getId().getId());

		return result;
	}

	public static class TriData {

		long id;
		double score;
	}
}
