package com.comfig;

public class Config {
public static double win_per=0.12;
public static double lose_per=-0.10;
public static int max_keep=120;
//当 需求日涨幅大于 这个数字的时候挂涨停价格
public static double h_pr=1.09;
//当 需求日涨幅大于 h_pr 的时候 是否卖出
public static boolean h_pr_Flag=false; 
public static int return_size=10; 
}
