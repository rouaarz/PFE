package com.cni.plateformetesttechnique.repository;

import com.cni.plateformetesttechnique.model.TestQuestion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TestQuestionRepository extends JpaRepository<TestQuestion, Long> {

    public List<TestQuestion> findByTestId(Long testId);
    int countByTest_Id(Long testId);  // Compter le nombre de questions pour un test spécifique
    Optional<TestQuestion> findByTestIdAndQuestionId(Long testId, Long questionId);

}
