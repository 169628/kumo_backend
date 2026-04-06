package tw.idv.rainbow.web.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import tw.idv.rainbow.web.entity.ConnectLogs;

public interface ConnectLogsRepository extends JpaRepository<ConnectLogs,Integer> {
}
