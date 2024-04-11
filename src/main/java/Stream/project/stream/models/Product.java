package Stream.project.stream.models;

import lombok.*;
import org.springframework.data.jdbc.repository.config.EnableJdbcAuditing;

import javax.persistence.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@EnableJdbcAuditing
@Table(name = "Product")
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @Column
    private String productName;
    @Column
    private String productCategory;
    @Column
    private String productDesc;
    @Column
    private String  productImageName;
    @Column
    private double productPrice;
    @Column
    private String category;
    //    @OneToMany(cascade = CascadeType.ALL ,orphanRemoval = true,fetch = FetchType.LAZY,mappedBy = "product")
    //    private List<ImageData> productImages;
    @Lob
    @Column(name = "imagedata", length = 1000)
    private byte[] imageData;

}
