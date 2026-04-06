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
public class ConnectLogs {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idForJpa;
    private String sessionId;
    @Column(name = "device_id")
    private Long deviceId;
    @Column(name = "status_id")
    private Integer statusId;
    private String sv;
    @Column(insertable = false, updatable = false)
    private Timestamp reportedAt;

    @ManyToOne
    @JoinColumn( name = "device_id",insertable = false, updatable = false)
    private Devices device;

    @ManyToOne
    @JoinColumn( name = "status_id",insertable = false, updatable = false)
    private StatusRef status;
}
