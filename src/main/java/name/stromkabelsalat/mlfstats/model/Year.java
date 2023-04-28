package name.stromkabelsalat.mlfstats.model;

import java.util.ArrayList;
import java.util.List;

import lombok.Data;

@Data
public class Year {
	private Integer number;
	private final List<Month> months = new ArrayList<>();

	public Integer getRowspan() {
		return this
				.getMonths()
				.size();
	}

	public Integer getAbsoluteFrequency() {
		return this
				.getMonths()
				.stream()
				.map(Month::getAbsoluteFrequency)
				.reduce(0, Integer::sum);
	}
}
