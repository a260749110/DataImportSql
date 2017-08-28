package com.check.job;

import java.text.ParseException;
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
import com.check.cells.History;
import com.check.cells.LycjssFlagData;
import com.check.cells.ScoreColdList;
import com.comfig.Config;
import com.sql.dao.CDataBaseDao;
import com.sql.dao.CDataBaseResultDao;
import com.sql.domain.CDataBasePo;
import com.sql.domain.CDataResultPo;
import com.sql.domain.CDataResultPoPK;
import com.util.AppContextUtil;

public class LycjssFlagJob2 extends CheckJobsBase {

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
	private double buyPoint = 1.001;

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

		System.out.println("id :" + id + "  result:" + result + "  avg:" + po.getAvg() + "  use:"
				+ ((triCount == 0) ? 0 : useDay / triCount) + "   resultAll:" + resultAll);
	}

	private void iteration() {
		sl.add(1, 0);
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
				System.err.println("erro:" + LycjssFlagJob2.dateFormat.format(data.getDate()) + "  " + id + "   "
						+ data.getClose() + "   " + bf.getClose());
				histories.clear();
				continue;
			}

			// if (datas.get(i - 1).getLycjdmiFlagsums() > 0 &&
			// DapanData.getInstance().canTri(datas.get(i - 1))) {
			if (canBuy(bf, bbf)) {
				History history = new History();
				history.setSize(i);
				history.buySuccessFlag = buySuccess(data, bf, buyPoint);
				history.setStart(dateFormat.format(data.getDate()));
				history.setStartMoney(buyMoney(data, bf, buyPoint));
				histories.add(history);
				history.setScore(sl.getScore());
				// history.setScore( sl.getScore());
				history.now = data;
				history.bf = bf;
				history.bbf = bbf;
				history.index = i;
				// System.err.println("buy:" +
				// LycjssFlagJob.dateFormat.format(data.getDate()) + " " + id);
			}
			{

				removeList.clear();
				for (History history : histories) {
					double difH = (data.getHigh() - history.getStartMoney()) / history.getStartMoney();
					double difL = (data.getLow() - history.getStartMoney()) / history.getStartMoney();
					double difC = (data.getClose() - history.getStartMoney()) / history.getStartMoney();
					double difS = (data.getStart() - history.getStartMoney()) / history.getStartMoney();

					double difTS = (data.getStart() - bf.getClose()) / bf.getClose();
					double difTL = (data.getLow() - bf.getClose()) / bf.getClose();
					double difTH = (data.getHigh() - bf.getClose()) / bf.getClose();
					double difTC = (data.getClose() - bf.getClose()) / bf.getClose();

					double bdifH = (bf.getHigh() - history.getStartMoney()) / history.getStartMoney();
					double bdifL = (bf.getLow() - history.getStartMoney()) / history.getStartMoney();
					double bdifC = (bf.getClose() - history.getStartMoney()) / history.getStartMoney();
					double bdifS = (bf.getStart() - history.getStartMoney()) / history.getStartMoney();
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
					if (!okFlag) {
						if (bf.getLydmiSellflag() > 0) {
							dif = difS;
							okFlag = true;
						}
					}
					if (okFlag) {
						if ((history.getStartMoney() * (1 + win) < bf.getClose() * 1.101)
								&& (history.getStartMoney() * (1 + win) > bf.getClose() * 1.09)) {
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
									- LycjssFlagJob2.dateFormat.parse(history.getStart()).getTime())
									/ (3600l * 1000l * 24l);
						} catch (ParseException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						days = i - history.index;
						// if(history.index+150<i)
						if (days > Config.max_keep) {

							System.err.println("超时卖出" + "   id:" + id + "  时间: " + history.getStart() + " - "
									+ dateFormat.format(data.getDate()) + " " + history.getStartMoney() + "-"
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
							history.setEnd(dateFormat.format(data.getDate()));
							// System.err.println("S date:"+data.getDate()+"
							// "+data.getClose());
							history.setEndMoney(history.getStartMoney() * (1 + dif));
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
		if (now.getLydmiBuyflag() <= 0)
			return false;

		if (!DapanData.getInstance().canTri(now))
			return false;

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

}
