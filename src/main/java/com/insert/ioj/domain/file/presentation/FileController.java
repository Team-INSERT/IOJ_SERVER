package com.insert.ioj.domain.file.presentation;

import com.insert.ioj.domain.file.service.FileService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/files")
public class FileController {
    private final FileService fileService;

    @GetMapping("/presigned")
    public ResponseEntity<String> getPresignedUrl(
        @RequestParam String fileName
    ) {
        String url = fileService.generatePreSignedUrlToUpload(fileName);
        return ResponseEntity.ok(url);
    }

    @GetMapping("/download")
    public ResponseEntity<String> getPresignedUrlToDownload(
        @RequestParam String fileDomain
    ) {
        String url = fileService.generatePreSignedUrlToDownload(fileDomain);
        return ResponseEntity.ok(url);
    }
}
