package com.check.job;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import com.alibaba.fastjson.JSON;
import com.check.EcheckType;
import com.check.cells.AllHistory;
import com.check.cells.LycjssFlagData;
import com.check.job.LycjssFlagJob.History;
import com.check.job.LycjssFlagJob.Other;
import com.sql.domain.CDataResultPo;
import com.sql.domain.CDataResultPoPK;
import com.util.AppContextUtil;
import com.util.Helper;
import com.util.MathHelper;

public class HistoryCheckJob {
	public List<History> historys = new ArrayList<>();
	public AllHistory allHistory = new AllHistory();
	public List<Other> otherList = new ArrayList<>();

	public HistoryCheckJob() {

	}

	public void load(List<Long> ids) {
		List<LycjssFlagData> tDatas = new ArrayList<>();
		for (int i = 0; i < ids.size(); i++) {
			long id = Long.valueOf(ids.get(i) + "");
			if (id == 399001L || id > 100000)
				continue;

			CDataResultPoPK pk = new CDataResultPoPK();
			pk.setId(id);
			pk.setType(EcheckType.LYCJSS_FLAG.toString());
			CDataResultPo po = AppContextUtil.instance.getCDataBaseResultDao().findOne(pk);
			if (po == null) {
				System.err.println("null");
				continue;
			}

			Other other = JSON.parseObject(po.getOther(), Other.class);
			historys.addAll(other.getHistorys());

			otherList.add(other);

			List<LycjssFlagData> sDatas = new ArrayList<>();
			List<LycjssFlagData> fDatas = new ArrayList<>();

			List<LycjssFlagData> sbdifDatas = new ArrayList<>();
			List<LycjssFlagData> fbdifDatas = new ArrayList<>();
			other.getHistorys().forEach(data -> {
				if (data.getDif() > 0) {
					sDatas.add(data.bf);
					sbdifDatas.add(Helper.containsT(data.bf, data.bbf, LycjssFlagData.class));

				} else {
					fDatas.add(data.bf);
					fbdifDatas.add(Helper.containsT(data.bf, data.bbf, LycjssFlagData.class));
				}
			});
			if (sDatas.size() > 3 || fDatas.size() > 3) {
				LycjssFlagData sd = Helper.avgList(sDatas, LycjssFlagData.class);
				LycjssFlagData fd = Helper.avgList(fDatas, LycjssFlagData.class);
				LycjssFlagData difd = Helper.containsT(sd, fd, LycjssFlagData.class);

				LycjssFlagData bsd = Helper.avgList(sbdifDatas, LycjssFlagData.class);
				LycjssFlagData bfd = Helper.avgList(fbdifDatas, LycjssFlagData.class);
				LycjssFlagData bdifd = Helper.containsT(bsd, bfd, LycjssFlagData.class);
				sd.setId(id);
				fd.setId(id);
				// System.err.println(id+" "+sDatas.size()+":"+fDatas.size()+"
				// sss:" + JSON.toJSONString(sd));
				// System.err.println(id+" "+sDatas.size()+":"+fDatas.size()+"
				// fff:" + JSON.toJSONString(fd));
				System.err
						.println(id + "  " + sDatas.size() + ":" + fDatas.size() + " ddd:" + JSON.toJSONString(bdifd));
				tDatas.add(bdifd);
			}
		}
		LycjssFlagData ttt = Helper.varianceList(tDatas, LycjssFlagData.class);
		LycjssFlagData tttavg = Helper.avgList(tDatas, LycjssFlagData.class);
		System.err.println(" ttt:" + " " + tDatas.size() + "  " + JSON.toJSONString(ttt));
		System.err.println(" tttavg:" + " " + tDatas.size() + "  " + JSON.toJSONString(tttavg));
		System.err.println(JSON
				.toJSONString(Helper.sortT(Helper.incAbsT(tttavg, 0.0, LycjssFlagData.class), LycjssFlagData.class)));
		allHistory.addHistorys(historys);
		try {
			allHistory.toSql();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		checkAllHistorys();
	}

	private void checkAllHistorys() {
		List<History> sl = new ArrayList<>();
		List<History> fl = new ArrayList<>();
		List<LycjssFlagData> csl = new ArrayList<>();
		List<LycjssFlagData> cfl = new ArrayList<>();
		
		
		
		
		
		for (History h : historys) {
			if (h.getDif() > 0) {
				sl.add(h);
			}
			if (h.getDif() < 0) {
				fl.add(h);
			}
			if (h.getDif() == 0) {
				System.err.println("eee");
			}
		}

		for (History h : sl) {
			csl.add(Helper.mutT(h.bf, h.bbf, LycjssFlagData.class));

		}
		for (History h : fl) {
			cfl.add(Helper.mutT(h.bf, h.bbf, LycjssFlagData.class));
		}

		LycjssFlagData avgvsl = Helper.avgList(csl, LycjssFlagData.class);
		LycjssFlagData avgvfl = Helper.avgList(cfl, LycjssFlagData.class);
  
		
		System.err.println("tts:   "+JSON.toJSONString(avgvsl));
		System.err.println("ttf:   "+JSON.toJSONString(avgvfl));
		LycjssFlagData result = Helper.difArv(avgvsl, avgvfl, LycjssFlagData.class);
		System.err.println(JSON
				.toJSONString(result));
		System.err.println(JSON
				.toJSONString(Helper.sortT(Helper.incAbsT(result, 0.5, LycjssFlagData.class), LycjssFlagData.class)));

	}
}
