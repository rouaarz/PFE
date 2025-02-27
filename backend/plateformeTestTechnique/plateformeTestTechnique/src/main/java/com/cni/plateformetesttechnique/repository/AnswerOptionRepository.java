package com.cni.plateformetesttechnique.repository;
import org.springframework.data.jpa.repository.JpaRepository;
import com.cni.plateformetesttechnique.model.AnswerOption;
import java.util.List;

public interface AnswerOptionRepository extends JpaRepository<AnswerOption, Long> {
    List<AnswerOption> findAllByIdIn(List<Long> ids);
}


