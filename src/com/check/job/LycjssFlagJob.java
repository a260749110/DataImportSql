package com.check.job;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import com.alibaba.fastjson.JSON;
import com.check.CheckJobsBase;
import com.check.DapanData;
import com.check.EcheckType;
import com.check.cells.AllDatas;
import com.check.cells.AllHistory;
import com.check.cells.LycjssFlagData;
import com.check.cells.SaveOtherData;
import com.check.cells.ScoreColdList;
import com.check.cells.SuccessScore;
import com.comfig.Config;
import com.sql.dao.CDataBaseDao;
import com.sql.dao.CDataBaseResultDao;
import com.sql.domain.CDataBasePo;
import com.sql.domain.CDataResultPo;
import com.sql.domain.CDataResultPoPK;
import com.sql.util.DataBaseService;
import com.util.AppContextUtil;
import com.util.MathHelper;
import com.util.MathHelper.IGetValue;

public class LycjssFlagJob extends CheckJobsBase {

	List<LycjssFlagData> datas;
	double result = 1;
	static double resultAll = 1;
	public final static double loss = Config.lose_per;
	public final static double win = Config.win_per;
	Other other = new Other();
	ScoreColdList sl = new ScoreColdList(50);
	int useDay = 0;
	int triCount = 0;
	public static long success = 0;
	public static long unSuccess = 0;
	public static AllHistory allHistory = new AllHistory();
	public static Map<String, List<History>> historyMap = new HashMap<>();
	long id;
	private double buyPoint = 1.003;
	public static SuccessScore successScore = new SuccessScore();
	public AllDatas allDatas = new AllDatas();

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
		po.setScore(sl.getScore());
		dao.save(po);
		DataBaseService.saveSaveOtherData((int) id, other.toSaveOtherData());
		System.out.println("id :" + id + "  result:" + result + "  avg:" + po.getAvg() + "  use:"
				+ ((triCount == 0) ? 0 : useDay / triCount) + "   resultAll:" + resultAll);
	}

	private void iteration() {
		sl.add(1, 1);
		int count = 0;
		List<History> histories = new ArrayList<>();
		List<History> removeList = new ArrayList<>();
		for (int i = Config.return_size + 1; i < datas.size(); i++) {
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
				System.err.println("erro:" + LycjssFlagJob.dateFormat.format(data.getDate()) + "  " + id + "   "
						+ data.getClose() + "   " + bf.getClose());
				histories.clear();
				continue;
			}

			// if (datas.get(i - 1).getLycjdmiFlagsums() > 0 &&
			// DapanData.getInstance().canTri(datas.get(i - 1))) {
			if (canBuy(bf, bbf)) {
				History history = new History();
				history.size = i;
				history.buySuccessFlag = buySuccess(data, bf, buyPoint);
				history.start = dateFormat.format(data.getDate());
				history.startMoney = buyMoney(data, bf, buyPoint);
				histories.add(history);
				history.setScore(sl.getScore());
				// history.setScore( sl.getScore());
				history.now = data;
				history.bf = bf;
				history.bbf = bbf;
				history.index = i;
				history.variance = MathHelper.Variance(datas, i - 1, Config.return_size,
						new IGetValue<LycjssFlagData>() {

							@Override
							public double getValue(LycjssFlagData t) {
								// TODO Auto-generated method stub
								return t.getLycjdmiVdif();
							}

						});
				allDatas.add(bf);
				history.avgLycjdmiFlagsumsshow = allDatas.getCurrAvgLycjdmiFlagsumsshow();
				// System.err.println("buy:" +
				// LycjssFlagJob.dateFormat.format(data.getDate()) + " " + id);
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
							// System.err.println(data.getDate()+"difH"+"
							// "+difS);
						}
					}

					if (!okFlag) {
						if (bdifL <= loss && (difTH >= -0.099)) {

							dif = difS;
							okFlag = true;
						} else {
							if (bdifL <= loss && difTH < -0.099) {

								System.err.println("跌停无法出售" + " id:" + id + " 时间:" + dateFormat.format(data.getDate()));
							}
						}
					}
					// if (!okFlag) {
					// if (difL <= loss && data.getClose() / bf.getClose() >=
					// 0.901) {
					//
					// dif = difC;
					// okFlag = true;
					// } else {
					// if (difL <= loss && data.getClose() / bf.getClose() <
					// 0.901) {
					//
					// // System.err.println("跌停无法出售" +" id:" +id+"
					// // 时间:" + dateFormat.format(data.getDate()));
					// }
					// }
					// }
					if (okFlag) {
						if ((history.startMoney * (1 + win) < bf.getClose() * 1.101)
								&& (history.startMoney * (1 + win) > bf.getClose() * 1.09)) {
							// okFlag=false;
							// System.err.println("涉及涨停无法出售" +" id:" +id+" 时间:"
							// + dateFormat.format(data.getDate())+"
							// buy:"+history.start+" "+history.startMoney+"
							// close:"+data.getClose());
						}
						if (data.getStart() / bf.getClose() > 1.099 && data.getClose() / bf.getClose() > 1.099) {
							// okFlag=false;
						}
					}
					// if(!okFlag)
					// {
					// if(history.index==i-1)
					// if(history.now.getMacdMacd()<history.bf.getMacdMacd())
					// {
					// okFlag=true;
					// dif=difC;
					// }
					// }
					// if(!okFlag)
					{
						long days = 0;
						try {
							days = (data.getDate().getTime()
									- LycjssFlagJob.dateFormat.parse(history.getStart()).getTime())
									/ (3600l * 1000l * 24l);
						} catch (ParseException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						days = i - history.index;
						// if(history.index+150<i)
						if (days > Config.max_keep) {

							System.err.println("超时卖出" + "   id:" + id + "  时间: " + history.getStart() + " - "
									+ dateFormat.format(data.getDate()) + " " + history.startMoney + "-"
									+ data.getClose());
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
						// score += dif;
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
							// System.err.println("S date:"+data.getDate()+"
							// "+data.getClose());
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
							// System.err.println("fullll");
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
									// TODO Auto-generated catch block
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

	// public static boolean canBuy(LycjssFlagData now, LycjssFlagData bf) {
	// if (now.getLydmiBuyflag()<= 0)
	// return false;
	////
	// if (now.getMacdMacd() <= bf.getMacdMacd())
	// return false;
	// if (!DapanData.getInstance().canTri(now))
	// return false;
	//// if (bf.getLycjdmiHightsum() <= bf.getLycjdmiVdif())
	//// return false;
	//
	// // if (now.getClose()>= bf.getClose()*1.097)
	// // return false;
	// // if (now.getLycjssVma() <= now.getLycjssVpr())
	// // return false;
	// return true;
	// }
	public static boolean canBuy(LycjssFlagData now, LycjssFlagData bf) {
		if (now.getLycjdmiFlagsums() <= 0)
			return false;

		if (Math.round(now.getMacdMacd() / 10) <= Math.round(bf.getMacdMacd() / 10))
			return false;
		if (!DapanData.getInstance().canTri(now))
			return false;
		// if (bf.getLycjdmiHightsum() <= bf.getLycjdmiVdif())
		// return false;
		// if((now.getLydmiAdxr()+now.getLydmiMdi())>now.getLydmiAdx()*3)
		// return false;
		// if (now.getClose()>= bf.getClose()*1.097)
		// return false;
		// if (now.getLycjssVma() <= now.getLycjssVpr())
		// return false;
		return true;
	}
	// public static boolean canBuy(LycjssFlagData now, LycjssFlagData bf) {
	// if (now.getLykdjBuyflag() <= 0)
	// return false;
	//
	// if (now.getMacdMacd() <= bf.getMacdMacd())
	// return false;
	// if (!DapanData.getInstance().canTri(now))
	// return false;
	// if (bf.getLycjdmiHightsum() <= bf.getLycjdmiVdif())
	// return false;
	//
	// // if (now.getClose()>= bf.getClose()*1.097)
	// // return false;
	// // if (now.getLycjssVma() <= now.getLycjssVpr())
	// // return false;
	// return true;
	// }

	public static boolean canBuy(double score) {

		if (score < 1) {
			double rand = random.nextDouble();
			rand = Math.pow(rand, 1);
			if (rand > score) {
				// System.out.println("can not buy:" + score + " " + rand);
				return true;
				// return false;
			}
		}
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
		public int index = 0;
		public boolean takeFlag = false;
		public LycjssFlagData now;
		public LycjssFlagData bf;
		public LycjssFlagData bbf;
		public double avgLycjdmiFlagsumsshow;
		public boolean buySuccessFlag = false;
		private double variance;

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

		public double getVariance() {
			return variance;
		}

		public void setVariance(double variance) {
			this.variance = variance;
		}
	}

}
