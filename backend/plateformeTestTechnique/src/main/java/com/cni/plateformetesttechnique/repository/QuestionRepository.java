package com.cni.plateformetesttechnique.repository;

import com.cni.plateformetesttechnique.model.Question;
import org.springframework.data.jpa.repository.JpaRepository;

public interface QuestionRepository extends JpaRepository<Question, Long> {
}

