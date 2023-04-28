package name.stromkabelsalat.mlfstats.entities;

import java.time.LocalDateTime;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Table(name = "mlf2_entries")
@Data
public class Mlf2Entry {
	@Id
	private Integer id;

	private LocalDateTime time;

	private Integer category;
}
