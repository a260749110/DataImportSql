package com.dataImport;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.List;

import org.apache.commons.httpclient.util.DateUtil;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;

import com.sql.dao.CBuySellDao;
import com.sql.dao.CHistoryBuySellDao;
import com.sql.domain.CBuySellPo;
import com.sql.domain.CHistoryBuySellPK;
import com.util.AppContextUtil;

public class ImporSimHistory {
	private static SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");

	public static void main(String[] args) throws IOException {

		FileReader fr = new FileReader("D://temp.txt");
		BufferedReader br = new BufferedReader(fr);

		String line = null;
		CBuySellDao dao = AppContextUtil.instance.getCBuySellDao();
		for (line = br.readLine(); line != null; line = br.readLine()) {
			if (!line.contains("����") && !line.contains("����")) {
				continue;
			}
			try {
				HistoryCell cell = new HistoryCell(line);

				// System.err.println(cell.getId());
				if (cell.isBuy()) {
					List<CBuySellPo> pos = dao.findById(cell.getId());
					if (pos != null && pos.size() != 0) {
						System.err.println(format.format(cell.getDate()) + " �޷�����:" + cell.getId() + ",��Ϊ���ݿ��Ѿ����ڸ�����");

					} else {
						CBuySellPo po = new CBuySellPo();
						po.setBuyDate(cell.getDate());
						po.setId((int) cell.getId());
						po.setBuySize(cell.getCount());
						po.setBuyMoney((double) cell.getMoney());
						po.setIsSell((byte) 0);
						dao.save(po);
						System.out.println(format.format(cell.getDate()) + " �ɹ�����:" + cell.getId() + "");
					}
				} else {

					List<CBuySellPo> pos = dao.findById(cell.getId());
					if (pos != null && pos.size() != 0) {
						CBuySellPo po = pos.get(0);
						po.setIsSell((byte) 1);
						po.setSellMoney((double) cell.getMoney());
						po.setSellDate(cell.getDate());
						dao.save(po);
						System.out.println(format.format(cell.getDate()) + " �ɹ�����:" + cell.getId() + "");

					} else {
						System.err.println(format.format(cell.getDate()) + " �޷�����:" + cell.getId() + ",��Ϊ���ݲ�����");
					}

				}

			} catch (Exception e) {
				e.printStackTrace();
			}
		}

	}

}
