package com.service.hd.api;

import com.service.hd.service.DownloadService;
import com.service.hd.service.VideoItem;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v1")
public class LoaderResource {

    @Autowired
    private DownloadService downloadService;

    @GetMapping(path = "load", produces = "video/mp4")
    public ResponseEntity<InputStreamResource> get() {
        VideoItem videoItem = downloadService.execute();

        InputStreamResource resource = new InputStreamResource(videoItem.getVideo());

        MediaType mediaType = MediaType.parseMediaType("video/mp4");
        long size = videoItem.getSize();

        return ResponseEntity
                .ok()
                .contentLength(size)
                .contentType(mediaType)
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + videoItem.getExt() + "\"")
                .body(resource);
    }
}
