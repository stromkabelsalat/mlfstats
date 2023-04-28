package name.stromkabelsalat.mlfstats.components;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.stream.IntStream;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import name.stromkabelsalat.mlfstats.entities.Mlf2Category;
import name.stromkabelsalat.mlfstats.entities.Mlf2Entry;
import name.stromkabelsalat.mlfstats.model.Category;
import name.stromkabelsalat.mlfstats.model.Month;
import name.stromkabelsalat.mlfstats.model.Root;
import name.stromkabelsalat.mlfstats.model.Year;
import name.stromkabelsalat.mlfstats.repositories.Mlf2CategoryRepository;
import name.stromkabelsalat.mlfstats.repositories.Mlf2EntryRepository;

@Component
public class RootComponent {
	private final Integer[] categoryIds;

	private final Mlf2CategoryRepository mlf2CategoryRepository;
	private final Mlf2EntryRepository mlf2EntryRepository;

	public RootComponent(
			@Value("${name.stromkabelsalat.mlfstats.category-ids}") final Integer[] categoryIds,
			
			final Mlf2CategoryRepository mlf2CategoryRepository,
			final Mlf2EntryRepository mlf2EntryRepository) {
		this.categoryIds = categoryIds;

		this.mlf2CategoryRepository = mlf2CategoryRepository;
		this.mlf2EntryRepository = mlf2EntryRepository;
	}

	public Root root() {
		Integer cumulativeFrequency = 0;

		final Root root = new Root();

		final List<List<Mlf2Entry>> mlf2EntryListByCategoryList = Arrays
				.stream(this.categoryIds)
				.map(this.mlf2EntryRepository::findByCategoryOrderByTime)
				.filter(mlf2EntryListByCategory -> !mlf2EntryListByCategory.isEmpty())
				.toList();

		for (final List<Mlf2Entry> mlf2EntryListByCategory : mlf2EntryListByCategoryList) {
			final Integer categoryId = mlf2EntryListByCategory
					.get(0)
					.getCategory();
			final String categoryName = this.mlf2CategoryRepository
					.findById(categoryId)
					.map(Mlf2Category::getCategory).orElse(null);

			final Category category = new Category();
			category.setName(categoryName);

			final Integer startInclusiveYear = mlf2EntryListByCategory.get(0).getTime().getYear();
			final Integer startExclusiveYear = mlf2EntryListByCategory.get(mlf2EntryListByCategory.size() - 1).getTime()
					.getYear() + 1;

			final List<Integer> yearNumbers = IntStream.range(startInclusiveYear, startExclusiveYear).boxed().toList();
			for (final Integer yearNumber : yearNumbers) {
				final Year year = new Year();
				year.setNumber(yearNumber);

				final List<Integer> monthNumbers = IntStream.range(1, 13).boxed().toList();
				for (final Integer monthNumber : monthNumbers) {
					final Month month = new Month();
					month.setNumber(monthNumber);

					final LocalDateTime startTime = LocalDateTime.of(yearNumber, monthNumber, 1, 0, 0, 0);
					final LocalDateTime endTime = startTime.plusMonths(1).minusNanos(1);

					final List<Mlf2Entry> mlf2EntriesByCategoryInMonth = this.mlf2EntryRepository
							.findByCategoryAndTimeBetweenOrderById(categoryId, startTime, endTime);

					if (!mlf2EntriesByCategoryInMonth.isEmpty()) {
						final Mlf2Entry firstEntry = mlf2EntriesByCategoryInMonth.get(0);
						final Mlf2Entry lastEntry = mlf2EntriesByCategoryInMonth
								.get(mlf2EntriesByCategoryInMonth.size() - 1);

						final Integer firstId = firstEntry.getId();
						final Integer lastId = lastEntry.getId();

						final Integer absoluteFrequency = mlf2EntriesByCategoryInMonth.size();
						cumulativeFrequency += absoluteFrequency;

						month.setFirstId(firstId);
						month.setLastId(lastId);
						month.setAbsoluteFrequency(absoluteFrequency);
						month.setCumulativeFrequency(cumulativeFrequency);

						year.getMonths().add(month);
					}
				}

				if (!year.getMonths().isEmpty()) {
					category.getYears().add(year);
				}
			}

			root.getCategories().add(category);
		}

		return root;
	}
}
