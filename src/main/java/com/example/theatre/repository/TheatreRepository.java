package com.example.theatre.repository;

import com.example.theatre.entity.Theatre;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TheatreRepository extends JpaRepository<Theatre, Long>, PagingAndSortingRepository<Theatre,Long> {
    @Override
    Optional<Theatre> findById(Long id);

    @Override
    List<Theatre> findAll();
}
