package name.stromkabelsalat.mlfstats.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Table(name = "mlf2_categories")
@Data
public class Mlf2Category {
	@Id
	private Integer id;

	@Column(nullable = false)
	private String category;
}
