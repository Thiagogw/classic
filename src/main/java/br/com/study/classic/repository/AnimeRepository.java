package br.com.study.classic.repository;

import br.com.study.classic.domain.Anime;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface AnimeRepository extends MongoRepository<Anime, String> {
    Anime findByName(String name);
}
