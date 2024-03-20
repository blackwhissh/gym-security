package com.epam.hibernate.entity;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "trainer")
public class Trainer {
    @Id
    @SequenceGenerator(name = "trainer_id_seq", allocationSize = 1)
    @GeneratedValue(generator = "trainer_id_seq", strategy = GenerationType.SEQUENCE)
    @Column(name = "trainer_id", nullable = false)
    private Long trainerId;
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "specialization")
    @Enumerated
    private TrainingType specialization;
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "user_id")
    private User user;
    @OneToMany(mappedBy = "trainer", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
    private List<Training> trainings = new ArrayList<>();
    @ManyToMany(mappedBy = "trainers", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private Set<Trainee> trainees = new HashSet<>();

    public Trainer() {
    }

    public Trainer(TrainingType specialization, User user) {
        this.specialization = specialization;
        this.user = user;
    }

    public Trainer(TrainingType specialization, User user, List<Training> trainings) {
        this.specialization = specialization;
        this.user = user;
        this.trainings = trainings;
    }

    public Set<Trainee> getTrainees() {
        return trainees;
    }

    public void setTrainees(Set<Trainee> trainees) {
        this.trainees = trainees;
    }

    public Long getTrainerId() {
        return trainerId;
    }

    public void setTrainerId(Long trainerId) {
        this.trainerId = trainerId;
    }

    public TrainingType getSpecialization() {
        return specialization;
    }

    public void setSpecialization(TrainingType specialization) {
        this.specialization = specialization;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public List<Training> getTrainings() {
        return trainings;
    }

    public void setTrainings(List<Training> trainings) {
        this.trainings = trainings;
    }

    public void addTraining(Training training) {
        trainings.add(training);
        training.setTrainer(this);
    }

    public void removeTraining(Training training) {
        trainings.remove(training);
        training.setTrainer(null);
    }

    @Override
    public String toString() {
        return "Trainer{" +
                "trainerId=" + trainerId +
                ", specialization=" + specialization +
                ", user=" + user +
                '}';
    }
}
