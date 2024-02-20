package br.com.study.classic.controller;

import br.com.study.classic.domain.Anime;
import br.com.study.classic.dto.AnimeResponse;
import br.com.study.classic.service.AnimeService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;
import java.util.List;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("animes")
public class AnimeController {

    private final AnimeService animeService;

    @GetMapping
    public ResponseEntity<List<AnimeResponse>> findAll() {
        return ResponseEntity.ok(animeService.findAll());
    }

    @GetMapping("/{name}")
    public ResponseEntity<AnimeResponse> findByName(@PathVariable String name) {
        return ResponseEntity.ok(animeService.findByName(name));
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<Void> create(@RequestBody Anime anime) {
        String name = animeService.create(anime);

        return ResponseEntity.created(URI.create("animes/".concat(name))).build();
    }

    @PutMapping("/{name}")
    public ResponseEntity<AnimeResponse> update(@PathVariable String name, @RequestBody Anime anime) {
        return ResponseEntity.ok(animeService.update(name, anime));
    }

    @DeleteMapping("/{name}")
    public ResponseEntity<Void> delete(@PathVariable String name) {
        animeService.delete(name);

        return ResponseEntity.ok().build();
    }
}
