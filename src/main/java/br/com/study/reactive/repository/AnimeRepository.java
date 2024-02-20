package br.com.study.reactive.repository;

import br.com.study.reactive.domain.Anime;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface AnimeRepository extends MongoRepository<Anime, String> {
    Anime findByName(String name);
}
