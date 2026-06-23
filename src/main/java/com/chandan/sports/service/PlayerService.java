package com.chandan.sports.service;

import com.chandan.sports.entity.Player;
import com.chandan.sports.entity.Sport;
import com.chandan.sports.repository.PlayerRepository;
import com.chandan.sports.repository.SportRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@AllArgsConstructor
public class PlayerService {

    private final PlayerRepository playerRepository;
    private final SportRepository sportRepository; // Inject SportRepository to validate the sport

    public Player createPlayer(Player newPlayer){
        // 1. Validate that the sport block is provided in the JSON
        if (newPlayer.getSport() == null || newPlayer.getSport().getId() == null) {
            throw new IllegalArgumentException("Sport ID is mandatory to register a player.");
        }
        // 2. Fetch the managed Sport entity from the database to ensure it exists
        Long sportId = newPlayer.getSport().getId();
        Sport managedSport = sportRepository.findById(sportId)
                .orElseThrow(()->new RuntimeException(""));
        // 3. Attach the verified, managed database sport to our player
        newPlayer.setSport(managedSport);

        // 4. Save safely
        return playerRepository.save(newPlayer);
    }

    public List<Player> getAllPlayer(){
        return playerRepository.findAll();
    }

    public Player getPlayerById(Long id){
        return playerRepository.findById(id)
                .orElseThrow(()-> new RuntimeException("Player with id: "+id+"not found"));
    }

    @Transactional
    public Player updatePlayer(Long id, Player updatePlayerData){
        Player existingPlayer = playerRepository.findById(id)
                .orElseThrow(()-> new RuntimeException("Player with id: \"+id+\"not found"));

        // 1. Update basic fields if they are provided
        if (updatePlayerData.getName() != null && !updatePlayerData.getName().isBlank()) {
            existingPlayer.setName(updatePlayerData.getName());
        }
        if (updatePlayerData.getSpeciality() != null) {
            existingPlayer.setSpeciality(updatePlayerData.getSpeciality());
        }
        if (updatePlayerData.getCountry() != null) {
            existingPlayer.setCountry(updatePlayerData.getCountry());
        }
        // 2. Handle the Sport relationship safely
        if (updatePlayerData.getSport() != null && updatePlayerData.getSport().getId() != null){
            Long newSportID = updatePlayerData.getSport().getId();

            // Fetch the official sport record from the database
            Sport managerSport = sportRepository.findById(newSportID)
                    .orElseThrow(()-> new RuntimeException("Sport ID " + newSportID + " does not exist"));
            // This ONLY changes the foreign key (sport_id) in the player table.
            // It completely ignores any other modified sport fields sent in the JSON.
            existingPlayer.setSport(managerSport);
        }

        return playerRepository.save(existingPlayer);
    }

    public void deletePlayer(Long id) {
        // Fixed the string quotes and added clean spacing
        Player player = playerRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Player with id " + id + " not found"));

        playerRepository.delete(player);
    }
}
