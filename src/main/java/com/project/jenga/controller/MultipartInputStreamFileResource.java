package com.project.jenga.controller;

import org.springframework.core.io.InputStreamResource;
import java.io.IOException;
import java.io.InputStream;

public class MultipartInputStreamFileResource extends InputStreamResource {
    // Spirng -> FastAPI로 파일을 보내는 것을 처리함
    private final String filename;

    public MultipartInputStreamFileResource(InputStream inputStream, String filename) {
        super(inputStream);
        this.filename = filename;
    }

    @Override
    public String getFilename() {
        return this.filename;
    }

    @Override
    public long contentLength() throws IOException {
        return -1; // unknown
    }
}
