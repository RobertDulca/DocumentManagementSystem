package at.fhtw.swkom.paperless.persistence.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DocumentEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
    private String title;
    private LocalDateTime createdAt;

    public DocumentEntity(String document, Object o) {
        this.title = document;
        this.createdAt = LocalDateTime.now();
    }
}

