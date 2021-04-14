package org.acowzon.backend.entity.address;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import java.io.Serializable;
import java.util.UUID;

@Data
@NoArgsConstructor
@Entity
@Table(name = "t_Address")
public class AddressEntity implements Serializable {

    @Id
    @GenericGenerator(name = "idGenerator", strategy = "uuid2")
    @GeneratedValue(generator = "idGenerator")
    @Type(type="uuid-char")
    private UUID id;   // 地址id

    @NotEmpty
    private String country;  // 国家

    @NotEmpty
    private String province; // 省

    @NotEmpty
    private String city;     // 市

    @NotEmpty
    private String detail;  // 具体地址

    @Version
    private int version;

    public AddressEntity(UUID id) {
        this.id = id;
    }
}
