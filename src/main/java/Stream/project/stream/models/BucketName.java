package Stream.project.stream.models;

import lombok.AllArgsConstructor;
import lombok.Getter;
@AllArgsConstructor
@Getter
public enum BucketName {
  PRODUCT_IMAGE("product-images-list");
  private final String bucketName;
}