package com.bigcalculate.cell;

import java.util.ArrayList;
import java.util.List;

public class CalculateList {
	private List<CalculateNode> nodes = new ArrayList<>();
	private float bestScore;
	public List<CalculateNode> getNodes() {
		return nodes;
	}
	public void setNodes(List<CalculateNode> nodes) {
		this.nodes = nodes;
	}
	public float getBestScore() {
		return bestScore;
	}
	public void setBestScore(float bestScore) {
		this.bestScore = bestScore;
	}
}
