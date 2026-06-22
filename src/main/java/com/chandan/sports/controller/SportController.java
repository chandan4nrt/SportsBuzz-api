package com.chandan.sports.controller;

import com.chandan.sports.entity.Sport;
import com.chandan.sports.service.SportService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/sport")
@RequiredArgsConstructor
public class SportController {
    private final SportService sportService;

    @PostMapping
    public Sport createSport(@RequestBody Sport newSport){
        return sportService.createSport(newSport);
    }

    @GetMapping
    public List<Sport> getAllSport(){
        return sportService.getAllSport();
    }

    @GetMapping("/{id}")
    public Sport getSportById(@PathVariable Long id){
        return sportService.getSportById(id);
    }

    @PatchMapping("/{id}")
    public Sport updateSport(@PathVariable Long id,@RequestBody Sport updatedSportData){
        return sportService.updateSport(id,updatedSportData);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteSport(@PathVariable Long id){
        // 1. Trigger business logic
        sportService.deleteSport(id);
        // 2. Return HTTP 204 No Content
        return ResponseEntity.noContent().build();

    }
}
