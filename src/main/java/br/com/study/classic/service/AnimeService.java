package br.com.study.classic.service;

import br.com.study.classic.domain.Anime;
import br.com.study.classic.domain.Episode;
import br.com.study.classic.dto.AnimeResponse;
import br.com.study.classic.dto.EpisodeResponse;
import br.com.study.classic.repository.AnimeRepository;
import br.com.study.classic.repository.EpisodeRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@AllArgsConstructor
@Service
public class AnimeService {

    private final AnimeRepository animeRepository;
    private final EpisodeRepository episodeRepository;

    public List<AnimeResponse> findAll() {
        List<Anime> animes = animeRepository.findAll();

        return animes.stream()
                .map(anime -> {
                            List<Episode> episodes = episodeRepository.findByName(anime.getName());

                            return AnimeResponse.builder()
                                    .name(anime.getName())
                                    .episodes(episodes.stream()
                                            .map(episode -> EpisodeResponse.builder()
                                                    .name(episode.getName())
                                                    .title(episode.getTitle())
                                                    .build()).toList()
                                    ).build();
                        }
                ).toList();
    }

    public AnimeResponse findByName(String name) {
        Anime anime = animeRepository.findByName(name);

        List<Episode> episodes = episodeRepository.findByName(anime.getName());

        return AnimeResponse.builder()
                .name(anime.getName())
                .episodes(episodes.stream()
                        .map(episode -> EpisodeResponse.builder()
                                .title(episode.getTitle())
                                .name(episode.getName())
                                .build())
                        .toList()
                ).build();
    }

    public AnimeResponse create(Anime anime) {
        Anime savedAnime = animeRepository.save(anime);

        List<Episode> episodes = episodeRepository.findByName(savedAnime.getName());

        return AnimeResponse.builder()
                .name(savedAnime.getName())
                .episodes(episodes.stream().map(episode -> EpisodeResponse.builder()
                                .name(episode.getName())
                                .title(episode.getTitle())
                                .build())
                        .toList())
                .build();
    }

    public AnimeResponse update(String name, Anime anime) {
        Anime existingAnime = animeRepository.findByName(name);

        anime.withId(existingAnime.getId());

        Anime savedAnime = animeRepository.save(anime);

        List<Episode> episodes = episodeRepository.findByName(existingAnime.getName());

        List<EpisodeResponse> episodeResponses = episodes.stream()
                .map(episode -> {
                    episode.setName(savedAnime.getName());

                    Episode savedEpisode = episodeRepository.save(episode);

                    return EpisodeResponse.builder()
                            .title(savedEpisode.getTitle())
                            .name(savedEpisode.getName())
                            .build();
                })
                .toList();

        return AnimeResponse.builder()
                .name(savedAnime.getName())
                .episodes(episodeResponses).build();
    }

    public AnimeResponse delete(String name) {
        Anime existingAnime = animeRepository.findByName(name);

        animeRepository.delete(existingAnime);

        List<Episode> episodes = episodeRepository.findByName(existingAnime.getName());

        List<EpisodeResponse> episodesResponses = episodes.stream()
                .map(episode -> {
                    episodeRepository.delete(episode);

                    return EpisodeResponse.builder()
                            .name(episode.getName())
                            .title(episode.getTitle())
                            .build();
                })
                .toList();

        return AnimeResponse.builder()
                .name(existingAnime.getName())
                .episodes(episodesResponses)
                .build();
    }
}
