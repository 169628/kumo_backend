package tw.idv.rainbow.web.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class DownloadByRef {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Short downloadById;

    private String content;
}
