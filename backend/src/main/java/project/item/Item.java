package project.item;

import java.util.UUID;

import jakarta.persistence.*;
import lombok.*;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Table(name = "items")
public class Item {
    @Id
    @GeneratedValue
    @Getter
    private UUID id;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    @Getter
    @Setter
    private ItemStatus status = ItemStatus.PENDING;
}
