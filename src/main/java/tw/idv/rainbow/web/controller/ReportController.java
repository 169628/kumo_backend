package tw.idv.rainbow.web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import tw.idv.rainbow.common.ApiResponse;
import tw.idv.rainbow.web.dto.ReportDTO;
import tw.idv.rainbow.web.service.ReportService;

@RestController
@RequestMapping("report")
public class ReportController {

    @Autowired
    private ReportService reportService;

    @GetMapping("received")
    public ApiResponse getAll() {
        ReportDTO report = reportService.getAllReceived();
        return ApiResponse.success(report);
    }
}
