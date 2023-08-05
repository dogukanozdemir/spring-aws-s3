package com.sss.storage.aws.asset;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.*;

import com.sss.storage.aws.asset.dto.UploadAssetResponseDto;
import com.sss.storage.aws.asset.entity.AssetRecord;
import com.sss.storage.aws.asset.enums.AssetModuleType;
import com.sss.storage.aws.asset.repository.AssetRecordRepository;
import lombok.RequiredArgsConstructor;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class AssetService {

  @Value("${aws.bucket.name}")
  private String bucketName;

  @Value("${aws.bucket.base-url}")
  private String bucketBaseUrl;

  private final AmazonS3 s3Client;
  private final AssetRecordRepository assetRecordRepository;
  private static final Long DUMMY_WORKSPACE_ID = 1L;
  private static final AssetModuleType MODULE_TYPE = AssetModuleType.TASK;
  private static final Long MODULE_ID = 1L;

  public UploadAssetResponseDto uploadAsset(MultipartFile file){

    String uuid = UUID.randomUUID().toString();
    String filePath =
        String.join("/", DUMMY_WORKSPACE_ID.toString(), MODULE_TYPE.name(), uuid);

    ObjectMetadata metadata = new ObjectMetadata();
    metadata.setContentType(file.getContentType());
    metadata.setContentLength(file.getSize());

    try (InputStream in = file.getInputStream()) {
      PutObjectRequest request = new PutObjectRequest(bucketName, filePath, in, metadata);
      s3Client.putObject(request);
    } catch (Exception e){
      log.error(e.toString());
    }

    saveAssetRecord(file, filePath);

    return UploadAssetResponseDto.builder().assetUrl(bucketBaseUrl + filePath).build();
  }

  public boolean deleteAsset(String assetPath) {
    try {
      s3Client.deleteObject(bucketName, assetPath);
      return true;
    } catch (Exception e) {
      log.info(e.toString());
      return false;
    }
  }

  private void saveAssetRecord(MultipartFile file,String url) {
    AssetRecord assetRecord =
        AssetRecord.builder()
            .originalFileName(file.getOriginalFilename())
            .assetUrl(url)
            .assetSize(file.getSize())
            .moduleType(MODULE_TYPE)
            .moduleId(MODULE_ID)
            .workspaceId(DUMMY_WORKSPACE_ID)
            .build();
    assetRecordRepository.save(assetRecord);
  }
}
