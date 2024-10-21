package Stream.project.stream.models;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.jdbc.repository.config.EnableJdbcAuditing;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@EnableJdbcAuditing
@Table(name = "address")
public class Address {
  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private Long id;
  @Column
  private String street;
  @Column
  private String strNumber;
  @Column
  private String postalCode;
  @Column
  private String city;
  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name="user_id", nullable=false)
  @JsonBackReference
  private User user;

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (!(o instanceof Address )) return false;
    return id != null && id.equals(((Address) o).getId());
  }

  @Override
  public int hashCode() {
    return getClass().hashCode();
  }
}
