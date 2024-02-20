package br.com.study.classic.repository;

import br.com.study.classic.domain.Episode;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface EpisodeRepository extends MongoRepository<Episode, String> {
    List<Episode> findByName(String name);

    Episode findByTitle(String name);
}
