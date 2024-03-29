package br.com.study.classic.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.With;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Builder
@With
@NoArgsConstructor
@AllArgsConstructor
@Document("episode")
public class Episode {

    @Id
    private String id;

    private String name;

    private String title;
}
