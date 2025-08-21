package com.dls.pcms.application.services;

import com.dls.pcms.application.dto.ImageDTO;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Optional;

public interface ImageService {
    ImageDTO saveImage(MultipartFile file) throws IOException;
    Optional<ImageDTO> getImage(Long id);
    ImageDTO saveImageWithData2(MultipartFile file, MultipartFile file2) throws IOException;
}
