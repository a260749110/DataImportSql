package com.check.cells;

import java.text.DecimalFormat;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;

import org.apache.commons.httpclient.util.DateUtil;
import org.springframework.jdbc.datasource.init.DatabasePopulatorUtils;

import com.check.job.LycjssFlagJob;
import com.check.job.LycjssFlagJob.History;
import com.sql.dao.CHistoryInOutDao;
import com.sql.domain.CHistoryInOutPo;
import com.util.AppContextUtil;

public class AllHistory {
	List<History> historyList = new ArrayList<>();
	public Map<String, CHistoryInOutPo> historyMaps = new HashMap<String, CHistoryInOutPo>();
	public double initMoney = 20;
	public List<NewHistory> historybuyList = new ArrayList<>();
	Map<String, Double> historyWinloseMap = new HashMap<>();

	public void addHistory(History history) {
		historyList.add(history);

	}

	public void toSql() throws ParseException {
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
				spo.setMarkDate(LycjssFlagJob.dateFormat.parse(history.getStart()));
				historyMaps.put(history.getStart(), spo);
				poList.add(spo);
			}
			if (epo == null) {
				epo = new CHistoryInOutPo();
				epo.setMarkDate(LycjssFlagJob.dateFormat.parse(history.getEnd()));
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
		dao.save(poList);

		historyList.sort(new Comparator<History>() {

			@Override
			public int compare(History o1, History o2) {
				try {
					return LycjssFlagJob.dateFormat.parse(o1.getStart())
							.compareTo(LycjssFlagJob.dateFormat.parse(o2.getStart()));
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
		String dateBefor=null;
		for (int i=0;i< historyList.size();i++) {
			History history=historyList.get(i);

			Date now = LycjssFlagJob.dateFormat.parse(history.getStart());
			if(dateBefor!=null)
			{

				if(dateBefor.equals(history.getStart()))
				{
					tempBuyList.add(history);
					continue;
				}
				dateBefor=history.getStart();
			}
			else
			{
				dateBefor=history.getStart();
				continue;
			}
			removeList.clear();
			
			
			
			

			int size=(int) (initMoney/rate);
			
			if (size > 0) {
				
				List<History> tList=sortList(tempBuyList, size);
				System.err.println("size:"+size);
				for (History bh:tList) {
					initMoney -= rate;
					NewHistory nHistory = new NewHistory();
					nHistory.history = bh;
					nHistory.buy = rate;
					buyList.add(bh);
					historybuyList.add(nHistory);
					System.err.println("B "+bh.getId()+":" + (initMoney + historybuyList.size()) + "   " + historybuyList.size() + " rate:"
							+ rate + "  date:" + nHistory.history.getStart() + "  ");
				}
				tempBuyList.clear();
//				initMoney -= rate;
//				NewHistory nHistory = new NewHistory();
//				nHistory.history = history;
//				nHistory.buy = rate;
//				buyList.add(history);
//				historybuyList.add(nHistory);
//				System.err.println("B:" + (initMoney + historybuyList.size()) + "   " + historybuyList.size() + " rate:"
//						+ rate + "  date:" + nHistory.history.getStart() + "  ");
			}
			for (NewHistory historyBuy : historybuyList) {
				Date end = LycjssFlagJob.dateFormat.parse(historyBuy.history.getEnd());
				// System.err.println(now +" "+end);
				if (now.compareTo(end) >= 0) {
					initMoney += (historyBuy.history.getDif() + 1d) * historyBuy.buy;
					removeList.add(historyBuy);
					System.err.println("S  "+historyBuy.history.getId()+":" + initMoney + "   rate:" + rate + "  " +historyBuy.history.getDif());
				}
			}
			if (initMoney + historybuyList.size() > 25) {
				rate = 1 + (initMoney + historybuyList.size()) / 200;
			}
			historybuyList.removeAll(removeList);
		}
		for (NewHistory historyBuy : historybuyList) {
			initMoney += (historyBuy.history.getDif() + 1d) * historyBuy.buy;
		}
		System.err.println("end:" + initMoney);
		List<WLD> wldL = new ArrayList<>();
		for (String str : historyWinloseMap.keySet()) {
			WLD wld = new WLD();
			wld.date = str;
			wld.result = historyWinloseMap.get(str);
			wldL.add(wld);
		}

		wldL.sort((a, b) -> {
			try {
				return LycjssFlagJob.dateFormat.parse(a.date).compareTo(LycjssFlagJob.dateFormat.parse(b.date));
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
			allDif += LycjssFlagJob.dateFormat.parse(h.getEnd()).getTime()
					- LycjssFlagJob.dateFormat.parse(h.getStart()).getTime();
//			System.err.println(h.getId() + ":\t买入时间:" + h.getStart() + "  买入价格:" + df.format(h.getStartMoney()) + " ,卖出时间:"
//					+ h.getEnd() + "  卖出价格:" + df.format(h.getEndMoney()) + "\t 收益:" + df.format(h.getDif()));
		}
		allDif /= buyList.size();
		allDif /= 3600l * 1000l * 24l;
		System.err.println("平均时间：" + allDif+",总共："+buyList.size()+"个交易");	}
	public static class WLD {
		String date;
		double result = 0;
	}

	public  List<History> sortList(List<History> list,int size)
	{
		
		list.sort((a,b)->{
			return a.getScore()>b.getScore()?1:a.getScore()==b.getScore()?0:-1;
		});
		List<History >result =new ArrayList<>();
		int index=0;
		if(size<list.size())
		{
			index=(list.size()-size)/2;
		}
	for (int i = 0; i<size&&i<list.size(); i++) {
			result.add(list.get(list.size()-1-i));
		}
		return result;
	}
	
	
	public static class NewHistory {
		History history;
		double buy;
	}
}
