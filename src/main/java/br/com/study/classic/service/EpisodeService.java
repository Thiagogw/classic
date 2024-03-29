package br.com.study.classic.service;

import br.com.study.classic.domain.Episode;
import br.com.study.classic.dto.EpisodeResponse;
import br.com.study.classic.repository.EpisodeRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@AllArgsConstructor
@Service
public class EpisodeService {

    private final EpisodeRepository episodeRepository;

    public List<EpisodeResponse> findAll() {
        List<Episode> episodes = episodeRepository.findAll();

        return episodes.stream().map(episode -> EpisodeResponse.builder()
                .id(episode.getId())
                .title(episode.getTitle())
                .name(episode.getName())
                .build()
        ).toList();
    }

    public List<EpisodeResponse> findByName(String name) {

        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        return episodeRepository.findByName(name).stream()
                .map(episode -> EpisodeResponse.builder()
                        .id(episode.getId())
                        .title(episode.getTitle())
                        .name(episode.getName())
                        .build()
                ).toList();
    }

    public EpisodeResponse findByTitle(String title) {
        Episode episode = episodeRepository.findByTitle(title);

        return EpisodeResponse.builder()
                .id(episode.getId())
                .title(episode.getTitle())
                .name(episode.getName())
                .build();
    }

    public EpisodeResponse create(Episode episode) {
        Episode savedEpisode = episodeRepository.save(episode);

        return EpisodeResponse.builder()
                .id(episode.getId())
                .title(savedEpisode.getTitle())
                .name(savedEpisode.getName())
                .build();
    }

    public EpisodeResponse update(String title, Episode episode) {
        Episode existingEpisode = episodeRepository.findByTitle(title);

        episode.withId(existingEpisode.getId());

        Episode episodeSaved = episodeRepository.save(episode);

        return EpisodeResponse.builder()
                .id(episode.getId())
                .title(episodeSaved.getTitle())
                .name(episodeSaved.getName())
                .build();
    }

    public EpisodeResponse delete(String title) {
        Episode existingEpisode = episodeRepository.findByTitle(title);

        episodeRepository.delete(existingEpisode);

        return EpisodeResponse.builder()
                .id(existingEpisode.getId())
                .title(existingEpisode.getTitle())
                .name(existingEpisode.getName())
                .build();
    }
}
