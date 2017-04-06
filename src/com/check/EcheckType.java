package com.check;

public enum EcheckType {
	LYCJSS_FLAG(null);
	private CheckJobsBase job;

	EcheckType(CheckJobsBase job) {
		this.job = job;
	}

	public CheckJobsBase getJob() {
		return job;
	}

}
