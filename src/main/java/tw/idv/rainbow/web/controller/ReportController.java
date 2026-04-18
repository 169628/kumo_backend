package tw.idv.rainbow.web.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import tw.idv.rainbow.common.ApiResult;
import tw.idv.rainbow.web.dto.ReportDTO;
import tw.idv.rainbow.web.service.ReportService;

@Tag(name = "Dashbord Page")
@RestController
@RequestMapping("report")
public class ReportController {

    @Autowired
    private ReportService reportService;

    @Operation(summary = "Count all received devices in three brand in past 7 days")
    @GetMapping("received")
    public ApiResult getAll() {
        ReportDTO report = reportService.getAllReceived();
        return ApiResult.success(report);
    }
}
