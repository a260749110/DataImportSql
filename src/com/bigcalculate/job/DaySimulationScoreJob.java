package com.bigcalculate.job;

import java.math.BigDecimal;
import java.text.ParseException;
import java.util.List;

import com.check.cells.History;
import com.comfig.Config;
import com.comfig.ImportConfig;

public class DaySimulationScoreJob extends DaySimulationJob {
	private double difScore;
	private int count;
	private BigDecimal success = new BigDecimal(1f);
	private BigDecimal fail = new BigDecimal(1f);
	private int rSuccess;
	private int rFail;

	private double successWin = 0;
	private double successLoss = 0;
	private double failWin = 0;
	private double failLoss = 0;
	private double allWin = 0;
	private double allLose = 0;
	private long successWinCount = 1;
	private long successLossCount = 1;
	private long failWinCount = 1;
	private long failLossCount = 1;
	private static double[] cache;

	public DaySimulationScoreJob() {
		super();
		if (cache == null) {
			cache = new double[5000];
			for (int i = 0; i < cache.length; i++) {
				cache[i] = Math.pow(ImportConfig.getInstance().getScoreDown(), i);
			}
		}
	}

	@Override
	public ConfigAndReesult toSql() throws ParseException {
		ConfigAndReesult andReesult = new ConfigAndReesult();
		andReesult.monetResylt = difScore;
		// this.moneyALL = ((double) rSuccess) / rFail;
		this.moneyALL = difScore;
		YearResult yearResult = new YearResult();
		double ra = success.doubleValue() / fail.doubleValue();
		yearResult.setStartMoney(ra);
		andReesult.yearResults.add(yearResult);

		double succcessUp = ((double) (successWinCount + successLossCount)
				* (double) Long.min(successWinCount, successLossCount))
				/ ((double) (failWinCount + failLossCount) * (double) Long.max(failWinCount, failLossCount));

		succcessUp = Math.pow(succcessUp, ImportConfig.getInstance().getRaUp());

		if (difScore > 0) {
			difScore = Math.pow(difScore, 0.3);
		} else {
			difScore = -Math.pow(-difScore, 0.3);
		}
		this.dscore = difScore * Math.pow(ra, ImportConfig.getInstance().getRaUp()) * (succcessUp);
		this.dscore /= count;
		System.err.println("allWin:" + allWin + "   allLose:" + allLose + "  sum:" + (allWin + allLose));

		System.err.println("scoure:" + difScore + "  ra:" + Math.pow(ra, ImportConfig.getInstance().getRaUp())
				+ "  sucessUp:" + (succcessUp) + "   result:" + this.dscore);

		System.err.println("¹²:" + count + "  success:" + success + "   fail:" + fail + "  scoure:" + difScore
				+ "  rSuccess:" + rSuccess + " rFail:" + rFail);

		System.err.println("successWin:" + successWin + "  successWinCount:" + successWinCount + "  successLoss:"
				+ successLoss + " successLossCount:" + successLossCount + "\r\n  failWin:" + failWin + "  failWinCount:"
				+ failWinCount + "  failLoss:" + failLoss + "  failLossCount:" + failLossCount + " \r\n");

		return andReesult;
	}

	@Override
	public synchronized void addHistory(History history) {
		if (!history.buySuccessFlag) {
			return;
		}
		float pow = ImportConfig.getInstance().getScorePow();

		if (history.getScore() > 0) {
			history.setScore(Math.pow(history.getScore(), pow));
		} else {

			history.setScore(-Math.pow(-history.getScore(), pow));
		}
		// double temp = Math.pow(ImportConfig.getInstance().getScoreDown(),
		// history.getAllSize() - history.getiSize())
		// * history.dif * history.getScore();
		double temp = (cache[history.getAllSize() - history.getiSize()] * history.dif * history.getScore())
				/ (Math.pow(history.usersI, 0.3));
		// System.err.println("ss:"+history.usersI);
		// System.err.println("date:" +
		// dateHelper.dateFormat.format(history.getStart()) + " i:"
		// + (history.getAllSize() - history.getiSize())+"
		// "+cache[history.getAllSize() - history.getiSize()]);
		count++;

		if (history.getScore() > 0) {
			if (history.dif > 0) {
				allWin += history.dif;
			} else {
				allLose += history.dif;
			}
		}

		if (temp > 0) {
			success = success.add(new BigDecimal(cache[history.getAllSize() - history.getiSize()]));
			if (history.dif > 0) {
				successWin += temp;
				successWinCount++;
				rSuccess++;
				temp = temp * 2;
			} else {
				successLossCount++;
				successLoss += temp;
				rFail++;
				temp = 0;
			}
		} else {
			temp = temp * ImportConfig.getInstance().getFailRate();
			fail = fail.add(new BigDecimal(cache[history.getAllSize() - history.getiSize()]));
			if (history.dif > 0) {
				failWinCount++;
				failWin += temp;
			} else {
				failLossCount++;
				failLoss += temp;
			}
		}
		difScore += temp;
		// System.err.println(history.getAllSize()+" "+history.getiSize()+"
		// "+history.id);
		// System.err.println( history.getScore()+" "+history.dif+" "+
		// (history.getAllSize()-history.getSize())+ "sc:"+
		// Math.pow(ImportConfig.getInstance().getScoreDown(),
		// history.getAllSize()-history.getSize())*history.dif*history.getScore());
		history.now.setBuyHistory(history);
	}
}
