package com.check.cells;

public class ScoreList {
	private double[] scores;
	private	double score;
	private	int index=0;
	private int size=0;
public ScoreList(int size)
{
	
	scores=new double[size]; 
	for(int i=0;i<scores.length;i++)
	{
		scores[i]=0;
	}
	}
public double add(double s)
{
	
	score-=scores[index];
	score+=s;
	scores[index]=s;
	index++;
	if(size<scores.length)
	{
		size++;
	}
	index=index%scores.length;
	return score;
			
	}
public double getScore()
{
	return score;
	}
}
