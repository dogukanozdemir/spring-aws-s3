package com.sss.storage.aws.asset.entity;

import com.sss.storage.aws.asset.enums.AssetModuleType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
public class AssetRecord {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  private String originalFileName;

  private String assetUrl;

  private Long assetSize;

  @CreationTimestamp private LocalDateTime createdAt;

  private AssetModuleType moduleType;

  private Long moduleId;

  private Long workspaceId;
}
