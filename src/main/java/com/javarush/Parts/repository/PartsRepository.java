package com.javarush.Parts.repository;

import com.javarush.Parts.model.Part;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PartsRepository extends JpaRepository<Part, Long> {

    @Query("FROM Part WHERE necessity=:value")
    List<Part> findByNecessity(@Param("value") Boolean value);

    @Query("FROM Part WHERE lower(name) LIKE CONCAT('%',:name,'%')")
    List<Part> findByName(@Param("name") String name);
}
