package com.bigcalculate;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import com.alibaba.fastjson.JSON;
import com.bigcalculate.cell.CalculateNode;
import com.bigcalculate.job.BigCalculateJob;
import com.bigcalculate.job.Caluculate;
import com.bigcalculate.job.DaySimulationJob;
import com.comfig.ImportConfig;
import com.sql.domain.CBigCalculatePo;
import com.util.AppContextUtil;
import com.util.Helper;

public class BigCalculateMain {

	public static void main(String[] args) {
		System.err.println("run thread:" + ImportConfig.getInstance().getThread_num());
		startTime = System.currentTimeMillis();
		Thread t = new Thread() {
			public void run() {
				run1();
			};
		};
		t.start();

	}

	private static int count = 0;
	private static List<CalculateNode> nodes = new ArrayList<>();
	private static int size = 500;
	private static long allTime = 0;
	private static int allCount = 0;
	private static long startTime;
	private static Lock lock = new ReentrantLock();
	private static int max;
	private static List<Integer> tList = new ArrayList<>();

	private static void run1() {
		int tc = count;

//		lock.lock();
//		try {
			count++;
//		} finally {
//			lock.unlock();
//		}
		tList.add(tc);
		while (true) {
			try {
				long start = System.currentTimeMillis();
//				lock.lock();
//				try {
//					allCount++;
//				} finally {
//					lock.unlock();
//				}
				allCount++;
				BigCalculateJob bigCalculateJob = new BigCalculateJob();
				bigCalculateJob.init();

				CBigCalculatePo cb = Helper.getRandomCalculatePo();
				if (cb == null) {
					cb = new CBigCalculatePo();
				}
				cb = bigCalculateJob.run(cb);
				AppContextUtil.instance.getCBigCalculateDao().save(cb);
				Helper.showMem();
				if (count < ImportConfig.getInstance().getThread_num()) {
					Thread t = new Thread() {
						public void run() {
							try {
								Thread.sleep(30000L);
							} catch (InterruptedException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
							run1();
						};
					};
					t.start();
				}
				System.err.println("�Ѿ����У�" + ((System.currentTimeMillis() - startTime) / 1000L / 60L) + "����" + " ִ��:"
						+ allCount + "�� �����У�" + JSON.toJSONString(tList));
				System.err.println(
						tc + "  :use:" + (-((double) start - (double) System.currentTimeMillis()) / 60000d) + "����");
				allTime += System.currentTimeMillis() - start;
				System.err.println(tc + "  :all use:" + (((float) (allTime / allCount)) / 60000f) + "����");
			} catch (Throwable e) {
				e.printStackTrace();
			}
		}
	}

	// public static void fun() {
	// long id = 1;
	//
	// CalculateNode node = new CalculateNode();
	// nodes.add(node);
	// DecimalFormat df = new DecimalFormat("0.00");
	// Random random = new Random();
	// for (int i = 0; i < 5000000; i++) {
	// if (nodes.size() >= size) {
	// node = nodes.get(random.nextInt(nodes.size()));
	// } else {
	// System.err.println("new");
	// node = new CalculateNode();
	// }
	// node = Helper.randomNode(node);
	// nodes.add(node);
	// Caluculate caluculate = new Caluculate(node, new DaySimulationJob());
	// caluculate.run(id);
	// node.setScore((float) caluculate.resultAll);
	// if (nodes.size() > size) {
	// CalculateNode nodeRemove = null;
	// for (CalculateNode n : nodes) {
	// if (nodeRemove == null) {
	// nodeRemove = n;
	// } else {
	// if (nodeRemove.getScore() > n.getScore()) {
	// nodeRemove = n;
	// }
	// }
	// }
	// if (nodeRemove != null) {
	// nodes.remove(nodeRemove);
	// nodeRemove.getTodayP().clear();
	// nodeRemove.getYestodayP().clear();
	// }
	// }
	//
	// // ��ʾJVM���ڴ�
	// long totalMem = Runtime.getRuntime().totalMemory();
	// System.out.println(df.format(totalMem / 1000000F) + " MB");
	// // ��ʾJVM����ʹ�õ�����ڴ�
	// long maxMem = Runtime.getRuntime().maxMemory();
	// System.out.println(df.format(maxMem / 1000000F) + " MB");
	// // �����ڴ�
	// long freeMem = Runtime.getRuntime().freeMemory();
	// System.out.println(df.format(freeMem / 1000000F) + " MB");
	// System.err.println("size:" + nodes.size());
	//
	// if (i % 1000 == 0) {
	// System.gc();
	// try {
	// Thread.sleep(1000);
	// } catch (InterruptedException e) {
	// // TODO Auto-generated catch block
	// e.printStackTrace();
	// }
	// }
	// }
	//
	// }
}
