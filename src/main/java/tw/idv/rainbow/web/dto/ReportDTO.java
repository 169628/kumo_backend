package tw.idv.rainbow.web.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ReportDTO {
    List<List<List<String>>> deviceMap;
    List<Integer> projector;
    List<Integer> monitor;
    List<Integer> robotVacuum;
}
