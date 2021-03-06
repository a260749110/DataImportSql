package com.check.cells;

import java.text.DecimalFormat;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.check.job.LycjssFlagJob.History;
import com.sql.dao.CHistoryInOutDao;
import com.sql.domain.CHistoryBuySellPK;
import com.sql.domain.CHistoryBuySellPo;
import com.sql.domain.CHistoryInOutPo;
import com.util.AppContextUtil;
import com.util.DateHelper;
import com.util.MathHelper;
import com.util.MathHelper.IGetValue;

public class AllHistory {
	List<History> historyList = new ArrayList<>();
	public Map<String, CHistoryInOutPo> historyMaps = new HashMap<String, CHistoryInOutPo>();
	public static double INIT_MONEY = 20; /// 112 :30倍
	public double targetSize = 20;
	public double defMoney = INIT_MONEY;
	/**
	 * 剩余金钱
	 */
	private double surplusMoney = defMoney;
	public double moneyALL = defMoney;
	public double upgradeRate = 1;
	public double upgradeMup = 7;
	private Date startDate = null;
	private Date endDate = null;
	private Map<String, Double> yearWinMap = new HashMap<>();
	public List<NewHistory> historybuyList = new ArrayList<>();
	Map<String, Double> historyWinloseMap = new HashMap<>();
	public boolean saveFlag = false;
	public boolean showFlag = true;
	public boolean saveHistoryFlag = false;
	public double groupUp = 0.0d;
	public double groupAll = 0;

	public void init() {
		historyMaps.clear();
		historybuyList.clear();
		historyWinloseMap.clear();
		surplusMoney = defMoney;
		moneyALL = defMoney;
		yearInfo.setLength(0);

	}

	public void addHistory(History history) {
		historyList.add(history);

	}

	public void addHistorys(List<History> historys) {
		historyList.addAll(historys);

	}

