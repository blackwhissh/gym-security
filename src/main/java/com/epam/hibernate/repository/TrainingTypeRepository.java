package com.epam.hibernate.repository;

import com.epam.hibernate.entity.TrainingType;
import com.epam.hibernate.entity.TrainingTypeEnum;
import com.epam.hibernate.exception.TrainingTypeEnumNotFoundException;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityNotFoundException;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import org.hibernate.HibernateException;
import org.hibernate.TransactionException;
import org.springframework.stereotype.Repository;

import java.util.Arrays;
import java.util.List;
import java.util.logging.Logger;

@Repository
public class TrainingTypeRepository {
    private static final Logger logger = Logger.getLogger(TrainingTypeRepository.class.getName());
    @PersistenceContext
    private EntityManager entityManager;

    @Transactional
    public void addTrainingTypes() {
        TrainingType trainingType;
        for (TrainingTypeEnum typeEnum : TrainingTypeEnum.values()) {
            try {
                trainingType = new TrainingType(typeEnum);
                entityManager.persist(trainingType);
            } catch (HibernateException e) {
                logger.warning("Error adding training types");
                throw new TransactionException(e.getMessage());
            }
        }
        logger.info("Successfully added training types");

    }

    public TrainingType selectById(Integer id) {
        TrainingType trainingType = entityManager.find(TrainingType.class, id);
        if (trainingType != null) {
            logger.info("Training type found");
        } else {
            logger.warning("Training type not found");
            throw new EntityNotFoundException("Wrong id - " + id);
        }
        return trainingType;
    }

    @SuppressWarnings("unchecked")
    public List<TrainingType> getAll() {
        return entityManager.createQuery("from TrainingType ").getResultList();
    }

    public TrainingType selectByType(TrainingTypeEnum typeEnum) {
        List<TrainingTypeEnum> types = Arrays.stream(TrainingTypeEnum.values()).toList();
        if (!types.contains(typeEnum)) {
            throw new TrainingTypeEnumNotFoundException();
        }
        TrainingType trainingType = null;
        for (TrainingType current : getAll()) {
            if (current.getTrainingTypeName() == typeEnum) {
                trainingType = current;
                break;
            }
        }
        return trainingType;
    }
}
