package pl.dudis.foodorders.infrastructure.database.entities;

import jakarta.persistence.*;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "local_type")
public class LocalTypeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "local_type_id")
    private Integer localTypeId;

    @Column(name = "type_name")
    private String typeName;

    @OneToOne(fetch = FetchType.LAZY,mappedBy = "type")
    private LocalEntity local;

}
