package tw.idv.rainbow.web.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Devices {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long deviceId;
    private String sn;
    private String brand;
    private String model;
    @Column(insertable = false, updatable = false)
    private Timestamp firstConnect;
}
