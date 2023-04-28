package name.stromkabelsalat.mlfstats.repositories;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import name.stromkabelsalat.mlfstats.entities.Mlf2Entry;

public interface Mlf2EntryRepository extends JpaRepository<Mlf2Entry, Integer> {
	List<Mlf2Entry> findByCategoryOrderByTime(Integer category);

	List<Mlf2Entry> findByCategoryAndTimeBetweenOrderById(Integer category, LocalDateTime startTime,
			LocalDateTime endTime);
}
