package br.com.study.classic.controller;

import br.com.study.classic.domain.Anime;
import br.com.study.classic.dto.AnimeResponse;
import br.com.study.classic.service.AnimeService;
import br.com.study.classic.util.AnimeCreator;
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
        Anime animeToBeSaved = AnimeCreator.createAnimeToBeSaved();
        Anime animeToBeFound = AnimeCreator.createAnimeToBeFound();
        Anime animeToBeUpdated = AnimeCreator.createAnimeToBeUpdated();
        Anime animeToBeDeleted = AnimeCreator.createAnimeToBeDeleted();

        Mockito.when(animeService.findAll()).thenReturn(List.of(AnimeResponse.builder().name(animeToBeSaved.getName()).build()));
        Mockito.when(animeService.findByName(Mockito.any())).thenReturn(AnimeResponse.builder().name(animeToBeFound.getName()).build());
        Mockito.when(animeService.create(Mockito.any())).thenReturn(AnimeResponse.builder().name(animeToBeSaved.getName()).build());
        Mockito.when(animeService.update(Mockito.any(), Mockito.any())).thenReturn(AnimeResponse.builder().name(animeToBeUpdated.getName()).build());
        Mockito.when(animeService.delete(Mockito.any())).thenReturn(AnimeResponse.builder().name(animeToBeDeleted.getName()).build());
    }

    @Test
    void findAllReturnListOfAnimeWhenSuccessful() {
        // Actions
        ResponseEntity<List<AnimeResponse>> responseEntity = animeController.findAll();

        // Assertions
        List<AnimeResponse> animesResponses = responseEntity.getBody();

        Assertions.assertThat(animesResponses).isNotEmpty();
    }

    @Test
    void findByNameReturnAnimeWhenSuccessful() {
        // Actions
        ResponseEntity<AnimeResponse> responseEntity = animeController.findByName("Fullmetal Alchemist");

        // Assertions
        AnimeResponse animeResponse = responseEntity.getBody();

        Assertions.assertThat(animeResponse).isNotNull();
        Assertions.assertThat(animeResponse.getName()).isEqualTo("Fullmetal Alchemist");
    }

    @Test
    void createReturnVoidWhenSuccessful() {
        // Actions
        ResponseEntity<AnimeResponse> responseEntity = animeController.create(Anime.builder().name("One Piece").build());

        // Assertions
        Assertions.assertThat(responseEntity.getStatusCode().is2xxSuccessful()).isTrue();
    }

    @Test
    void updateReturnAnimeWhenSuccessful() {
        // Assemble
        Anime anime = Anime.builder().name("Fullmetal Alchemist: Brotherhood").build();

        // Actions
        ResponseEntity<AnimeResponse> responseEntity = animeController.update("Fullmetal Alchemist", anime);

        // Assertions
        AnimeResponse updatedAnimeResponse = responseEntity.getBody();

        Assertions.assertThat(updatedAnimeResponse).isNotNull();
        Assertions.assertThat(updatedAnimeResponse.getName()).isEqualTo("Fullmetal Alchemist: Brotherhood");
    }

    @Test
    void deleteReturnVoidWhenSuccessful() {
        // Actions
        ResponseEntity<AnimeResponse> responseEntity = animeController.delete("Fullmetal Alchemist");

        // Assertions
        Assertions.assertThat(responseEntity.getStatusCode().is2xxSuccessful()).isTrue();
    }
}
