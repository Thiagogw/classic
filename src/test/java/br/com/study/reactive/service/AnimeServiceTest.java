package br.com.study.reactive.service;

import br.com.study.reactive.domain.Anime;
import br.com.study.reactive.repository.AnimeRepository;
import br.com.study.reactive.util.AnimeCreator;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;

@ExtendWith(SpringExtension.class)
class AnimeServiceTest {

    @InjectMocks
    private AnimeService animeService;

    @Mock
    private AnimeRepository animeRepository;

    @BeforeEach
    void setup() {
        Mockito.when(animeRepository.findAll()).thenReturn(List.of(AnimeCreator.createAnimeToBeSaved()));
        Mockito.when(animeRepository.findByName(Mockito.any())).thenReturn(AnimeCreator.createAnimeToBeFound());
        Mockito.when(animeRepository.save(Mockito.any())).thenReturn(AnimeCreator.createAnimeToBeSaved());
        Mockito.doNothing().when(animeRepository).delete(Mockito.any());
    }

    @Test
    void findAllReturnListOfAnimeWhenSuccessful() {
        // Actions
        List<Anime> animes = animeService.findAll();

        // Assertions
        Assertions.assertThat(animes).isNotEmpty();
    }

    @Test
    void findByNameReturnAnimeWhenSuccessful() {
        // Actions
        Anime anime = animeService.findByName("Fullmetal Alchemist");

        // Assertions
        Assertions.assertThat(anime).isNotNull();
        Assertions.assertThat(anime.getId()).isNotBlank();
        Assertions.assertThat(anime.getName()).isEqualTo("Fullmetal Alchemist");
    }

    @Test
    void createReturnVoidWhenSuccessful() {
        // Actions
        String name = animeService.create(Anime.builder().name("Fullmetal Alchemist").build());

        // Assertions
        Assertions.assertThat(name).isEqualTo("Fullmetal Alchemist");
    }

    @Test
    void updateReturnAnimeWhenSuccessful() {
        // Assemble
        Mockito.when(animeRepository.save(Mockito.any())).thenReturn(AnimeCreator.createAnimeToBeUpdated());

        Anime anime = Anime.builder().name("Fullmetal Alchemist: Brotherhood").build();

        // Actions
        Anime updatedAnime = animeService.update("Fullmetal Alchemist", anime);

        // Assertions
        Assertions.assertThat(updatedAnime).isNotNull();
        Assertions.assertThat(updatedAnime.getId()).isNotBlank();
        Assertions.assertThat(updatedAnime.getName()).isEqualTo("Fullmetal Alchemist: Brotherhood");
    }

    @Test
    void deleteReturnVoidWhenSuccessful() {
        // Actions
        boolean deleted = animeService.delete("Fullmetal Alchemist");

        // Assertions
        Assertions.assertThat(deleted).isTrue();
    }
}
