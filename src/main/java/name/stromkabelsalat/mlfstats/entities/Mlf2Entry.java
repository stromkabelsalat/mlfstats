package name.stromkabelsalat.mlfstats.entities;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
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

	@Column(nullable = false)
	private LocalDateTime time;

	@Column(nullable = false)
	private Integer category;
}
