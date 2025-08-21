package com.dls.pcms.infrastructure.repositories;

import com.dls.pcms.domain.models.Image;
import com.dls.pcms.application.dto.ImageDTO;
import com.dls.pcms.application.services.ImageService;
import com.dls.pcms.domain.repository.ImageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Optional;

@Service
public class ImageServiceImpl implements ImageService {

    @Autowired
    private ImageRepository imageRepository;

    @Override
    public ImageDTO saveImage(MultipartFile file) throws IOException {
        Image image = new Image();
        image.setName(file.getOriginalFilename());
        image.setData(file.getBytes());
        Image savedImage = imageRepository.save(image);

        return mapToDTO(savedImage);
    }

    @Override
    public ImageDTO saveImageWithData2(MultipartFile file, MultipartFile file2) throws IOException {
        Image image = new Image();
        image.setName(file.getOriginalFilename());
        image.setData(file.getBytes());
        image.setData2(file2.getBytes()); // Setting data2
        Image savedImage = imageRepository.save(image);

        return mapToDTO(savedImage);
    }

    @Override
    public Optional<ImageDTO> getImage(Long id) {
        return imageRepository.findById(id).map(this::mapToDTO);
    }

    private ImageDTO mapToDTO(Image image) {
        ImageDTO dto = new ImageDTO();
        dto.setId(image.getId());
        dto.setName(image.getName());
        dto.setData(image.getData());
        dto.setData2(image.getData2()); // Mapping data2
        return dto;
    }
}
