package br.com.study.reactive.service;

import br.com.study.reactive.domain.Anime;
import br.com.study.reactive.repository.AnimeRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@AllArgsConstructor
@Service
public class AnimeService {

    private final AnimeRepository animeRepository;

    public List<Anime> findAll() {
        return animeRepository.findAll();
    }

    public Anime findByName(String name) {
        return animeRepository.findByName(name);
    }

    public String create(Anime anime) {
        return animeRepository.save(anime).getName();
    }

    public Anime update(String name, Anime anime) {
        Anime existingAnime = findByName(name);

        anime.withId(existingAnime.getId());

        return animeRepository.save(anime);
    }

    public boolean delete(String name) {
        Anime existingAnime = findByName(name);

        animeRepository.delete(existingAnime);

        return true;
    }
}
