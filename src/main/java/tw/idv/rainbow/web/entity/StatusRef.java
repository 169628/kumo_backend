package tw.idv.rainbow.web.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class StatusRef {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer statusId;
    private String status;
}
