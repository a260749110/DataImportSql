package com.bigcalculate.job;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.bigcalculate.cell.CalculateNode;
import com.check.EcheckType;
import com.check.cells.AllDatas;
import com.check.cells.History;
import com.check.cells.LycjssFlagData;
import com.check.cells.SaveOtherData;
import com.check.cells.ScoreColdList;
import com.check.cells.SuccessScore;
import com.check.job.LycjssFlagJob;
import com.comfig.Config;
import com.comfig.ImportConfig;
import com.sql.dao.CDataBaseDao;
import com.sql.domain.CDataBasePo;
import com.sql.domain.CDataResultPo;
import com.sql.domain.CDataResultPoPK;
import com.util.AppContextUtil;
import com.util.Helper;
import com.util.MathHelper;
import com.util.MathHelper.IGetValue;
import com.util.RandomConfig;

public class Caluculate {

	private CalculateNode calculateNode;

	public Caluculate(CalculateNode calculateNode, DaySimulationJob daySimulationJob) {

		this.setCalculateNode(calculateNode);
		allHistory = daySimulationJob;
	}
	public static Map<Long, List<LycjssFlagData>> cacheMap = new HashMap<>();
	List<LycjssFlagData> datas;
	public double result = 1;
	public double resultAll = 1;
	public final static double loss = Config.lose_per;
	public final static double win = Config.win_per;
	Other other = new Other();
	ScoreColdList sl = new ScoreColdList(50);
	int useDay = 0;
	int triCount = 0;
	public  long success = 0;
	public  long unSuccess = 0;
	public DaySimulationJob allHistory;
	long id;
	private double buyPoint = 1.003;
	public  SuccessScore successScore = new SuccessScore();
	public AllDatas allDatas = new AllDatas();
	public  int tempCount = 0;

	public void run(long id) {
		this.id = id;
		init(id);
		iteration();
		tempCount++;
		if (tempCount % 100 == 0)
			System.out.println("id :" + id + "  result:" + result + "  use:" + ((triCount == 0) ? 0 : useDay / triCount)
					+ "   resultAll:" + resultAll);
	}

