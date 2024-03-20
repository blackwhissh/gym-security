package com.epam.hibernate.repository;

import com.epam.hibernate.entity.Training;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Repository;

import java.util.logging.Logger;

@Repository
public class TrainingRepository {
    public static final Logger logger = Logger.getLogger(TrainingRepository.class.getName());
    @PersistenceContext
    private EntityManager entityManager;

    @Transactional
    public Training save(Training training) {
        entityManager.merge(training);
        logger.info("Training saved successfully");
        return training;
    }
}
