package tw.idv.rainbow.web.entity;


import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import tw.idv.rainbow.common.JsonConverter;

import java.sql.Timestamp;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Campaigns {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer no;

    @Column(insertable = false, updatable = false)
    private Long campaignId;

    private String brand;

    private String model;

    private String sv;

    private String tv;

    private String file;

    private Integer fileSize;

    private Boolean isTestMode;

    @Convert(converter = JsonConverter.class)
    private List<String> testList;

    @Column(name = "download_by_id")
    private Short downloadById;

    @Column(insertable = false)
    private Boolean isEnabled;

    @Column(insertable = false, updatable = false)
    private Timestamp createAt;

    @Column(insertable = false)
    private Timestamp updateAt;

    @Column(insertable = false)
    private Boolean isDeleted;

    @ManyToOne
    @JoinColumn(name = "download_by_id", insertable = false, updatable = false)
    @JsonIgnore
    private DownloadByRef downloadBy;
}
