package com.check.job;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.alibaba.fastjson.JSON;
import com.check.CheckJobsBase;
import com.check.EcheckType;
import com.check.cells.LycjssFlagData;
import com.sql.dao.CDataBaseDao;
import com.sql.dao.CDataBaseResultDao;
import com.sql.domain.CDataBasePo;
import com.sql.domain.CDataResultPo;
import com.sql.domain.CDataResultPoPK;
import com.util.AppContextUtil;

public class LycjssFlagJob extends CheckJobsBase {

	List<LycjssFlagData> datas;
	double result = 1;
	double buy = 0;
	double loss = -0.15f;
	double win = 0.20f;
	Other other = new Other();
	int useDay;
	@Override
	public void run(long id) {
		init(id);
		iteration();
		CDataBaseResultDao dao = AppContextUtil.getContext().getBean(CDataBaseResultDao.class);
		CDataResultPo po = new CDataResultPo();
		CDataResultPoPK pk = new CDataResultPoPK();
		pk.setId(id);
		pk.setType(EcheckType.LYCJSS_FLAG.toString());
		po.setId(pk);
		po.setAvg((useDay==0)?0:(result-1) /(double)useDay);
		po.setResult(result);

		po.setSize(datas.size());
		po.setOther(JSON.toJSONString(other));
		dao.save(po);
		System.err.println("id :" + id + "  result:" + result + "  avg:" + po.getAvg());
	}

	private void iteration() {
		int count = 0;
		History history = null;
		int s = 0;
		for (int i = 0; i < datas.size(); i++) {
			LycjssFlagData data = datas.get(i);
			if (data.getLycjssFlags() > 0) {
				if (history !=null) {

				} else {
					history = new History();
					s=i;
					buy = data.getClose();
					history.start = dateFormat.format(data.getDate());
					history.startMoney = data.getClose();
					count++;
				}
			} else {
				if (history !=null) {
					double dif = (data.getClose() - buy) / buy;
					if (dif > win || dif < loss) {
						result *= 1 + dif;
						useDay+=i-s;
						history.end = dateFormat.format(data.getDate());
						history.endMoney = data.getClose();
						other.historys.add(history);
						history=null;
					}

				}
			}
		}
		System.err.println("count:" + count);
	}

	private void init(long id) {
		CDataBaseDao dao = AppContextUtil.getContext().getBean(CDataBaseDao.class);
		datas = new ArrayList<>();
		List<CDataBasePo> pos = dao.findById(id);
		for (int i = 60; i < pos.size(); i++) {

			LycjssFlagData data = JSON.parseObject(pos.get(i).getDataBase(), LycjssFlagData.class);
			data.setDate(pos.get(i).getId().getDate());
			datas.add(data);
		}
	}

	public class Other {
		private List<History> historys = new ArrayList<>();

		public List<History> getHistorys() {
			return historys;
		}

		public void setHistorys(List<History> historys) {
			this.historys = historys;
		}
	}
	private static SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd");
	public class History {
		private String start;
		private String end;
		private double startMoney;
		private double endMoney;

		public String getStart() {
			return start;
		}

		public void setStart(String start) {
			this.start = start;
		}

		public String getEnd() {
			return end;
		}

		public void setEnd(String end) {
			this.end = end;
		}

		public double getStartMoney() {
			return startMoney;
		}

		public void setStartMoney(double startMoney) {
			this.startMoney = startMoney;
		}

		public double getEndMoney() {
			return endMoney;
		}

		public void setEndMoney(double endMoney) {
			this.endMoney = endMoney;
		}
	}

}
