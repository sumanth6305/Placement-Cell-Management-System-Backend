package com.dls.pcms.application.dto;

import lombok.*;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ImageDTO {
    private Long id;
    private String name;
    private byte[] data;
    private byte[] data2;
}
