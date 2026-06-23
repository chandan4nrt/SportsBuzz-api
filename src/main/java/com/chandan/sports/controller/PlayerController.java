package com.chandan.sports.controller;

import com.chandan.sports.entity.Player;
import com.chandan.sports.service.PlayerService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/player")
@RequiredArgsConstructor
public class PlayerController {

    private final PlayerService playerService;

    @PostMapping
    public Player createPlayer(@Valid @RequestBody Player newPlayer){
        return playerService.createPlayer(newPlayer);
    }

    @GetMapping
    public List<Player> getAllPlayer(){
        return playerService.getAllPlayer();
    }

    @GetMapping("/{id}")
    public Player getPlayerById(@PathVariable Long id){
        return playerService.getPlayerById(id);
    }

    @PatchMapping("/{id}")
    public Player updatePlayer(@PathVariable Long id, @RequestBody Player updatePlayerData){
        return playerService.updatePlayer(id, updatePlayerData);
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deletePlayer(@PathVariable Long id){
        playerService.deletePlayer(id);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/{id}/country-fn")
    public ResponseEntity<String> updateCountryusingFUnction(@PathVariable Long id, @RequestParam String country){
        Integer rowsChanged = playerService.updateCountryViaFunction(id, country);
        return ResponseEntity.ok("Success! Rows updated: " + rowsChanged);
    }

}
