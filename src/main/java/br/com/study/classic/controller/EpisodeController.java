package br.com.study.classic.controller;

import br.com.study.classic.domain.Episode;
import br.com.study.classic.dto.EpisodeResponse;
import br.com.study.classic.service.EpisodeService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("episodes")
public class EpisodeController {

    private final EpisodeService episodeService;

    @GetMapping
    public ResponseEntity<List<EpisodeResponse>> findAll() {
        return ResponseEntity.ok(episodeService.findAll());
    }

    @GetMapping("/{title}")
    public ResponseEntity<EpisodeResponse> findByTitle(@PathVariable String title) {
        return ResponseEntity.ok(episodeService.findByTitle(title));
    }

    @PostMapping
    public ResponseEntity<EpisodeResponse> create(@RequestBody Episode episode) {
        return ResponseEntity.ok(episodeService.create(episode));
    }

    @PutMapping("/{title}")
    public ResponseEntity<EpisodeResponse> update(@PathVariable String title, @RequestBody Episode episode) {
        return ResponseEntity.ok(episodeService.update(title, episode));
    }

    @DeleteMapping("/{title}")
    public ResponseEntity<EpisodeResponse> delete(@PathVariable String title) {
        return ResponseEntity.ok(episodeService.delete(title));
    }
}
