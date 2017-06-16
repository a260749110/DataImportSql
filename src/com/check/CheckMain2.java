package com.check;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import com.check.cells.AllHistory.ConfigAndReesult;
import com.check.job.LycjssFlagJob2;
import com.sql.dao.CDataBaseDao;
import com.util.AppContextUtil;

public class CheckMain2 {
	public static void main(String[] args) {
		refresh();
		try {
			LycjssFlagJob2.allHistory.saveFlag = true;
			LycjssFlagJob2.allHistory.toSql();
		} catch (ParseException e) {
			e.printStackTrace();
		}
		double money = 1;
		double rate = 1;
		double best = 0;
		String bestStr = "";
		LycjssFlagJob2.allHistory.showFlag = false;
		LycjssFlagJob2.allHistory.saveFlag = false;
		List<ConfigAndReesult> results = new ArrayList<>();
		for (int i = 0; i <0; i++) {
			double moneySize = money;
			for (int j = 0; j < 100; j++) {
				System.err.println("money:" + moneySize + "  rate: " + rate);
				LycjssFlagJob2.allHistory.defMoney = moneySize;
				LycjssFlagJob2.allHistory.upgradeMup = rate;
				LycjssFlagJob2.allHistory.init();
				try {
					ConfigAndReesult reesult = LycjssFlagJob2.allHistory.toSql();
					results.add(reesult);
				} catch (ParseException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				if (LycjssFlagJob2.allHistory.moneyALL / moneySize > best) {
					best = LycjssFlagJob2.allHistory.moneyALL / moneySize;
					bestStr = LycjssFlagJob2.allHistory.yearInfo + " best:  money:" + moneySize + "  rate:" + rate
							+ "  " + LycjssFlagJob2.allHistory.moneyALL / moneySize;
				}
				moneySize += 2;
			}
			rate += 2;
		}
		results.sort((a, b) -> {
			return Double.compare(a.difPer, b.difPer);
		});

		for (int i = 0; i < results.size() / 5; i++) {
			System.err.println(results.get(results.size() - results.size() / 5 + i).toString());
			System.err.println("!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
		}
		System.err.println("success:" + LycjssFlagJob2.success + "   unSuccess:" + LycjssFlagJob2.unSuccess + "per:"
				+ (((double) LycjssFlagJob2.success) / (LycjssFlagJob2.success + LycjssFlagJob2.unSuccess)) + "  \r\n"
				+ bestStr);
	}

	public static void refresh() {
		CDataBaseDao dao = AppContextUtil.getContext().getBean(CDataBaseDao.class);
		List<Long> ids = dao.getAllId();
		for (int i = 0; i < ids.size(); i++) {
			long id = Long.valueOf(ids.get(i) + "").longValue();
			// if(id!=2212)
			// continue;
			if (id == 399001L || id > 100000)
				continue;
			LycjssFlagJob2 job = new LycjssFlagJob2();
			job.run(id);
		}
	}
}
