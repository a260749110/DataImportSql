package com.bigcalculate.job;

import java.text.DecimalFormat;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

import com.check.cells.History;
import com.comfig.ImportConfig;
import com.sql.domain.CHistoryBuySellPK;
import com.sql.domain.CHistoryBuySellPo;
import com.util.AppContextUtil;

public class LiSanDaySimulateJob extends DaySimulationJob{
	@Override
	public ConfigAndReesult toSql() throws ParseException {
		ConfigAndReesult result = new ConfigAndReesult();
		double startM = moneyALL;
		double success = 0;
		double unsuccess = 0;
		double nowSize = startM;
	
		historyList.sort(new Comparator<History>() {

			@Override
			public int compare(History o1, History o2) {
				return Long.compare(o1.getStartTime(), o2.getStartTime());
		
			}
		});

	
		List<History> buyList = new ArrayList<>();
		double rate = 1.0;
		List<NewHistory> removeList = new ArrayList<>();
		List<History> tempBuyList = new ArrayList<>();
		String yearBefor = null;
		String monthBf = null;
		double monyLeft = startM;
		YearResult yearResult = null;
		yearResult = new YearResult();
		for (int i = 0; i < historyList.size(); i++) {
			History history = historyList.get(i);
			History historyAf = (i < historyList.size() - 1) ? historyList.get(i + 1) : null;

			Date now = history.getStart();

			String yearNow = dateHelper.dateFormatYear.format(now);
			String monthNow = dateHelper.dateFormatMM.format(now);
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
			if ((historyAf != null && !(historyAf.getStart().compareTo(history.getStart()) == 0))
					|| historyAf == null) {
				cbuyFlag = true;
			}
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
					System.err.println( dateHelper.dateFormat.format(now)+ ":buySize:"+historybuyList.size());
					dscore = dscore * ImportConfig.getInstance().getScoreDown();
					dCount++;
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

			}

			removeList.clear();
			nowSize = (nowSize >= targetSize) ? targetSize : moneyALL;
			int size = 999999;
			if (size > 0) {
				rate = 1;
				List<History> tList = sortList(tempBuyList, size);
				// System.err.println("size:" + size);
				for (History bh : tList) {
					if (bh.getScore() <= 0) {
						continue;
					}
					// System.err.println(bh.getScore());
					if (!bh.buySuccessFlag)
						continue;
//					if (surplusMoney < rate) {
//						break;
//					}
					surplusMoney -= rate;
					NewHistory nHistory = new NewHistory();
					nHistory.history = bh;
					nHistory.buy = rate;
					buyList.add(bh);
					historybuyList.add(nHistory);
					// printErr("B " + bh.getId() + ":" + (surplusMoney +
					// historybuyList.size()) + " "
					// + historybuyList.size() + " score" +
					// nHistory.history.getScore() + " rate:" + rate
					// + " date:" + nHistory.history.getStart() + " ");
				}
			}
			tempBuyList.clear();
			for (NewHistory historyBuy : historybuyList) {
				Date end = new Date(historyBuy.history.getEndTime());
				Date start = new Date(historyBuy.history.getStartTime());
				if (now.compareTo(end) >= 0 || (i == historyList.size() - 1)) {
					surplusMoney += (historyBuy.history.getDif() + 1d) * historyBuy.buy;
					yearResult.count++;
					double temps = (historyBuy.history.getDif() + 1d) * historyBuy.buy - historyBuy.buy;
					moneyALL += temps;
					dscore += temps;
					removeList.add(historyBuy);

					yearResult.allUse += (int) ((end.getTime() - start.getTime()) / (3600l * 1000l * 24l));
					if (historyBuy.history.getDif() > 0) {
						yearResult.success += 1;
						success += 1;
					}
					if (historyBuy.history.getDif() < 0) {
						yearResult.unSuccess += 1;
						unsuccess += 1;
					}
					if (saveHistoryFlag) {
						CHistoryBuySellPo hbsPo = new CHistoryBuySellPo();
						CHistoryBuySellPK hbsPk = new CHistoryBuySellPK();

						hbsPk.setId((int) historyBuy.history.getId());
						hbsPk.setBuyDate(start);
						hbsPk.setSellDate(end);
						hbsPo.setId(hbsPk);
						hbsPo.setBuyMoney(historyBuy.history.getStartMoney());
						hbsPo.setSellMoney(historyBuy.history.getEndMoney());
						hbsPo.setDif(historyBuy.history.getDif());
						hbsPo.setRate(historyBuy.buy);
						hbsPo.setUserDay((int) ((hbsPk.getSellDate().getTime() - hbsPk.getBuyDate().getTime())
								/ (3600l * 1000l * 24l)));
						AppContextUtil.instance.getCHistoryBuySellDao().save(hbsPo);
					}
				}
			}
		
			historybuyList.removeAll(removeList);
		}

		for (NewHistory historyBuy : historybuyList) {
			surplusMoney += (historyBuy.history.getDif() + 1d) * historyBuy.buy;
			double temps = (historyBuy.history.getDif() + 1d) * historyBuy.buy - historyBuy.buy;
			moneyALL += temps;
			dscore += temps;
		}
		yearResult.endMoney = moneyALL;
		yearResult.dif = (yearResult.endMoney - yearResult.startMoney) / yearResult.startMoney;
		result.yearResults.add(yearResult);
		refreshYearWin(yearBefor);

		System.err.println("end:" + surplusMoney);

		DecimalFormat df = new DecimalFormat("######0.00");
		long allDif = 0;
		int take = 0;

	

		System.err.println("dCount:" + dCount);
		System.err.println("平均时间：" + allDif + ",总共：" + buyList.size() + "个交易" + "   " + moneyALL + "  "
				+ (moneyALL / startM) + " dscore:" + dscore);
		for (String key : yearWinMap.keySet()) {
			yearInfo.append(" year:" + key + "  :" + yearWinMap.get(key) + "  " + yearWinMap.get(key) / startM)
					.append("\r\n");

		}
		System.err.println("目前持有:" + take);
		yearInfo.append("success:" + success + "  Unsuccess:" + unsuccess + "  successRate:"
				+ (success) / (success + unsuccess)).append("\r\n");
		result.count = buyList.size();
		result.monetResylt = moneyALL;
		result.moneyDef = startM;
		result.rate = rate;
		result.sunccessCount = success;
		result.unSuccessCount = unsuccess;
		result.difPer = (moneyALL - startM) / startM * 100;
		result.allDif = allDif;
		result.groupAll = groupAll;
		System.err.println(result.toString());
		return result;
	}

}
