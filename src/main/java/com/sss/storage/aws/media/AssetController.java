package com.sss.storage.aws.media;

import com.sss.storage.aws.media.dto.AssetRecordDto;
import com.sss.storage.aws.media.dto.UploadAssetRequestDto;
import com.sss.storage.aws.media.dto.UploadAssetResponseDto;
import com.sss.storage.aws.media.entity.AssetRecord;
import com.sss.storage.aws.media.repository.AssetRecordRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequiredArgsConstructor
@Slf4j
public class AssetController {

  private final AssetService assetService;
  private final AssetRecordRepository assetRecordRepository;

  @PostMapping("/upload")
  public ResponseEntity<UploadAssetResponseDto> uploadAsset(
          @RequestParam("file") MultipartFile multipartFile ){
    return ResponseEntity.ok(assetService.uploadAsset(multipartFile));
  }

  @DeleteMapping("/delete")
  public ResponseEntity<Boolean> deleteAsset(@RequestParam("path") String assetPath){
    return ResponseEntity.ok(assetService.deleteAsset(assetPath));
  }


  // only for viewing the database, can be deleted
  @GetMapping()
  public ResponseEntity<List<AssetRecordDto>> viewDB() {
    Iterable<AssetRecord> assets = assetRecordRepository.findAll();

    List<AssetRecordDto> assetRecords = new ArrayList<>();
    assets.forEach(
        asset -> {
          AssetRecordDto assetRecordDto =
              AssetRecordDto.builder()
                  .id(asset.getId())
                  .originalFileName(asset.getOriginalFileName())
                  .url(asset.getAssetUrl())
                  .size(asset.getAssetSize())
                  .time(asset.getCreatedAt())
                  .build();
          assetRecords.add(assetRecordDto);
        });

    return ResponseEntity.ok(assetRecords);
  }
}
