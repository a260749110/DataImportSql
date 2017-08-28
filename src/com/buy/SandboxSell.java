package com.buy;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import com.check.cells.LycjssFlagData;
import com.comfig.Config;
import com.sql.domain.CBuySellPo;
import com.sql.domain.CDataBasePo;
import com.util.AppContextUtil;
import com.util.DateHelper;

public class SandboxSell {
	public List<CBuySellPo> buyList;

	public SandboxSell()

	{
		init();
	}

	public static void main(String[] args) {
		SandboxSell sandboxSell = new SandboxSell();
		System.err.println("\r\n");
		sandboxSell.run();
	}

	public void init() {
		buyList = AppContextUtil.instance.getCBuySellDao().findAll();
	}

	public void run() {
		for (CBuySellPo po : buyList) {
			List<LycjssFlagData> datas = getDataBase(po);
			testCanSell(po, datas);
		}
	}

	public void testCanSell(CBuySellPo po, List<LycjssFlagData> datas) {

		StringBuffer sb = new StringBuffer();
		if (datas.size() > Config.max_keep) {
			System.err.println("id:" + po.getId() + "��������������" + Config.max_keep + "��");
		}
		for (int i = 0; i < datas.size(); i++) {
			LycjssFlagData data = datas.get(i);
			double dif = (data.getClose() - po.getBuyMoney()) / po.getBuyMoney() * 100;
			BigDecimal bg = new BigDecimal(dif).setScale(2, RoundingMode.UP);
			LycjssFlagData dbf=data;
			if(i>0)
			{
				dbf=datas.get(i-1);
			}
			double todayDif=(data.getClose()-dbf.getClose())/dbf.getClose();
			dif = bg.doubleValue();
			DecimalFormat df = new DecimalFormat("######0.00");
			if (po.getBuyMoney() * (1 + Config.lose_per) > data.getLow()) {
				if(todayDif<-0.097&&todayDif>=-0.10)
				{
					sb.append("id:" + po.getId() + "��ͣ" + DateHelper.dateFormat.format(data.getDate()) + "�޷�ֹ�������������۸�"
							+ data.getClose()+"�¸�������Ӧ������ʱ����").append("\r\n");
				}
				else
				{
				sb.append("id:" + po.getId() + "Ӧ��" + DateHelper.dateFormat.format(data.getDate()) + "ֹ�������������۸�"
						+ data.getClose()).append("\r\n");
				}
			}
			if (po.getBuyMoney() * (1 + Config.win_per) < data.getHigh()) {
				sb.append("id:" + po.getId() + "Ӧ��" + DateHelper.dateFormat.format(data.getDate()) + "ֹӯ�����������۸�"
						+ (po.getBuyMoney() * (1 + Config.win_per))).append("\r\n");
			}


			if (i == datas.size() - 1) {
				po.setLastDate(data.getDate());
				po.setLastMoney(data.getClose());
				po.setLastDif(dif);
				AppContextUtil.instance.getCBuySellDao().save(po);
				if ((po.getBuyMoney() * (1 + Config.win_per) < data.getClose() * 1.101)
						&& (po.getBuyMoney() * (1 + Config.win_per) > data.getClose() * 1.09)
						&& (i == datas.size() - 1)) {
//					sb.append("id:" + po.getId() + " ��" + DateHelper.dateFormat.format(data.getDate()) + "�����ͣ����Ҫ��������"
//							+ "������۸�" + po.getBuyMoney() * (1 + Config.win_per)).append("\r\n");
				} else if ((po.getBuyMoney() * (1 + Config.win_per) <= data.getClose() * 1.101)) {
					System.err.println("id:" + po.getId() + " �¸������չң�"
							+ df.format(po.getBuyMoney() * (1 + Config.win_per)) + "����-----------");
				}
				if (po.getBuyMoney() * (1 + Config.lose_per) >= data.getClose() * 0.9) {
//					System.out.println("id:" + po.getId() + " �¸�������С�ڣ�"
//							+ df.format(po.getBuyMoney() * (1 + Config.lose_per)) + "����");
				}
			}
		}
		if (sb.length() > 0)
			System.err.println(sb);

	}

	public List<LycjssFlagData> getDataBase(CBuySellPo po) {
		List<CDataBasePo> datas = AppContextUtil.instance.getCDataBaseDao().findAfByIdAndDate(po.getId(),
				DateHelper.dateFormat.format(po.getBuyDate()));
		List<LycjssFlagData> ndatas = new ArrayList<>();
		for (CDataBasePo dpo : datas) {

			ndatas.add(LycjssFlagData.createData(dpo));
		}
		return ndatas;
	}

	public static class TestResult {

	}
}
