package com.sss.storage.aws.media.dto;

import lombok.Builder;

import java.time.LocalDateTime;
@Builder
public record AssetRecordDto(
    Long id, String originalFileName, String url, Long size, LocalDateTime time) {}