	private void iteration() {
		sl.add(1, 1);
		int count = 0;
		List<History> histories = new ArrayList<>();
		List<History> removeList = new ArrayList<>();
		for (int i = Config.return_size + 1; i < datas.size(); i++) {
			LycjssFlagData data = datas.get(i);
			if (data.getStart() <= 0.1)

			{
				histories.clear();
				continue;
			}
			LycjssFlagData bf = datas.get(i - 1);
			LycjssFlagData bbf = datas.get(i - 2);
			double change = data.getClose() / bf.getClose();
			if (change > 1.15 || change < 0.85) {
				System.err.println("erro:" + dateFormat.format(data.getDate()) + "  " + id + "   "
						+ data.getClose() + "   " + bf.getClose());
				histories.clear();
				continue;
			}

			if (canBuy(bf, bbf)) {
				History history = new History();
				history.size = i;
				history.buySuccessFlag = buySuccess(data, bf, buyPoint);
				history.start = dateFormat.format(data.getDate());
				history.startMoney = buyMoney(data, bf, buyPoint);
				histories.add(history);
				history.setScore(tempScore);
				history.now = data;
				history.bf = bf;
				history.bbf = bbf;
				history.index = i;
				history.variance = MathHelper.Variance(datas, i - 1, Config.return_size,
						new IGetValue<LycjssFlagData>() {

							@Override
							public double getValue(LycjssFlagData t) {
								return t.getLycjdmiVdif();
							}

						});
				allDatas.add(bf);
				history.avgLycjdmiFlagsumsshow = allDatas.getCurrAvgLycjdmiFlagsumsshow();
			}
			{

				removeList.clear();
				for (History history : histories) {
					double difH = (data.getHigh() - history.startMoney) / history.startMoney;
					double difL = (data.getLow() - history.startMoney) / history.startMoney;
					double difC = (data.getClose() - history.startMoney) / history.startMoney;
					double difS = (data.getStart() - history.startMoney) / history.startMoney;

					double difTS = (data.getStart() - bf.getClose()) / bf.getClose();
					double difTL = (data.getLow() - bf.getClose()) / bf.getClose();
					double difTH = (data.getHigh() - bf.getClose()) / bf.getClose();
					double difTC = (data.getClose() - bf.getClose()) / bf.getClose();

					double bdifH = (bf.getHigh() - history.startMoney) / history.startMoney;
					double bdifL = (bf.getLow() - history.startMoney) / history.startMoney;
					double bdifC = (bf.getClose() - history.startMoney) / history.startMoney;
					double bdifS = (bf.getStart() - history.startMoney) / history.startMoney;
					double dif = 0;
					boolean okFlag = false;
					if (difS < difH || difTS < 0.098) {
						if (difH > win) {
							double av = (difH + difS) / 2d;
							if (difS > win) {
								dif = difS;
								okFlag = true;
							} else {
								dif = win;
								okFlag = true;
							}
						}
					}

					if (!okFlag) {
						if (bdifL <= loss && (difTH >= -0.099)) {

							dif = difS;
							okFlag = true;
						} else {
							if (bdifL <= loss && difTH < -0.099) {

								//System.err.println("跌停无法出售" + " id:" + id + " 时间:" + dateFormat.format(data.getDate()));
							}
						}
					}
					if (okFlag) {
						if ((history.startMoney * (1 + win) < bf.getClose() * 1.101)
								&& (history.startMoney * (1 + win) > bf.getClose() * 1.09)) {
						}
						if (data.getStart() / bf.getClose() > 1.099 && data.getClose() / bf.getClose() > 1.099) {
						}
					}
					{
						long days = 0;
						try {
							days = (data.getDate().getTime()
									- dateFormat.parse(history.getStart()).getTime())
									/ (3600l * 1000l * 24l);
						} catch (ParseException e) {
							System.err.println(history.getStart());
							e.printStackTrace();
						}
						days = i - history.index;
						if (days > Config.max_keep) {

							okFlag = true;
							dif = difC;
						}
						if (days == 0) {
							okFlag = false;
						}
						if (days < 0) {
							System.err.println("eeeeeeeeeeeeeeeeeeee" + " id:" + id + "  时间: " + history.getStart());
						}
					}

					if (okFlag) {
						sl.add(dif, i - history.getSize());
						if (canBuy(history.getScore())) {

							if (dif > 0) {
								successScore.add(history.getScore());
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
					} else {
						if (i == datas.size() - 1) {
							dif = difC;
							if (canBuy(history.getScore())) {

								if (dif > 0) {
									successScore.add(history.getScore());
									success++;
								} else {
									unSuccess++;
								}
								result += dif;
								resultAll += dif;
								useDay += i - history.getSize();
								history.setSize(i - history.getSize());
								history.setId(id);
								history.takeFlag = true;
								triCount++;
								history.setDif(dif);
								history.setNowWin(result);
								try {
									history.end = dateFormat.format(new Date());
								} catch (Exception e) {
									e.printStackTrace();
								}
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

				}
				histories.removeAll(removeList);
			}
		}
	}

	private void init(long id) {
		CDataBaseDao dao = AppContextUtil.getContext().getBean(CDataBaseDao.class);
		if (cacheMap.containsKey(id)) {

			datas = cacheMap.get(id);
//			String js = JSONArray.toJSONString(datas);
//			List<LycjssFlagData> tempD = JSONArray.parseArray(js, LycjssFlagData.class);
//			datas = tempD;
			return;
		}
		datas = new ArrayList<>();
		List<CDataBasePo> pos = dao.findById(id);
		for (int i = 15; i < pos.size(); i++) {

			LycjssFlagData data = JSON.parseObject(pos.get(i).getDataBase(), LycjssFlagData.class);
			data.setDate(pos.get(i).getId().getDate());
			datas.add(data);
		}
		String js = JSONArray.toJSONString(datas);
		List<LycjssFlagData> tempD = JSONArray.parseArray(js, LycjssFlagData.class);
		cacheMap.put(id, tempD);
	}

	public static class Other {
		private List<History> historys = new ArrayList<>();
		private double avgLycjdmiFlagsumsshow;

		public List<History> getHistorys() {
			return historys;
		}

		public SaveOtherData toSaveOtherData() {
			SaveOtherData saveOtherData = new SaveOtherData();
			if (historys.size() <= 0)
				return saveOtherData;
			for (History h : historys) {
				saveOtherData
						.setLycjdmiFlagsumsshow(saveOtherData.getLycjdmiFlagsumsshow() + h.bf.getLycjdmiFlagsumsshow());
			}
			saveOtherData.setLycjdmiFlagsumsshow(saveOtherData.getLycjdmiFlagsumsshow() / historys.size());
			return saveOtherData;
		}

		public void setHistorys(List<History> historys) {
			this.historys = historys;
		}

		public double getAvgLycjdmiFlagsumsshow() {
			return avgLycjdmiFlagsumsshow;
		}

		public void setAvgLycjdmiFlagsumsshow(double avgLycjdmiFlagsumsshow) {
			this.avgLycjdmiFlagsumsshow = avgLycjdmiFlagsumsshow;
		}
	}

	private static Random random = new Random();
	public float tempScore = 0;

	public boolean canBuy(LycjssFlagData now, LycjssFlagData bf) {
		tempScore = 0;
		Helper.eachField(now, LycjssFlagData.class, (f, n, v, flter) -> {
			try {
				if (flter != null && !((RandomConfig) flter).enable()) {
					return;
				}
				if (!getCalculateNode().getTodayP().containsKey(n)) {
					getCalculateNode().getTodayP().put(n, ImportConfig.getInstance().getDef_parameter());
				}
				tempScore += Float.valueOf(v.toString()) * getCalculateNode().getTodayP().get(n);
			} catch (Exception e) {
				System.err.println("name:" + n);
				e.printStackTrace();
			}

		}, RandomConfig.class);

		Helper.eachField(bf, LycjssFlagData.class, (f, n, v, flter) -> {
			try {
				if (flter != null && !((RandomConfig) flter).enable() && !((RandomConfig) flter).calculateYestoday()) {
					return;
				}

				if (!getCalculateNode().getYestodayP().containsKey(n)) {
					getCalculateNode().getYestodayP().put(n, ImportConfig.getInstance().getDef_parameter());
				}
				tempScore += Float.valueOf(v.toString()) * getCalculateNode().getYestodayP().get(n);
			} catch (Exception e) {
				System.err.println("name:" + n);
				e.printStackTrace();
			}

		}, RandomConfig.class);

		if (tempScore > 0) {
			return true;
		}
		return false;

	}

	public static boolean canBuy(double score) {

		// if (score < 1) {
		// double rand = random.nextDouble();
		// rand = Math.pow(rand, 1);
		// if (rand > score) {
		// return true;
		// }
		// }
		return true;
	}

	public double buyMoney(LycjssFlagData now, LycjssFlagData bf, double buyPoint) {

		return now.getStart() * buyPoint;
	}

	public boolean buySuccess(LycjssFlagData now, LycjssFlagData bf, double buyPoint) {
		if (now.getLow() <= buyMoney(now, bf, buyPoint) && (now.getLow() <= bf.getClose() * 1.099)) {
			return true;

		}
		return false;
	}

	public CalculateNode getCalculateNode() {
		return calculateNode;
	}

	public void setCalculateNode(CalculateNode calculateNode) {
		this.calculateNode = calculateNode;
	}

	public  SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd");

}
