package br.com.study.classic.service;

import br.com.study.classic.domain.Anime;
import br.com.study.classic.domain.Episode;
import br.com.study.classic.dto.AnimeResponse;
import br.com.study.classic.dto.EpisodeResponse;
import br.com.study.classic.repository.AnimeRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@AllArgsConstructor
@Service
public class AnimeService {

    private final AnimeRepository animeRepository;
    private final EpisodeService episodeService;

    public List<AnimeResponse> findAll() {
        List<Anime> animes = animeRepository.findAll();

        return animes.stream()
                .parallel()
                .map(anime -> {
                            List<EpisodeResponse> episodesResponses = episodeService.findByName(anime.getName());

                            return AnimeResponse.builder()
                                    .id(anime.getId())
                                    .name(anime.getName())
                                    .episodes(episodesResponses)
                                    .build();
                        }
                ).toList();
    }

    public AnimeResponse findByName(String name) {
        Anime anime = animeRepository.findByName(name);

        List<EpisodeResponse> episodesResponses = episodeService.findByName(anime.getName());

        return AnimeResponse.builder()
                .id(anime.getId())
                .name(anime.getName())
                .episodes(episodesResponses).build();
    }

    public AnimeResponse create(Anime anime) {
        Anime savedAnime = animeRepository.save(anime);

        List<EpisodeResponse> episodesResponses = episodeService.findByName(savedAnime.getName());

        return AnimeResponse.builder()
                .id(anime.getId())
                .name(savedAnime.getName())
                .episodes(episodesResponses)
                .build();
    }

    public AnimeResponse update(String name, Anime anime) {
        Anime existingAnime = animeRepository.findByName(name);

        anime.withId(existingAnime.getId());

        Anime savedAnime = animeRepository.save(anime);

        List<EpisodeResponse> episodeResponses = episodeService.findByName(existingAnime.getName()).stream()
                .map(episodeResponse -> {
                    Episode episode = Episode.builder()
                            .id(episodeResponse.getId())
                            .name(savedAnime.getName())
                            .title(episodeResponse.getTitle())
                            .build();

                    return episodeService.update(episodeResponse.getTitle(), episode);
                }).toList();

        return AnimeResponse.builder()
                .id(anime.getId())
                .name(savedAnime.getName())
                .episodes(episodeResponses).build();
    }

    public AnimeResponse delete(String name) {
        Anime existingAnime = animeRepository.findByName(name);

        animeRepository.delete(existingAnime);

        List<EpisodeResponse> episodesResponses = episodeService.findByName(existingAnime.getName()).stream()
                .map(episode -> {
                    episodeService.delete(episode.getTitle());

                    return EpisodeResponse.builder()
                            .id(episode.getId())
                            .name(episode.getName())
                            .title(episode.getTitle())
                            .build();
                }).toList();

        return AnimeResponse.builder()
                .id(existingAnime.getId())
                .name(existingAnime.getName())
                .episodes(episodesResponses)
                .build();
    }
}
