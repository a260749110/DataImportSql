package com.bigcalculate;

import java.text.ParseException;
import java.util.List;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.bigcalculate.cell.CalculateNode;
import com.bigcalculate.job.BigCalculateJob;
import com.bigcalculate.job.DaySimulationJob;
import com.bigcalculate.job.DaySimulationJob.ConfigAndReesult;
import com.bigcalculate.job.DaySimulationJob.YearResult;
import com.comfig.ImportConfig;
import com.sql.domain.CBigCalculatePo;
import com.util.AppContextUtil;

public class RefreshCaculateMain {

	public static void main(String[] args) {
		CalculateNode node = null;

		for (int i = 0; i < (int) ImportConfig.getInstance().getMax_size(); i++) {

			CBigCalculatePo po = AppContextUtil.instance.getCBigCalculateDao().findOne(i + 1);
			try {
				List<CalculateNode> nodes = JSONArray.parseArray(po.getDataBase(), CalculateNode.class);
				for (CalculateNode n : nodes) {
					if (node == null || n.getsScore() > node.getsScore()) {
						node = n;

					}
				}
				node = refresh(node);
				nodes.clear();
				nodes.add(node);
				po.setScore(node.getScore());
				po.setsScore(node.getsScore());
				po.setDataBase(JSON.toJSONString(nodes));
				po.setDscore(node.getDscore());
				node = null;
				AppContextUtil.instance.getCBigCalculateDao().save(po);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		DaySimulationJob dj = new DaySimulationJob();
		BigCalculateJob bj = new BigCalculateJob();
		bj.init();
		bj.run(node, dj);
		try {
			ConfigAndReesult result = dj.toSql();
			System.err.println(result.toString());
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private static CalculateNode refresh(CalculateNode node) throws ParseException {
		DaySimulationJob dj = new DaySimulationJob();
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
		} catch (ParseException e) {
			e.printStackTrace();
		}
		node.setsScore((float) dj.moneyALL);
		
		return node;
	}

}
