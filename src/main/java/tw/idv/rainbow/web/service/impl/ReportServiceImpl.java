package tw.idv.rainbow.web.service.impl;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import tw.idv.rainbow.web.dto.ReportDTO;
import tw.idv.rainbow.web.service.ReportService;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Service
public class ReportServiceImpl implements ReportService {
    private final RedisTemplate<Object, Object> redisTemplate;

    public ReportServiceImpl(RedisTemplate<Object, Object> redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    @Override
    public ReportDTO getAllReceived() {
        List<List<List<String>>> map = new ArrayList<>();
        List<List<String>> projectorDevice = new ArrayList<>();
        List<List<String>> monitorDevice = new ArrayList<>();
        List<List<String>> robotVacuumDevice = new ArrayList<>();
        List<Integer> projectorCount = new ArrayList<>();
        List<Integer> monitorCount = new ArrayList<>();
        List<Integer> robotVacuumCount = new ArrayList<>();

        // for projector
        for(int i = 0; i <= 6; i++) {
            LocalDate date = LocalDate.now().minusDays(i);
            String key = "Projector" + ":" + "received" + ":" + date ;
            List<String> devices = new ArrayList<>();
            Set<Object> members = redisTemplate.opsForSet().members(key);
            if (members != null) {
                projectorCount.add(members.size());
                for (Object object : members) {
                    devices.add(object.toString());
                }
            }else{
                projectorCount.add(0);
            }
            projectorDevice.add(devices);
        }

        // for monitor
        for(int i = 0; i <= 6; i++) {
            LocalDate date = LocalDate.now().minusDays(i);
            String key = "Monitor" + ":" + "received" + ":" + date ;
            List<String> devices = new ArrayList<>();
            Set<Object> members = redisTemplate.opsForSet().members(key);
            if (members != null) {
                monitorCount.add(members.size());
                for (Object object : members) {
                    devices.add(object.toString());
                }
            }else{
                monitorCount.add(0);
            }
            monitorDevice.add(devices);
        }

        // for robotVacuum
        for(int i = 0; i <= 6; i++) {
            LocalDate date = LocalDate.now().minusDays(i);
            String key = "Robot Vacuum" + ":" + "received" + ":" + date ;
            List<String> devices = new ArrayList<>();
            Set<Object> members = redisTemplate.opsForSet().members(key);
            if (members != null) {
                robotVacuumCount.add(members.size());
                for (Object object : members) {
                    devices.add(object.toString());
                }
            }else{
                robotVacuumCount.add(0);
            }
            robotVacuumDevice.add(devices);
        }

        map.add(projectorDevice);
        map.add(monitorDevice);
        map.add(robotVacuumDevice);
        ReportDTO reportDTO = new ReportDTO();
        reportDTO.setDeviceMap(map);
        reportDTO.setProjector(projectorCount);
        reportDTO.setMonitor(monitorCount);
        reportDTO.setRobotVacuum(robotVacuumCount);

        return reportDTO;
    }
}
