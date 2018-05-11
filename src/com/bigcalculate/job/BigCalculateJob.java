package com.bigcalculate.job;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import com.alibaba.fastjson.JSONArray;
import com.bigcalculate.cell.CalculateNode;
import com.bigcalculate.job.DaySimulationJob.ConfigAndReesult;
import com.bigcalculate.job.DaySimulationJob.YearResult;
import com.comfig.ImportConfig;
import com.sql.dao.CDataBaseDao;
import com.sql.domain.CBigCalculatePo;
import com.util.AppContextUtil;
import com.util.Helper;
import com.util.ThreadPoolArrayList;

public class BigCalculateJob {
	private static List<Long> allIds;

	public void init() {
		if (allIds == null)
			allIds = loadIds();

	}

	CalculateNode node;

	public CBigCalculatePo run(CBigCalculatePo calculate) {
		List<CalculateNode> nodes = null;
		if (calculate.getDataBase() == null) {
			nodes = new ArrayList<>();

		} else {
			try {
				nodes = JSONArray.parseArray(calculate.getDataBase(), CalculateNode.class);
			} catch (Exception e) {
				e.printStackTrace();
				nodes = new ArrayList<>();
			}
		}
		DaySimulationJob dj =  new DaySimulationScoreJob();// new DaySimulationJob();

		node = new CalculateNode();

		Random random = new Random();
		if (nodes.size() >= ImportConfig.getInstance().getSave_size()) {
			node = nodes.get(random.nextInt(nodes.size()));
		} else {
			System.err.println("new");
			node = new CalculateNode();
		}

		Helper.initNode(node, ImportConfig.getInstance().getSampleSize());

		node = Helper.randomNode(node);

		allIds.forEach(id -> {
			Caluculate caluculate = new Caluculate(node, dj);
			caluculate.run(id);
		});
		System.err.println("run End");
		// for (long id : allIds) {
		// Caluculate caluculate = new Caluculate(node, dj);
		// caluculate.run(id);
		// score += caluculate.resultAll;
		// }
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
		} catch (ParseException e) {
			e.printStackTrace();
		}
		node.setsScore((float) dj.moneyALL);
		node.setDscore((float) dj.dscore);
		nodes = refreshNods(calculate, nodes);
		nodes.add(node);
		CalculateNode nodeRemove = null;
		CalculateNode best = null;
		nodes.sort((a, b) -> {
			return a.getDscore() > b.getDscore() ? -1 : a.getDscore() == b.getDscore() ? 0 : 1;
		});
		for (CalculateNode n : nodes) {
			if (nodeRemove == null) {
				nodeRemove = n;
			} else {
				if (nodeRemove.getDscore() > n.getDscore()) {
					nodeRemove = n;
				}
			}
			if (best == null) {
				best = n;
			} else {
				if (best.getDscore() < n.getDscore()) {
					best = n;
				}
			}
		}
		// for (CalculateNode n : nodes) {
		// if (nodeRemove == null) {
		// nodeRemove = n;
		// } else {
		// if (nodeRemove.getsScore() > n.getsScore()) {
		// nodeRemove = n;
		// }
		// }
		// if (best == null) {
		// best = n;
		// } else {
		// if (best.getsScore() < n.getsScore()) {
		// best = n;
		// }
		// }
		// }
		if (nodes.size() > ImportConfig.getInstance().getSave_size()) {
			if (nodeRemove != null) {
				nodes.remove(nodeRemove);

			}
		}
		calculate.setDataBase(JSONArray.toJSONString(nodes));
		calculate.setScore(best.getScore());
		calculate.setDscore(best.getDscore());
		calculate.setsScore(best.getsScore());
		return calculate;
	}

	public void run(CalculateNode node,DaySimulationJob dj) {
		allIds.forEach(id -> {
			Caluculate caluculate = new Caluculate(node, dj);
			caluculate.run(id);
		});
	

	}

	private List<CalculateNode> refreshNods(CBigCalculatePo calculate, List<CalculateNode> nodes) {
		try {

			if (calculate.getId() <= 0)
				return nodes;
			else {
				CBigCalculatePo now = AppContextUtil.instance.getCBigCalculateDao().findOne(calculate.getId());

				List<CalculateNode> nnodes = null;
				nnodes = JSONArray.parseArray(now.getDataBase(), CalculateNode.class);
				return nnodes;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return nodes;
	}

	private List<Long> loadIds() {
		List<Long> list = new ThreadPoolArrayList<>(ImportConfig.getInstance().getCpuUse());
		CDataBaseDao dao = AppContextUtil.getContext().getBean(CDataBaseDao.class);
		List<Long> ids = dao.getAllId();
		for (int i = 0; i < ids.size(); i++) {
			Long id = Long.valueOf(ids.get(i) + "");
			if (id == 399001L || id > 100000)
				continue;
			list.add(id);
		}

		return list;
	}

}
