package com.sss.storage.aws.media.dto;

import com.sss.storage.aws.media.enums.AssetModuleType;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;


@Builder
@Setter
@Getter
public class UploadAssetRequestDto{

    private MultipartFile file;
    private AssetModuleType moduleType;
    private Long moduleId;
}
