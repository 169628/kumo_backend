package tw.idv.rainbow.web.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import tw.idv.rainbow.web.entity.ConnectLogs;

import java.util.List;

public interface ConnectLogsRepository extends JpaRepository<ConnectLogs,Long> {

    @Query("SELECT c FROM ConnectLogs c WHERE c.reportedAt = " + "(SELECT MAX(c2.reportedAt) FROM ConnectLogs c2 WHERE c2.deviceId = c.deviceId)" + "ORDER BY c.reportedAt DESC")
    List<ConnectLogs> findLatestPerDevice();

    List<ConnectLogs> findByDeviceIdOrderByReportedAtDesc(Long deviceId);
}