	public AllHistory() {
		try {
			startDate = DateHelper.dateFormat.parse("2012/1/1");
			endDate = DateHelper.dateFormat.parse("2017/1/1");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public StringBuffer yearInfo = new StringBuffer();

	public ConfigAndReesult toSql() throws ParseException {
		ConfigAndReesult result = new ConfigAndReesult();
		double startM = moneyALL;
		double success = 0;
		double unsuccess = 0;
		double nowSize = startM;
		List<CHistoryInOutPo> poList = new ArrayList<>();
		for (History history : historyList) {
			CHistoryInOutPo spo = historyMaps.get(history.getStart());
			CHistoryInOutPo epo = historyMaps.get(history.getEnd());
			double winLost = 0;
			if (historyWinloseMap.containsKey(history.getStart())) {
				winLost = historyWinloseMap.get(history.getStart());

			}
			winLost += history.getDif();
			historyWinloseMap.put(history.getStart(), winLost);
			if (spo == null) {
				spo = new CHistoryInOutPo();
				spo.setMarkDate(DateHelper.dateFormat.parse(history.getStart()));
				historyMaps.put(history.getStart(), spo);
				poList.add(spo);
			}
			if (epo == null) {
				epo = new CHistoryInOutPo();
				epo.setMarkDate(DateHelper.dateFormat.parse(history.getEnd()));
				historyMaps.put(history.getEnd(), epo);
				poList.add(epo);

			}
			spo.setMoneyOut(spo.getMoneyOut() - 1);
			epo.setMoneyInAll(epo.getMoneyInAll() + 1);
			epo.setMoneyIn(epo.getMoneyIn() + history.getDif() + 1);

		}

		poList.sort(new Comparator<CHistoryInOutPo>() {

			@Override
			public int compare(CHistoryInOutPo o1, CHistoryInOutPo o2) {
				// TODO Auto-generated method stub
				return o1.getMarkDate().before(o2.getMarkDate()) ? -1 : 1;
			}
		});
		double allCount = 0;
		double outCount = 0;
		for (CHistoryInOutPo po : poList) {
			allCount += po.getMoneyIn() + po.getMoneyOut();
			po.setMoneyAll(allCount);
			outCount += po.getMoneyOut() + po.getMoneyInAll();
			po.setMoneyOutAll(outCount);
		}
		CHistoryInOutDao dao = AppContextUtil.getContext().getBean(CHistoryInOutDao.class);
		if (saveFlag)
			dao.save(poList);

		historyList.sort(new Comparator<History>() {

			@Override
			public int compare(History o1, History o2) {
				try {
					return DateHelper.dateFormat.parse(o1.getStart())
							.compareTo(DateHelper.dateFormat.parse(o2.getStart()));
				} catch (ParseException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				return 0;
			}
		});
		List<History> buyList = new ArrayList<>();
		double rate = 1.0;
		List<NewHistory> removeList = new ArrayList<>();
		List<History> tempBuyList = new ArrayList<>();
		// String dateBefor = null;
		String yearBefor = null;
		String monthBf = null;
		double monyLeft = startM;
		YearResult yearResult = null;

		for (int i = 0; i < historyList.size(); i++) {
			History history = historyList.get(i);
			History historyAf = (i < historyList.size() - 1) ? historyList.get(i + 1) : null;

			Date now = DateHelper.dateFormat.parse(history.getStart());

			String yearNow = DateHelper.dateFormatYear.format(now);
			String monthNow = DateHelper.dateFormatMM.format(now);
			if (startDate.after(now))
				continue;
			if (yearBefor == null) {
				yearBefor = yearNow;
				yearResult = new YearResult();
				yearResult.year = yearNow;
				yearResult.startMoney = moneyALL;

			}
			if (monthBf == null) {
				monthBf = monthNow;

			}
			if (!monthBf.equals(monthNow)) {
				monthBf = monthNow;
				surplusMoney += groupUp;
				groupAll += groupUp;
				// System.err.println("每月资金成长");
			}
			if (!yearNow.equals(yearBefor)) {
				yearResult.endMoney = moneyALL;
				yearResult.dif = (yearResult.endMoney - yearResult.startMoney) / yearResult.startMoney;
				result.yearResults.add(yearResult);
				refreshYearWin(yearBefor);
				yearResult = new YearResult();
				yearResult.year = yearNow;
				yearResult.startMoney = moneyALL;

				yearBefor = yearNow;
			}
			boolean cbuyFlag = false;
			if ((historyAf != null && !historyAf.getStart().equals(history.getStart())) || historyAf == null) {
				cbuyFlag = true;
			}
			// if (dateBefor != null)
			{

				if (!cbuyFlag) {
					boolean buyFlag = false;
					for (NewHistory historyBuy : historybuyList) {
						if (historyBuy.history.getId() == history.getId()) {
							buyFlag = true;
							break;
						}
					}
					if (!buyFlag) {
						tempBuyList.add(history);
					}
					continue;
				} else {
					boolean buyFlag = false;
					for (NewHistory historyBuy : historybuyList) {
						if (historyBuy.history.getId() == history.getId()) {
							buyFlag = true;
							break;
						}
					}
					if (!buyFlag) {
						tempBuyList.add(history);
					}
				}
				// dateBefor = history.getStart();
			}
			// else {
			// dateBefor = history.getStart();
			//
			// continue;
			// }
			removeList.clear();

			// int size = (int) (initMoney / rate);
			nowSize = (nowSize >= targetSize) ? targetSize : moneyALL;
			int size = (int) (nowSize - historybuyList.size());
			if (size > 0) {
				rate = surplusMoney / size;
				List<History> tList = sortList(tempBuyList, size);
				// System.err.println("size:" + size);
				for (History bh : tList) {
					if (!bh.buySuccessFlag)
						continue;
					if (surplusMoney < rate) {
						break;
					}
					surplusMoney -= rate;
					NewHistory nHistory = new NewHistory();
					nHistory.history = bh;
					nHistory.buy = rate;
					buyList.add(bh);
					historybuyList.add(nHistory);
					printErr("B " + bh.getId() + ":" + (surplusMoney + historybuyList.size()) + "   "
							+ historybuyList.size() + "  score" + nHistory.history.getScore() + " rate:" + rate
							+ "  date:" + nHistory.history.getStart() + "  ");
				}
				// initMoney -= rate;
				// NewHistory nHistory = new NewHistory();
				// nHistory.history = history;
				// nHistory.buy = rate;
				// buyList.add(history);
				// historybuyList.add(nHistory);
				// System.err.println("B:" + (initMoney + historybuyList.size())
				// + " " + historybuyList.size() + " rate:"
				// + rate + " date:" + nHistory.history.getStart() + " ");
			}
			tempBuyList.clear();
			for (NewHistory historyBuy : historybuyList) {
				Date end = DateHelper.dateFormat.parse(historyBuy.history.getEnd());
				// System.err.println(now +" "+end);
				if (now.compareTo(end) >= 0 || (i == historyList.size() - 1)) {
					surplusMoney += (historyBuy.history.getDif() + 1d) * historyBuy.buy;
					yearResult.count++;
					moneyALL += (historyBuy.history.getDif() + 1d) * historyBuy.buy - historyBuy.buy;
					removeList.add(historyBuy);

					yearResult.allUse += (int) ((DateHelper.dateFormat.parse(historyBuy.history.getEnd()).getTime()
							- DateHelper.dateFormat.parse(historyBuy.history.getStart()).getTime())
							/ (3600l * 1000l * 24l));
					if (historyBuy.history.getDif() > 0) {
						yearResult.success += 1;
						success += 1;
					}
					if (historyBuy.history.getDif() < 0) {
						yearResult.unSuccess += 1;
						unsuccess += 1;
					}
					CHistoryBuySellPo hbsPo = new CHistoryBuySellPo();
					CHistoryBuySellPK hbsPk = new CHistoryBuySellPK();

					hbsPk.setId((int) historyBuy.history.getId());
					hbsPk.setBuyDate(DateHelper.dateFormat.parse(historyBuy.history.getStart()));
					hbsPk.setSellDate(DateHelper.dateFormat.parse(historyBuy.history.getEnd()));
					hbsPo.setId(hbsPk);
					hbsPo.setBuyMoney(historyBuy.history.getStartMoney());
					hbsPo.setSellMoney(historyBuy.history.getEndMoney());
					hbsPo.setDif(historyBuy.history.getDif());
					hbsPo.setRate(historyBuy.buy);
					hbsPo.setUserDay((int) ((DateHelper.dateFormat.parse(historyBuy.history.getEnd()).getTime()
							- DateHelper.dateFormat.parse(historyBuy.history.getStart()).getTime())
							/ (3600l * 1000l * 24l)));
					if (saveHistoryFlag) {
						AppContextUtil.instance.getCHistoryBuySellDao().save(hbsPo);
					}
					printErr("S  " + historyBuy.history.getId() + ":" + surplusMoney + "   rate:" + rate + "  "
							+ historyBuy.history.getDif());
				}
			}
			// if (initMoney + historybuyList.size() > 25) {
			// rate = 1 + (initMoney + historybuyList.size()) / 80;
			// }
			historybuyList.removeAll(removeList);
			// rate = (startM - historybuyList.size() <= 0) ? 1 : initMoney /
			// (startM - historybuyList.size());
			// rate = getRate(rate, moneyALL, startM * upgradeRate, upgradeMup,
			// startM);
		}

		for (NewHistory historyBuy : historybuyList) {
			surplusMoney += (historyBuy.history.getDif() + 1d) * historyBuy.buy;
			moneyALL += (historyBuy.history.getDif() + 1d) * historyBuy.buy - historyBuy.buy;
		}
		yearResult.endMoney = moneyALL;
		yearResult.dif = (yearResult.endMoney - yearResult.startMoney) / yearResult.startMoney;
		result.yearResults.add(yearResult);
		refreshYearWin(yearBefor);

		System.err.println("end:" + surplusMoney);
		List<WLD> wldL = new ArrayList<>();
		for (String str : historyWinloseMap.keySet()) {
			WLD wld = new WLD();
			wld.date = str;
			wld.result = historyWinloseMap.get(str);
			wldL.add(wld);
		}

		wldL.sort((a, b) -> {
			try {
				return DateHelper.dateFormat.parse(a.date).compareTo(DateHelper.dateFormat.parse(b.date));
			} catch (Exception e) {
				// TODO: handle exception
			}
			return 0;
		});

		// for (WLD w:wldL) {
		// System.out.println( w.date+","+w.result);
		// }
		DecimalFormat df = new DecimalFormat("######0.00");
		long allDif = 0;
		for (History h : buyList) {
			long dDif = (DateHelper.dateFormat.parse(h.getEnd()).getTime()
					- DateHelper.dateFormat.parse(h.getStart()).getTime()) / (3600l * 1000l * 24l);
			allDif += dDif;
			printErr(h.getId() + ":\t买入时间:" + h.getStart() + "买入价格:" + df.format(h.getStartMoney()) + " ,卖出时间:"
					+ h.getEnd() + " 卖出价格:" + df.format(h.getEndMoney()) + "\t 收益:" + df.format(h.getDif() * 100) + "%"
					+ " 耗时：" + dDif + "天      score:" + h.getScore());
		}
		allDif /= buyList.size();
		System.err.println(
				"平均时间：" + allDif + ",总共：" + buyList.size() + "个交易" + "   " + moneyALL + "  " + (moneyALL / startM));
		for (String key : yearWinMap.keySet()) {
			yearInfo.append(" year:" + key + "  :" + yearWinMap.get(key) + "  " + yearWinMap.get(key) / startM)
					.append("\r\n");

		}
		yearInfo.append("success:" + success + "  Unsuccess:" + unsuccess + "  successRate:"
				+ (success) / (success + unsuccess)).append("\r\n");
		// System.err.println(yearInfo);
		result.count = buyList.size();
		result.monetResylt = moneyALL;
		result.moneyDef = startM;
		result.rate = rate;
		result.sunccessCount = success;
		result.unSuccessCount = unsuccess;
		result.difPer = moneyALL / startM;
		result.allDif = allDif;
		result.groupAll = groupAll;
		System.err.println(result.toString());
		return result;
	}

	public static class WLD {
		String date;
		double result = 0;
	}

	public static double getRate(double rate, double now, double upgrade, double mup, double def) {
		if (now > upgrade) {
			rate = (now) / def;
		}
		return rate;
	}

	private void refreshYearWin(String year) {

		yearWinMap.put(year, moneyALL);
	}

	private static double getSortScore(History h) {
		// if( h.bf.getLycjdmiFlagsumsshow()==0)
		// {
		// return 0;
		// }
		// Math.abs((h.bf.getLycjdmiFlagsumsshow))
		// return (h.bbf.getLycjdmiHightsum());
		// return h.bf.getLycjdmiFlagsumsshow()/h.avgLycjdmiFlagsumsshow;
		// return -h.getVariance();
		return h.getScore();
	}

	public static List<History> sortList(List<History> list, int size) {

		list.sort((a, b) -> {

			return getSortScore(a) > getSortScore(b) ? 1 : getSortScore(a) == getSortScore(b) ? 0 : -1;
			// return a.getScore() > b.getScore() ? 1 : a.getScore() ==
			// b.getScore() ? 0 : -1;
			// return a.ft.getLycjdmiVdif() > b.ft.getLycjdmiVdif() ? -1 :
			// a.ft.getLycjdmiVdif() == b.ft.getLycjdmiVdif() ? 0 : 1;
		});
		List<History> result = new ArrayList<>();
		List<History> temp = new ArrayList<>();
		temp.addAll(list);
		for (History h : list) {
			
			if (h.bf.getMacdMacd() <= h.bbf.getMacdMacd())
				{
				System.err.println("asdasdas!!!!!!!!!!!!!");
				}
			
//			if (h.bf.getLycjdmiHightsum() >= h.bf.getLycjdmiVdif()) {
//				temp.remove(h);
//			}
//			else 
				if (h.bbf.getLycjdmiHightsum() <= h.bbf.getLycjdmiVdif())
			{
				temp.remove(h);
			}
		}
		int index = 0;
		// if (size < list.size()) {
		// index = (list.size() - size) / 2;
		// }

		//
		int tempi = 0;
		for (int i = 0; result.size() < size&&i < temp.size(); i++) {
			History t=temp.get(i);
			if(t.bf.getLycjssFlags()>t.bbf.getLycjssFlags())
			{
				
				result.add(t);
			}
		}
		temp.removeAll(result);
		
		
		
		for (; result.size() < size && temp.size() > 0;) {
			if ((tempi % 5) <30) {
			
				result.add(MathHelper.removeMost(temp, new IGetValue<History>() {

					@Override
					public double getValue(History t) {
						return -(t.bf.getMacdMacd()-t.bbf.getMacdMacd());
					}
				}));
			}
			else if ((tempi % 5) < 40)

			{
				result.add(MathHelper.removeMost(temp, new IGetValue<History>() {

					@Override
					public double getValue(History t) {
						return t.bf.getLycjdmiVdif()-t.bbf.getLycjdmiVdif();
					}
				}));
			}
			// 4.553596856682389
//			else if ((tempi % 2) < 4)
//
//			{
//				result.add(MathHelper.removeMost(temp, new IGetValue<History>() {
//
//					@Override
//					public double getValue(History t) {
//						return -(t.bf.getMacdMacd()-t.bbf.getMacdMacd());
//					}
//				}));
//			}

			else {

				result.add(MathHelper.removeMost(temp, new IGetValue<History>() {

					@Override
					public double getValue(History t) {
						// TODO Auto-generated method stub
						return -t.getScore();
					}
				}));

			}
			tempi++;
		}

		// Random randomIndex=
		// for (int i = 0; i < size && i < list.size(); i++) {
		//
		// result.add(list.get(i));
		// }
		// for (int i = 0, j=list.size()-1;
		// i<=j&&result.size()<=size&&result.size()<
		// list.size(); ) {
		//
		// if(i%2==j%2)
		// {
		// result.add(list.get(i));
		// i++;
		// }
		// else
		// {
		// result.add(list.get(j));
		// j--;
		// }
		// }
		// for (int i = 0, j=list.size()-1; i < size && i < list.size(); i=i+2)
		// {
		//
		// result.add(list.get(i/2));
		// result.add(list.get(list.size()-1-i/2));
		// }
		return result;
	}

	private void printErr(String str) {
		if (showFlag) {
			System.err.println(str);
		}
	}

	public static class NewHistory {
		History history;
		double buy;
	}

	public static class ConfigAndReesult {
		public double groupAll;
		public double moneyDef;
		public double monetResylt;
		public double sunccessCount;
		public double unSuccessCount;
		public double rate;
		public double count;
		public double difPer;
		public double allDif;
		public List<YearResult> yearResults = new ArrayList<>();

		@Override
		public String toString() {
			StringBuffer stringBuffer = new StringBuffer();
			stringBuffer.append("moneyDef:" + moneyDef).append("  ");
			stringBuffer.append("monetResylt:" + monetResylt).append("  ");
			stringBuffer.append("difPer:" + difPer).append("  ");
			stringBuffer.append("平均耗时:" + allDif).append("  ");
			stringBuffer.append("successPer:" + ((double) (sunccessCount) / (sunccessCount + unSuccessCount)))
					.append("   ");
			stringBuffer.append("总成长:" + groupAll).append("  ");
			stringBuffer.append("rate:" + rate).append("  ");
			stringBuffer.append("sunccessCount:" + sunccessCount).append("   ");
			stringBuffer.append("unSuccessCount:" + unSuccessCount).append("   ");
			stringBuffer.append("all count:" + count).append("  ");
			stringBuffer.append("\r\n");
			for (YearResult result : yearResults) {
				stringBuffer.append(result.toString()).append("\r\n");
			}
			return stringBuffer.toString();
		}
	}

	public static class YearResult {
		public String year;
		public double startMoney;
		public double endMoney;
		public double dif;
		public int count = 0;
		public int aveUse = 0;
		public int allUse = 0;
		public int success;
		public int unSuccess;

		@Override
		public String toString() {
			StringBuffer stringBuffer = new StringBuffer();
			stringBuffer.append("year:" + year).append(" ");
			stringBuffer.append("startMoney:" + startMoney).append(" ");
			stringBuffer.append("endMoney:" + endMoney).append(" ");
			stringBuffer.append("dif:" + dif).append("");
			stringBuffer.append("一共" + count).append("个交易   ");
			stringBuffer.append("平均每个交易:" + (count > 0 ? (allUse / count) : 0)).append("天   ");
			stringBuffer
					.append("成功率:"
							+ ((success + unSuccess) > 0 ? (((double) success) / ((double) (success + unSuccess))) : 0))
					.append("  ");
			return stringBuffer.toString();
		}
	}
}
