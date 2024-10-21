package Stream.project.stream.services;

import Stream.project.stream.configs.AmazonConfig;
import com.amazonaws.AmazonServiceException;
import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.iterable.S3Objects;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.S3Object;
import com.amazonaws.services.s3.model.S3ObjectInputStream;
import com.amazonaws.services.s3.model.S3ObjectSummary;
import com.amazonaws.util.IOUtils;
import java.io.IOException;
import java.io.InputStream;
import java.util.Map;
import java.util.Optional;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Service;

@AllArgsConstructor
@Service
@PropertySource("classpath:application.properties")
public class FileStore {
  private final AmazonS3 amazonS3;

  public void upload(String path,
      String fileName,
      Optional<Map<String, String>> optionalMetaData,
      InputStream inputStream) {
    ObjectMetadata objectMetadata = new ObjectMetadata();
    optionalMetaData.ifPresent(map -> {
      if (!map.isEmpty()) {
        map.forEach(objectMetadata::addUserMetadata);
      }
    });
    try {
      amazonS3.putObject(path, fileName, inputStream, objectMetadata);
    } catch (AmazonServiceException e) {
      throw new IllegalStateException("Failed to upload the file", e);
    }
  }
  public byte[] download(String path, String key) {
    try {
      S3Object object = amazonS3.getObject(path, key);
      S3ObjectInputStream objectContent = object.getObjectContent();
      return IOUtils.toByteArray(objectContent);
    } catch (AmazonServiceException | IOException e) {
      throw new IllegalStateException("Failed to download the file", e);
    }
  }



  public byte[] downloadAllProducts() {

    AWSCredentials awsCredentials = new BasicAWSCredentials(
        "AKIAU6GDYOWM3LADKXUU",
        "LHVSp96gWadD4oyDLBC65XPwKYSJBVnNQDV2kNn2"
    );
    AmazonS3 sbucket = AmazonS3ClientBuilder
        .standard()
        .withRegion("eu-north-1")
        .withCredentials(new AWSStaticCredentialsProvider(awsCredentials))
        .build();
    S3Objects.inBucket(sbucket, "product-images-list")
        .forEach((S3ObjectSummary objectSummary) -> {
      // TODO: Consume `objectSummary` the way you need
      System.out.println(objectSummary.getKey());
    });
    return null;
  }

}
