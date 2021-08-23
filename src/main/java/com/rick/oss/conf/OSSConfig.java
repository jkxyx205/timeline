package com.rick.oss.conf;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @author Rick
 * @createdAt 2021-08-19 14:38:00
 */
@Component
@Data
@ConfigurationProperties(prefix = "oss")
public class OSSConfig {

    private String endpoint;

    private String accessKeyId;

    private String accessKeySecret;

    private String bucketName;
}
