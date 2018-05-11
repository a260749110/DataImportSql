package com.bigcalculate;

import java.text.ParseException;
import java.util.List;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.bigcalculate.cell.CalculateNode;
import com.bigcalculate.job.BigCalculateJob;
import com.bigcalculate.job.DaySimulationJob;
import com.bigcalculate.job.DaySimulationScoreJob;
import com.bigcalculate.job.DaySimulationJob.ConfigAndReesult;
import com.bigcalculate.job.DaySimulationJob.YearResult;
import com.comfig.ImportConfig;
import com.sql.domain.CBigCalculatePo;
import com.util.AppContextUtil;
import com.util.Helper;

public class RefreshCaculateMain {

	public static void main(String[] args) {
		CalculateNode node = null;

		for (int i = 0; i < (int) 20; i++) {
			System.err.println("-------------------id:" + (i + 1));
			CBigCalculatePo po = AppContextUtil.instance.getCBigCalculateDao().findOne(i + 1);
			if (po == null) {
				break;
			}
			try {
				List<CalculateNode> nodes = JSONArray.parseArray(po.getDataBase(), CalculateNode.class);
				node = null;
				for (CalculateNode n : nodes) {
					if (node == null || n.getDscore() > node.getDscore()) {
						node = n;

					}
				}
				Helper.initNode(node, ImportConfig.getInstance().getSampleSize());
				node = refresh(node);
				nodes.clear();
				nodes.add(node);
				po.setScore(node.getScore());
				po.setsScore(node.getsScore());
				po.setDataBase(JSON.toJSONString(nodes));
				po.setDscore(node.getDscore());
				AppContextUtil.instance.getCBigCalculateDao().save(po);
			} catch (Exception e) {
				e.printStackTrace();
			}
			// try {
			// DaySimulationJob dj = new DaySimulationScoreJob();
			// BigCalculateJob bj = new BigCalculateJob();
			// bj.init();
			// bj.run(node, dj);
			// ConfigAndReesult result = dj.toSql();
			// System.err.println(result.toString());
			// } catch (ParseException e) {
			// // TODO Auto-generated catch block
			// e.printStackTrace();
			// }
		}
		System.err.println(" end !!!!!!!!!!!!!");
		for (int i = 0; i < 99999; i++) {
			try {
				Thread.sleep(10000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

	private static CalculateNode refresh(CalculateNode node) throws ParseException {
		DaySimulationJob dj = new DaySimulationScoreJob();
		BigCalculateJob bj = new BigCalculateJob();
		bj.init();
		bj.run(node, dj);
		try {
			ConfigAndReesult rs = dj.toSql();
			int i = 0;
			for (YearResult ye : rs.yearResults) {
				i++;
				if (i == rs.yearResults.size()) {
					node.setScore((float) ye.startMoney);
				}
				node.getYearInfo().put(ye.year, ye);
			}
			node.setDscore((float) dj.dscore);
			node.setsScore((float) dj.moneyALL);
		} catch (ParseException e) {
			e.printStackTrace();
		}

		return node;
	}

}
