package tw.idv.rainbow.web.entity;


import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import tw.idv.rainbow.common.JsonConverter;

import java.sql.Timestamp;
import java.util.List;

@Schema(description = "Create campaign object")
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

    @Schema(description = "brand", example = "Projector")
    private String brand;

    @Schema(description = "model", example = "L1")
    private String model;

    @Schema(description = "sv", example = "v1.0.1")
    private String sv;

    @Schema(description = "tv", example = "v1.0.2")
    private String tv;

    @Column(length = 255)
    private String file;

    private Integer fileSize;

    @Schema(description = "Is this campaign test mode?", example = "true")
    private Boolean isTestMode;

    @Schema(description = "List of test devices", example = "['EXAM111','EXAM222']")
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

    @Column(length = 500)
    private String filePath;

    @ManyToOne
    @JoinColumn(name = "download_by_id", insertable = false, updatable = false)
    @JsonIgnore
    private DownloadByRef downloadBy;
}
