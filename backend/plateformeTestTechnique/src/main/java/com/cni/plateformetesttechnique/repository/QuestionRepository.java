package com.cni.plateformetesttechnique.repository;

import com.cni.plateformetesttechnique.model.Question;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface QuestionRepository extends JpaRepository<Question, Long> {
    Optional<Question> findById(Long id);

}

