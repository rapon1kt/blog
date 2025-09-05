package com.raponi.blog.application.validators;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.List;

import javax.imageio.ImageIO;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class ImageValidatorService {

  private static final List<String> ALLOWED_CONTENT_TYPES = Arrays.asList("image/jpeg", "image/png", "image/gif");
  private static final long MAX_FILE_SIZE = 5 * 1024 * 1024;
  private static final int MIN_WIDTH = 250;
  private static final int MIN_HEIGHT = 250;

  public ImageValidationResponse isValid(MultipartFile file) {
    String contentType = file.getContentType();
    if (contentType == null || !ALLOWED_CONTENT_TYPES.contains(contentType)) {
      return new ImageValidationResponse(false, "Archive type not allowed, allowed types: " + ALLOWED_CONTENT_TYPES);
    }
    if (file.getSize() > MAX_FILE_SIZE) {
      return new ImageValidationResponse(false,
          "Archive size is not suported, max size: " + (MAX_FILE_SIZE / 1024 / 1024) + " MB.");
    }
    try (InputStream inputStream = file.getInputStream()) {
      BufferedImage image = ImageIO.read(inputStream);
      if (image == null) {
        return new ImageValidationResponse(false, "The archive is not valid, please try again.");
      }
      if (image.getWidth() < MIN_WIDTH || image.getHeight() < MIN_HEIGHT) {
        return new ImageValidationResponse(false,
            "The archive dimensions must be at least " + MIN_WIDTH + "x" + MIN_HEIGHT + " pixels");
      }
    } catch (IOException e) {
      return new ImageValidationResponse(false, "Something went wrong reading the archive, please try again.");
    }
    return new ImageValidationResponse(true, "Image uploaded with success!");
  }

}
