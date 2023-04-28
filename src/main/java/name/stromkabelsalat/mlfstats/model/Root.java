package name.stromkabelsalat.mlfstats.model;

import java.util.ArrayList;
import java.util.List;

import lombok.Data;

@Data
public class Root {
	private Integer number;
	private final List<Category> categories = new ArrayList<>();

	public Integer getRowspan() {
		return this
				.getCategories()
				.stream()
				.map(Category::getRowspan)
				.reduce(0, Integer::sum);
	}

	public Integer getAbsoluteFrequency() {
		return this
				.getCategories()
				.stream()
				.map(Category::getAbsoluteFrequency)
				.reduce(0, Integer::sum);
	}
}
