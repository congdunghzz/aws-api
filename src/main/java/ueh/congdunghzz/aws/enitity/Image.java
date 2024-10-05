package ueh.congdunghzz.aws.enitity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import ueh.congdunghzz.aws.common.enums.ActiveStatus;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Document
public class Image {
    @Id
    private String id;
    private String name;
    private String key;
    private String url;
    private String ownedBy;
    private Long createDate;
    private String contentType;
}
