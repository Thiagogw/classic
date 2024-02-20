package br.com.study.reactive.controller;

import br.com.study.reactive.domain.Anime;
import br.com.study.reactive.service.AnimeService;
import br.com.study.reactive.util.AnimeCreator;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;

@ExtendWith(SpringExtension.class)
class AnimeControllerTest {

    @InjectMocks
    private AnimeController animeController;

    @Mock
    private AnimeService animeService;

    @BeforeEach
    void setup() {
        Mockito.when(animeService.findAll()).thenReturn(List.of(AnimeCreator.createAnimeToBeSaved()));
        Mockito.when(animeService.findByName(Mockito.any())).thenReturn(AnimeCreator.createAnimeToBeFound());
        Mockito.when(animeService.create(Mockito.any())).thenReturn("");
        Mockito.when(animeService.update(Mockito.any(), Mockito.any())).thenReturn(AnimeCreator.createAnimeToBeUpdated());
        Mockito.when(animeService.delete(Mockito.any())).thenReturn(true);
    }

    @Test
    void findAllReturnListOfAnimeWhenSuccessful() {
        // Actions
        ResponseEntity<List<Anime>> responseEntity = animeController.findAll();

        // Assertions
        List<Anime> animes  = responseEntity.getBody();

        Assertions.assertThat(animes).isNotEmpty();
    }

    @Test
    void findByNameReturnAnimeWhenSuccessful() {
        // Actions
        ResponseEntity<Anime> responseEntity = animeController.findByName("Fullmetal Alchemist");

        // Assertions
        Anime anime = responseEntity.getBody();

        Assertions.assertThat(anime).isNotNull();
        Assertions.assertThat(anime.getId()).isNotBlank();
        Assertions.assertThat(anime.getName()).isEqualTo("Fullmetal Alchemist");
    }

    @Test
    void createReturnVoidWhenSuccessful() {
        // Actions
        ResponseEntity<Void> responseEntity = animeController.create(Anime.builder().name("One Piece").build());

        // Assertions
        Assertions.assertThat(responseEntity.getStatusCode().is2xxSuccessful()).isTrue();
    }

    @Test
    void updateReturnAnimeWhenSuccessful() {
        // Assemble
        Anime anime = Anime.builder().name("Fullmetal Alchemist: Brotherhood").build();

        // Actions
        ResponseEntity<Anime> responseEntity = animeController.update("Fullmetal Alchemist", anime);

        // Assertions
        Anime updatedAnime = responseEntity.getBody();

        Assertions.assertThat(updatedAnime).isNotNull();
        Assertions.assertThat(updatedAnime.getId()).isNotBlank();
        Assertions.assertThat(updatedAnime.getName()).isEqualTo("Fullmetal Alchemist: Brotherhood");
    }

    @Test
    void deleteReturnVoidWhenSuccessful() {
        // Actions
        ResponseEntity<Void> responseEntity = animeController.delete("Fullmetal Alchemist");

        // Assertions
        Assertions.assertThat(responseEntity.getStatusCode().is2xxSuccessful()).isTrue();
    }
}
