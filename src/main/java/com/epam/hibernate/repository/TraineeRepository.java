package com.epam.hibernate.repository;

import com.epam.hibernate.entity.Trainee;
import com.epam.hibernate.entity.Training;
import com.epam.hibernate.entity.TrainingType;
import com.epam.hibernate.entity.TrainingTypeEnum;
import com.epam.hibernate.exception.TrainingTypeEnumNotFoundException;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityNotFoundException;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TemporalType;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Repository;

import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.logging.Logger;

@Repository
public class TraineeRepository {
    public static final Logger logger = Logger.getLogger(TrainerRepository.class.getName());
    @PersistenceContext
    private EntityManager entityManager;
    private final TrainingTypeRepository trainingTypeRepository;

    public TraineeRepository(TrainingTypeRepository trainingTypeRepository) {
        this.trainingTypeRepository = trainingTypeRepository;
    }

    @Transactional
    public Trainee save(Trainee trainee) {
        logger.info("Trainee saved successfully");
        return entityManager.merge(trainee);
    }

    public Trainee selectByUsername(String username) {
        Trainee trainee;
        try {
            trainee = (Trainee) entityManager.createQuery("select t from Trainee t join fetch t.user u where u.username like :username")
                    .setParameter("username", username)
                    .getSingleResult();
            logger.info("User found");
        } catch (RuntimeException e) {
            logger.warning("User not found");
            throw new EntityNotFoundException("Wrong username - " + username);

        }
        return trainee;
    }

    @Transactional
    public Trainee updateTrainee(String username, Date dob, String address, String firstName, String lastName, Boolean isActive) {
        if (dob != null) {
            entityManager.createQuery("update Trainee t set t.dob = :dob where t.user.username = :username")
                    .setParameter("dob", dob)
                    .setParameter("username", username)
                    .executeUpdate();
            logger.info("Trainee date of birth updated successfully");
        }
        entityManager.createQuery("update User u set u.firstName = :firstName where u.username = :username")
                .setParameter("firstName", firstName)
                .setParameter("username", username)
                .executeUpdate();
        logger.info("Trainee first name updated successfully");


        entityManager.createQuery("update User u set u.lastName = :lastName where u.username = :username")
                .setParameter("lastName", lastName)
                .setParameter("username", username)
                .executeUpdate();
        logger.info("Trainee last name updated successfully");


        entityManager.createQuery("update User u set u.isActive = :isActive where u.username = :username")
                .setParameter("isActive", isActive)
                .setParameter("username", username)
                .executeUpdate();
        logger.info("Trainee status updated successfully");

        if (address != null) {
            entityManager.createQuery("update Trainee t set t.address = :address where t.user.username = :username")
                    .setParameter("address", address)
                    .setParameter("username", username)
                    .executeUpdate();
            logger.info("Trainee address updated successfully");
        }

        return selectByUsername(username);
    }

    @Transactional
    public void deleteTrainee(String username) {
        Trainee trainee = selectByUsername(username);
        entityManager.createQuery("delete Training t where t.trainee = :trainee")
                .setParameter("trainee", trainee)
                .executeUpdate();
        entityManager.createQuery("delete Trainee t where t.traineeId  = :traineeId")
                .setParameter("traineeId", trainee.getTraineeId())
                .executeUpdate();
        entityManager.createQuery("delete User u where u.userId = :userId")
                .setParameter("userId", trainee.getUser().getUserId())
                .executeUpdate();
        logger.info("Trainee removed successfully");
    }

    @Transactional
    @SuppressWarnings("unchecked")
    public List<Training> getTrainingList(String traineeUsername, Date fromDate, Date toDate,
                                          String trainerName, TrainingTypeEnum trainingTypeEnum) {
        List<TrainingTypeEnum> types = Arrays.stream(TrainingTypeEnum.values()).toList();
        if (!types.contains(trainingTypeEnum)){
            throw new TrainingTypeEnumNotFoundException();
        }
        TrainingType trainingTypeName = null;
        if (trainingTypeEnum != null) {
            trainingTypeName = trainingTypeRepository.selectByType(trainingTypeEnum);
        }
        List<Training> trainingList = entityManager.createQuery("select t from Training t " +
                        "join t.trainee tr " +
                        "join t.trainer trn " +
                        "join t.trainingType tt " +
                        "where tr.user.username = :traineeUsername " +
                        "and (:fromDate is null or t.trainingDate >= :fromDate) " +
                        "and (:toDate is null or t.trainingDate <= :toDate) " +
                        "and (:trainerName is null or trn.user.firstName = :trainerName) " +
                        "and (:trainingTypeName is null or tt.trainingTypeName = :trainingTypeName)")
                .setParameter("traineeUsername", traineeUsername)
                .setParameter("fromDate", fromDate, TemporalType.DATE)
                .setParameter("toDate", toDate, TemporalType.DATE)
                .setParameter("trainerName", trainerName)
                .setParameter("trainingTypeName", trainingTypeName)
                .getResultList();
        if (trainingList.isEmpty()) {
            logger.warning("Query performed, but no result found");
        } else {
            logger.info("Trainings found successfully");
        }
        return trainingList;
    }
}
