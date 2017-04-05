package com.dataImport;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileFilter;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;
import com.sql.dao.CDataBaseDao;
import com.sql.domain.CDataBasePo;
import com.sql.domain.CDataBasePoPK;
import com.util.AppContextUtil;

public class ImportJob {
	private EImportType type;
	private String dir;
	private CDataBaseDao dataBaseDao;

	public ImportJob(EImportType type, String dir) {
		this.type = type;
		this.dir = dir;
		dataBaseDao=AppContextUtil.getContext().getBean(CDataBaseDao.class);
	}

	public void runJob() {
		try {
			File src = new File(getDir());
			File[] files = src.listFiles(new FileFilter() {

				@Override
				public boolean accept(File pathname) {
					String name = pathname.getName();
					String[] names = name.split("\\.");
					try {

						Long.valueOf(names[0]);
						if (!names[1].equals("txt")) {
							return false;
						}
					} catch (Exception e) {
						return false;
					}
					return true;
				}
			});
			for (File f : files) {
				try {
					loadFile(f);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	private void loadFile(File f) throws Exception {
		BufferedReader di = new BufferedReader(new InputStreamReader(new DataInputStream(new FileInputStream(f))));
		int count = 0;
		String[] keys = null;
		long id = Long.valueOf(f.getName().split("\\.")[0]);
		for (String line = di.readLine(); line != null; line = di.readLine()) {
			String[] cells = line.split("\t");
			if (cells.length < 6) {
				continue;
			}
			if (count == 0) {
				try {
					keys = toKeys(cells);
				} catch (Exception e) {
					e.printStackTrace();
				}

			} else {
				CDataBasePo po = toPo(id, keys, cells);
				dataBaseDao.save(po);
			}
			count++;
		}
	}

	private static SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd");

	private CDataBasePo toPo(long id, String[] keys, String[] values) throws ParseException {
		CDataBasePo po = new CDataBasePo();
		CDataBasePoPK pk = new CDataBasePoPK();
		pk.setId(id);
		pk.setDate(dateFormat.parse(values[0]));
		po.setId(pk);
		JSONObject data = new JSONObject();
		for (int i = 1; i < values.length; i++) {
			try {
				data.put(keys[i], Double.valueOf(values[i]));
			} catch (Exception e) {
				data.put(keys[i], 0);
			}

		}
		po.setDataBase(data.toJSONString());
		return po;
	}

	private String[] toKeys(String[] cells) {
		String[] result = new String[cells.length];
		result[0] = "time";
		result[1] = "start";
		result[2] = "high";
		result[3] = "low";
		result[4] = "close";
		result[5] = "deal";

		for (int i = 6; i < cells.length; i++) {
			String cell = cells[i].trim();

			cell = cell.replaceAll("-", "");
			cell = cell.replaceAll("_", "");
			cell = cell.toLowerCase();
			String[] tempCell = cell.split("\\.");
			cell = tempCell[0];
			for (int j = 1; j < tempCell.length; j++) {
				char[] cs = tempCell[j].toCharArray();
				cs[0] -= 32;
				cell += String.valueOf(cs);
				;
			}
			cell = cell.replaceAll("\\.", "");
			result[i] = cell;
			System.err.println(cell);
		}
		return result;
	}

	public EImportType getType() {
		return type;
	}

	public void setType(EImportType type) {
		this.type = type;
	}

	public String getDir() {
		return dir;
	}

	public void setDir(String dir) {
		this.dir = dir;
	}
}
