package com.comfig;

import java.util.Date;

import com.check.job.LycjssFlagJob;

public class Config {
public static double win_per=0.12;
public static double lose_per=-0.10;
public static int max_keep=120;
//�� �������Ƿ����� ������ֵ�ʱ�����ͣ�۸�
public static double h_pr=1.09;
//�� �������Ƿ����� h_pr ��ʱ�� �Ƿ�����
public static boolean h_pr_Flag=false; 
public static int return_size=10; 
public static Date startDate= new Date(1167580800000L);
public static void main(String[] args) {
	System.err.println(LycjssFlagJob.dateFormat.format(startDate));
}
}
