package com.rick.oss.model;

import lombok.AllArgsConstructor;
import lombok.Value;

import java.io.InputStream;

/**
 * @author Rick
 * @createdAt 2021-08-19 15:35:00
 */
@AllArgsConstructor
@Value
public class ImageObject {

    private InputStream is;

    private String ext;
}
