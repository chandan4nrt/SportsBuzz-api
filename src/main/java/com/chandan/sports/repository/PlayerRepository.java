package com.chandan.sports.repository;

import com.chandan.sports.entity.Player;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface PlayerRepository extends JpaRepository<Player, Long> {

    @Transactional
    @Query(value = "SELECT update_player_country(:playerId, :newCountry)", nativeQuery = true)
    Integer callUpdateCountryFunction(
            @Param("playerId") Long playerId,
            @Param("newCountry") String newcountry
    );
}
