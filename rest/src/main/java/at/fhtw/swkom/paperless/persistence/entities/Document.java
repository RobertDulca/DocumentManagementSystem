package at.fhtw.swkom.paperless.persistence.entities;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Document {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
    private String title;
    private LocalDateTime createdAt;

    public Document(String document, Object o) {
        this.title = document;
        this.createdAt = LocalDateTime.now();
    }
}

