package name.stromkabelsalat.mlfstats.model;

import lombok.Data;

@Data
public class Month {
	private Integer number;

	private Integer firstId;
	private Integer lastId;

	private Integer absoluteFrequency;

	private Integer cumulativeFrequency;
}
