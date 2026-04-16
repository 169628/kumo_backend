package tw.idv.rainbow.web.service.impl;

import com.aventrix.jnanoid.jnanoid.NanoIdUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tw.idv.rainbow.web.dto.CampaignDTO;
import tw.idv.rainbow.web.dto.ConnectLogDTO;
import tw.idv.rainbow.web.dto.ConnectResponseDTO;
import tw.idv.rainbow.web.dto.DeviceDTO;
import tw.idv.rainbow.web.entity.Campaigns;
import tw.idv.rainbow.web.entity.ConnectLogs;
import tw.idv.rainbow.web.entity.Devices;
import tw.idv.rainbow.web.entity.StatusRef;
import tw.idv.rainbow.web.repository.CampaignsRepository;
import tw.idv.rainbow.web.repository.ConnectLogsRepository;
import tw.idv.rainbow.web.repository.DevicesRepository;
import tw.idv.rainbow.web.repository.StatusRefRepository;
import tw.idv.rainbow.web.service.DeviceService;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

@Service
@Transactional
public class DeviceServiceImpl implements DeviceService {

    @Autowired
    private DevicesRepository deviceRepository;
    @Autowired
    private ConnectLogsRepository connectLogsRepository;
    @Autowired
    private CampaignsRepository campaignsRepository;
    @Autowired
    private StatusRefRepository statusRefRepository;
    @Autowired
    private RedisTemplate<Object, Object> redisTemplate;


    @Override
    public ConnectResponseDTO connect(DeviceDTO deviceDTO) {
        ConnectResponseDTO response = new ConnectResponseDTO();
        ConnectLogs connectLog = new ConnectLogs();

        String brand = deviceDTO.getBrand();
        String model = deviceDTO.getModel();
        String sv = deviceDTO.getSv();
        String sn = deviceDTO.getSn();
        String status = deviceDTO.getStatus();

        //check column
        if (brand == null) {
            response.setMessage("Brand is required");
            return response;
        } else if (brand.length() < 2 || brand.length() > 20) {
            response.setMessage("Brand must be at least 2 characters and maximum 20 characters");
            return response;
        }

        if (model == null) {
            response.setMessage("Model is required");
            return response;
        } else if (model.length() < 2 || model.length() > 10) {
            response.setMessage("Model must be at least 2 characters and maximum 10 characters");
            return response;
        }

        if (sv == null) {
            response.setMessage("Source version is required");
            return response;
        } else if (sv.length() < 2 || sv.length() > 10) {
            response.setMessage("Source version must be at least 2 characters and maximum 10 characters");
            return response;
        }

        if (sn == null) {
            response.setMessage("Serial number is required");
            return response;
        } else if (sn.length() < 2 || sn.length() > 50) {
            response.setMessage("Serial number must be at least 2 characters and maximum 50 characters");
            return response;
        }

        // check device
        Devices device = deviceRepository.findBySn(sn);
        if (device == null) {
            device = new Devices();
            device.setBrand(brand);
            device.setModel(model);
            device.setSn(sn);
            device = deviceRepository.save(device);
        } else {
            //比較 brand/model
            if (!Objects.equals(device.getBrand(), brand) || !Objects.equals(device.getModel(), model)) {
                response.setMessage("Wrong device");
                return response;
            }
        }

        // check campaign
        if (deviceDTO.getSession() == null) {
            String session = NanoIdUtils.randomNanoId();
            connectLog.setSessionId(session);
            connectLog.setDeviceId(device.getDeviceId());
            connectLog.setStatusId(1);
            connectLog.setSv(sv);
            connectLogsRepository.save(connectLog);
            // save redis
            String key = brand + ":" + "received" + ":" + LocalDate.now();
            redisTemplate.opsForSet().add(key,sn);
            redisTemplate.expire(key, 30, TimeUnit.DAYS);
            List<Campaigns> campaigns = campaignsRepository.findByBrandAndModelAndSvAndIsEnabledIsTrueAndIsDeletedIsFalseOrderByUpdateAtDesc(brand, model, sv);
            if (campaigns == null || campaigns.isEmpty()) {
                response.setMessage("No match campaign");
                return response;
            } else {
                Campaigns campaign = campaigns.get(0);
                if (campaign.getIsTestMode()) {
                    List<String> testList = campaign.getTestList();
                    if (testList == null || testList.isEmpty()) {
                        response.setMessage("No test list");
                        return response;
                    } else if (!testList.contains(sn)){
                        response.setMessage("Permission denied");
                        return response;
                    }
                }
                CampaignDTO campaignDTO = CampaignDTO.toDTO(campaign);
                response.setSession(session);
                response.setStatus("received");
                response.setCampaignDTO(campaignDTO);
                return response;
            }
        } else {

            //check status
            if (status == null) {
                response.setMessage("Status is required");
                return response;
            }
            StatusRef statusRef = statusRefRepository.findByStatus(status);
            if(statusRef == null){
                response.setMessage("Wrong Status");
                return response;
            }

            //save status
            connectLog.setSessionId(deviceDTO.getSession());
            connectLog.setDeviceId(device.getDeviceId());
            connectLog.setStatusId(statusRef.getStatusId());
            connectLog.setSv(sv);
            connectLogsRepository.save(connectLog);

            if(!Objects.equals(status,"succeeded") && !Objects.equals(status,"failed")){
                response.setSession(deviceDTO.getSession());
            }
            response.setStatus(status);
            return response;
        }
    }

    @Override
    public List<ConnectLogDTO> getDevice() {
        List<ConnectLogs> connectLogsList = connectLogsRepository.findLatestPerDevice();
        List<ConnectLogDTO> dtoList = new ArrayList<>();
        for(ConnectLogs connectLog : connectLogsList){
            ConnectLogDTO dto = ConnectLogDTO.toDTO(connectLog);
            dtoList.add(dto);
        }
        return dtoList;
    }

    @Override
    public List<ConnectLogDTO> getOneDeviceLog(Long deviceId) {
        List<ConnectLogs> connectLogsList = connectLogsRepository.findByDeviceIdOrderByReportedAtDesc(deviceId);
        List<ConnectLogDTO> dtoList = new ArrayList<>();
        for(ConnectLogs connectLog : connectLogsList){
            ConnectLogDTO dto = ConnectLogDTO.toDTO(connectLog);
            dtoList.add(dto);
        }
        return dtoList;
    }
}
