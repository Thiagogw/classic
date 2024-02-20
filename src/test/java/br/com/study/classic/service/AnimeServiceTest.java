package br.com.study.classic.service;

import br.com.study.classic.domain.Anime;
import br.com.study.classic.dto.AnimeResponse;
import br.com.study.classic.repository.AnimeRepository;
import br.com.study.classic.repository.EpisodeRepository;
import br.com.study.classic.util.AnimeCreator;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.ArrayList;
import java.util.List;

@ExtendWith(SpringExtension.class)
class AnimeServiceTest {

    @InjectMocks
    private AnimeService animeService;

    @Mock
    private AnimeRepository animeRepository;

    @Mock
    private EpisodeRepository episodeRepository;

    @BeforeEach
    void setup() {
        Mockito.when(animeRepository.findAll()).thenReturn(List.of(AnimeCreator.createAnimeToBeSaved()));
        Mockito.when(animeRepository.findByName(Mockito.any())).thenReturn(AnimeCreator.createAnimeToBeFound());
        Mockito.when(animeRepository.save(Mockito.any())).thenReturn(AnimeCreator.createAnimeToBeSaved());
        Mockito.doNothing().when(animeRepository).delete(Mockito.any());

        Mockito.when(episodeRepository.findByName(Mockito.any())).thenReturn(new ArrayList<>());
    }

    @Test
    void findAllReturnListOfAnimeWhenSuccessful() {
        // Actions
        List<AnimeResponse> animesResponses = animeService.findAll();

        // Assertions
        Assertions.assertThat(animesResponses).isNotEmpty();
    }

    @Test
    void findByNameReturnAnimeWhenSuccessful() {
        // Actions
        AnimeResponse animeResponse = animeService.findByName("Fullmetal Alchemist");

        // Assertions
        Assertions.assertThat(animeResponse).isNotNull();
        Assertions.assertThat(animeResponse.getName()).isEqualTo("Fullmetal Alchemist");
    }

    @Test
    void createReturnVoidWhenSuccessful() {
        // Actions
        AnimeResponse animeResponse = animeService.create(Anime.builder().name("Fullmetal Alchemist").build());

        // Assertions
        Assertions.assertThat(animeResponse).isNotNull();
    }

    @Test
    void updateReturnAnimeWhenSuccessful() {
        // Assemble
        Mockito.when(animeRepository.save(Mockito.any())).thenReturn(AnimeCreator.createAnimeToBeUpdated());

        Anime anime = Anime.builder().name("Fullmetal Alchemist: Brotherhood").build();

        // Actions
        AnimeResponse updatedAnimeResponse = animeService.update("Fullmetal Alchemist", anime);

        // Assertions
        Assertions.assertThat(updatedAnimeResponse).isNotNull();
        Assertions.assertThat(updatedAnimeResponse.getName()).isEqualTo("Fullmetal Alchemist: Brotherhood");
    }

    @Test
    void deleteReturnVoidWhenSuccessful() {
        // Actions
        AnimeResponse deleted = animeService.delete("Fullmetal Alchemist");

        // Assertions
        Assertions.assertThat(deleted).isNotNull();
    }
}
