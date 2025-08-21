/*package com.dls.pcms.adapters.inbound.controllers;

import com.dls.pcms.application.dto.ImageDTO;
import com.dls.pcms.application.services.ImageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import java.util.Optional;
import java.io.IOException;
@RestController
@RequestMapping("/api/images")
public class ImageController {

    @Autowired
    private ImageService imageService;

    @PostMapping("/upload")
    public ResponseEntity<String> uploadImage(@RequestParam("file") MultipartFile file) {
        try {
            ImageDTO imageDTO = imageService.saveImage(file);
            return ResponseEntity.status(HttpStatus.CREATED).body("Image uploaded successfully: " + imageDTO.getId());
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error uploading image");
        }
    }

    @PostMapping("/uploadWithData2")
    public ResponseEntity<String> uploadImageWithData2(@RequestParam("file") MultipartFile file,
                                                       @RequestParam("file2") MultipartFile file2) {
        try {
            ImageDTO imageDTO = imageService.saveImageWithData2(file, file2);
            return ResponseEntity.status(HttpStatus.CREATED).body("Image with data2 uploaded successfully: " + imageDTO.getId());
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error uploading image with data2");
        }
    }

    @GetMapping("/double/{id1}/{id2}")
    public ResponseEntity<byte[][]> getTwoImages(@PathVariable Long id1, @PathVariable Long id2) {
        Optional<ImageDTO> imageDTO1 = imageService.getImage(id1);
        Optional<ImageDTO> imageDTO2 = imageService.getImage(id2);

        if (imageDTO1.isPresent() && imageDTO2.isPresent()) {
            byte[][] images = new byte[2][];
            images[0] = imageDTO1.get().getData();
            images[1] = imageDTO2.get().getData();

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(org.springframework.http.MediaType.IMAGE_JPEG);

            return new ResponseEntity<>(images, headers, HttpStatus.OK);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }
}

*/



package com.dls.pcms.adapters.inbound.controllers;

import com.dls.pcms.application.dto.ImageDTO;
import com.dls.pcms.application.services.ImageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Optional;

@RestController
@RequestMapping("/api/images")
public class ImageController {

    @Autowired
    private ImageService imageService;

    @PostMapping("/upload")
    public ResponseEntity<String> uploadImage(@RequestParam("file") MultipartFile file) {
        try {
            ImageDTO imageDTO = imageService.saveImage(file);
            return ResponseEntity.status(HttpStatus.CREATED).body("Image uploaded successfully: " + imageDTO.getId());
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error uploading image");
        }
    }

    @PostMapping("/uploadWithData2")
    public ResponseEntity<String> uploadImageWithData2(@RequestParam("file") MultipartFile file,
                                                       @RequestParam("file2") MultipartFile file2) {
        try {
            ImageDTO imageDTO = imageService.saveImageWithData2(file, file2);
            return ResponseEntity.status(HttpStatus.CREATED).body("Image with data2 uploaded successfully: " + imageDTO.getId());
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error uploading image with data2");
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<byte[]> getImage(@PathVariable Long id) {
        Optional<ImageDTO> imageDTO = imageService.getImage(id);

        return imageDTO
                .map(dto -> {
                    HttpHeaders headers = new HttpHeaders();
                    headers.setContentType(org.springframework.http.MediaType.IMAGE_JPEG);
                    headers.setContentLength(dto.getData().length);
                    return new ResponseEntity<>(dto.getData(), headers, HttpStatus.OK);
                })
                .orElse(ResponseEntity.status(HttpStatus.NOT_FOUND).body(null));
    }

    @GetMapping("/{id}/data2")
    public ResponseEntity<byte[]> getImageData2(@PathVariable Long id) {
        Optional<ImageDTO> imageDTO = imageService.getImage(id);

        return imageDTO
                .map(dto -> {
                    HttpHeaders headers = new HttpHeaders();
                    headers.setContentType(org.springframework.http.MediaType.IMAGE_JPEG);
                    headers.setContentLength(dto.getData2().length);
                    return new ResponseEntity<>(dto.getData2(), headers, HttpStatus.OK);
                })
                .orElse(ResponseEntity.status(HttpStatus.NOT_FOUND).body(null));
    }
}
