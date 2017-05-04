package com.check.job;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import com.alibaba.fastjson.JSON;
import com.check.CheckJobsBase;
import com.check.DapanData;
import com.check.EcheckType;
import com.check.cells.AllHistory;
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
	static double resultAll = 1;
	double loss = -0.10;
	double win = 0.12f;
	Other other = new Other();
	int useDay = 0;
	int triCount = 0;
	public static long success = 0;
	public static long unSuccess = 0;
	private double score = 1;
	public static AllHistory allHistory = new AllHistory();
	public static Map<String, List<History>> historyMap = new HashMap<>();
	long id;

	@Override
	public void run(long id) {
		this.id = id;
		init(id);
		iteration();
		CDataBaseResultDao dao = AppContextUtil.getContext().getBean(CDataBaseResultDao.class);
		CDataResultPo po = new CDataResultPo();
		CDataResultPoPK pk = new CDataResultPoPK();
		pk.setId(id);
		pk.setType(EcheckType.LYCJSS_FLAG.toString());
		po.setId(pk);
		po.setAvg((useDay == 0) ? 0 : (result - 1) / (double) useDay);
		po.setResult(result);

		po.setSize(((triCount == 0) ? 0 : useDay / triCount));
		po.setOther(JSON.toJSONString(other));
		po.setScore(score);
		dao.save(po);

		System.out.println("id :" + id + "  result:" + result + "  avg:" + po.getAvg() + "  use:"
				+ ((triCount == 0) ? 0 : useDay / triCount) + "   resultAll:" + resultAll);
	}

	private void iteration() {
		int count = 0;
		List<History> histories = new ArrayList<>();
		List<History> removeList = new ArrayList<>();
		for (int i = 2; i < datas.size(); i++) {
			LycjssFlagData data = datas.get(i);
			// if (data.getLycjssFlags() > 0) {
			// if (data.getLycjdmiFlags() > 0) {
			if (data.getStart() <= 0.1)

			{
				histories.clear();
				continue;
			}
			LycjssFlagData bf = datas.get(i - 1);
			LycjssFlagData bbf = datas.get(i - 2);
			double change = data.getClose() / bf.getClose();
			if (change > 1.15 || change < 0.85) {
				System.err.println("erro:" + LycjssFlagJob.dateFormat.format(data.getDate()) + "  " + id);
				histories.clear();
				continue;
			}

			// if (datas.get(i - 1).getLycjdmiFlagsums() > 0 &&
			// DapanData.getInstance().canTri(datas.get(i - 1))) {
			if (bf.getLycjdmiFlagsums() > 0 && bf.getMacdMacd() > bbf.getMacdMacd()
					&& DapanData.getInstance().canTri(data)) {
				History history = new History();
				history.size = i;
				history.start = dateFormat.format(data.getDate());
				history.startMoney = data.getStart();
				histories.add(history);
				history.score = score;

			}
			{

				removeList.clear();
				for (History history : histories) {
					double difH = (data.getHigh() - history.startMoney) / history.startMoney;
					double difL = (data.getLow() - history.startMoney) / history.startMoney;
					double difC = (data.getClose() - history.startMoney) / history.startMoney;
					double difS = (data.getStart() - history.startMoney) / history.startMoney;
					double dif = 0;
					boolean okFlag = false;
					if (difS < difH) {
						if (difH > win) {
							double av = (difH + difS) / 2d;
							if (av > win)
								dif = av;
							else
								dif = win;
							okFlag = true;
							// System.err.println(data.getDate()+"difH"+"
							// "+difS);
						}
					}
					if (!okFlag) {
						if (difL <= loss) {
							dif = difC;
							okFlag = true;
						}
					}
					if (okFlag) {
						score += dif;
						if (canBuy(history.score)) {

							if (dif > 0) {
								success++;
							} else {
								unSuccess++;
							}
							result += dif;
							resultAll += dif;
							useDay += i - history.getSize();
							history.setSize(i - history.getSize());
							history.setId(id);
							triCount++;
							history.setDif(dif);
							history.setNowWin(result);
							history.end = dateFormat.format(data.getDate());
							history.endMoney = history.getStartMoney() * (1 + dif);
							removeList.add(history);
							other.historys.add(history);
							allHistory.addHistory(history);
							count++;
						} else {
							removeList.add(history);
						}
					}

				}
				histories.removeAll(removeList);
			}
		}
		System.out.println("count:" + count);
	}

	private void init(long id) {
		CDataBaseDao dao = AppContextUtil.getContext().getBean(CDataBaseDao.class);
		datas = new ArrayList<>();
		List<CDataBasePo> pos = dao.findById(id);
		for (int i = 15; i < pos.size(); i++) {

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

	private static Random random = new Random();

	public static boolean canBuy(LycjssFlagData now, LycjssFlagData bf) {
		return now.getLycjdmiFlagsums() > 0 && now.getMacdMacd() > bf.getMacdMacd()
				&& DapanData.getInstance().canTri(now);
	}

	public static boolean canBuy(double score) {
		if (score < 1) {
			double rand = random.nextDouble();
			rand = Math.pow(rand, 1);
			if (rand > score) {
				// System.out.println("can not buy:" + score + " " + rand);
				return false;
			}
		}
		return true;
	}

	public static SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd");

	public static class History {
		private long id;
		private String start;
		private String end;
		private double startMoney;
		private double endMoney;
		private int size;
		private double dif;
		private double nowWin;
		private double score = 1;

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

		public int getSize() {
			return size;
		}

		public void setSize(int size) {
			this.size = size;
		}

		public double getDif() {
			return dif;
		}

		public void setDif(double dif) {
			this.dif = dif;
		}

		public double getNowWin() {
			return nowWin;
		}

		public void setNowWin(double nowWin) {
			this.nowWin = nowWin;
		}

		public double getScore() {
			return score;
		}

		public void setScore(double score) {
			this.score = score;
		}

		public long getId() {
			return id;
		}

		public void setId(long id) {
			this.id = id;
		}
	}

}
