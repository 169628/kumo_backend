package tw.idv.rainbow.web.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.awt.*;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

@Tag(name = "Download the file")
@RestController
@RequestMapping("file")
public class FileController {
    @Value("${file.upload-dir:uploads}")
    private String filePath;

    @Operation(summary = "Download one file", description = "The file name include '.jpg', you can check the get campaign api and use the column 'file'")
    @GetMapping(value = "{fileName}",produces = MediaType.APPLICATION_OCTET_STREAM_VALUE)
    public byte[] download(@PathVariable String fileName) throws IOException {
        return Files.readAllBytes(Paths.get(filePath,fileName));
    }
}
