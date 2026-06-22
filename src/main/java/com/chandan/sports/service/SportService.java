package com.chandan.sports.service;

import com.chandan.sports.entity.Sport;
import com.chandan.sports.repository.SportRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class SportService{
    private final SportRepository sportRepository;

    public Sport createSport(Sport newSport){
        // Business Validation: A sport cannot have negative players
        if (newSport.getPlayers() != null && newSport.getPlayers() < 0){
            throw new IllegalArgumentException("A sport cannot have a negative number of players!");
        }

        // Business Validation: Prevent blank names
        if (newSport.getName() == null || newSport.getName().trim().isEmpty()) {
            throw new IllegalArgumentException("Sport name is mandatory.");
        }
        return sportRepository.save(newSport);
    }

    public List<Sport> getAllSport(){
        return sportRepository.findAll();
    }

    public Sport getSportById(Long id){
        return sportRepository.findById(id)
                .orElseThrow(()-> new RuntimeException("Sport with id \" + id + \" not found"));
    }

    public Sport updateSport(Long id, Sport updatedSportData){

       Sport existingSport = sportRepository.findById(id)
               .orElseThrow(()-> new RuntimeException("Sports with id: "+id+ "not exist"));

        //for patch The incoming updatedSportData object will have name = null and isBallNeeded = null.

       if (updatedSportData.getName() != null){
           existingSport.setName(updatedSportData.getName());
       }

       if (updatedSportData.getPlayers() != null){
           existingSport.setPlayers((updatedSportData.getPlayers()));
       }

       if (updatedSportData.getIsBallNeeded() != null) {
           existingSport.setIsBallNeeded((updatedSportData.getIsBallNeeded()));
       }
       return sportRepository.save(existingSport);
    }

    public void deleteSport(Long id){
        Sport sportExists = sportRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Sports with id: "+id+ "not exist"));
        sportRepository.delete(sportExists); //// Passing the entity is safer than deleteById
    }

}
