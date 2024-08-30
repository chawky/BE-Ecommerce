package Stream.project.stream.configs;
import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
@PropertySource("classpath:application.properties")
@Configuration
public class AmazonConfig {

  @Value("${awsPublic}")
  private String awsPublic;
  @Value("${awsSecret}")
  private String awsSecret;
  @Bean
  public AmazonS3 s3() {

    AWSCredentials awsCredentials = new BasicAWSCredentials(
        awsPublic,
        awsSecret
    );
    return AmazonS3ClientBuilder
        .standard()
        .withRegion("eu-north-1")
        .withCredentials(new AWSStaticCredentialsProvider(awsCredentials))
        .build();
  }
}