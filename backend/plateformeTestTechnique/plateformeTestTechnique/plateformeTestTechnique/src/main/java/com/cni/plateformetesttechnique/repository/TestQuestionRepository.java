package com.cni.plateformetesttechnique.repository;

import com.cni.plateformetesttechnique.model.TestQuestion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TestQuestionRepository extends JpaRepository<TestQuestion, Long> {

    public List<TestQuestion> findByTestId(Long testId);

}
