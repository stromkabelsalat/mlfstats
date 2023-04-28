package name.stromkabelsalat.mlfstats.model;

import java.util.ArrayList;
import java.util.List;

import lombok.Data;

@Data
public class Category {
	private String name;
	private final List<Year> years = new ArrayList<>();

	public Integer getRowspan() {
		return this
				.getYears()
				.stream()
				.map(Year::getRowspan)
				.reduce(0, Integer::sum);
	}

	public Integer getAbsoluteFrequency() {
		return this
				.getYears()
				.stream()
				.map(Year::getAbsoluteFrequency)
				.reduce(0, Integer::sum);
	}
}
