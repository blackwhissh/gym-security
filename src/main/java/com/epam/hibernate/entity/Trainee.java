package com.epam.hibernate.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import org.hibernate.annotations.Cascade;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.*;

@Entity()
@Table(name = "trainee")
public class Trainee {
    @Id
    @SequenceGenerator(name = "trainee_id_seq", allocationSize = 1)
    @GeneratedValue(generator = "trainee_id_seq", strategy = GenerationType.SEQUENCE)
    @Column(name = "trainee_id", nullable = false)
    private Long traineeId;
    @Column(name = "date_of_birth")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date dob;
    @Column(name = "address")
    private String address;
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "user_id")
    private User user;
    @OneToMany(mappedBy = "trainee", orphanRemoval = true, fetch = FetchType.EAGER)
    @Cascade({org.hibernate.annotations.CascadeType.ALL,
            org.hibernate.annotations.CascadeType.DELETE_ORPHAN})
    private List<Training> trainings = new ArrayList<>();
    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinTable(
            name = "trainee_trainer_mapping",
            joinColumns = @JoinColumn(name = "trainee_id"),
            inverseJoinColumns = @JoinColumn(name = "trainer_id")
    )
    private Set<Trainer> trainers = new HashSet<>();

    public Trainee() {
    }

    public Trainee(User user) {
        this.user = user;
    }

    public Trainee(Date dob, String address, User user) {
        this.dob = dob;
        this.address = address;
        this.user = user;
    }

    public Trainee(Date dob, String address, User user, List<Training> trainings) {
        this.dob = dob;
        this.address = address;
        this.user = user;
        this.trainings = trainings;
    }

    public Long getTraineeId() {
        return traineeId;
    }

    public void setTraineeId(Long traineeId) {
        this.traineeId = traineeId;
    }

    public Date getDob() {
        return dob;
    }

    public void setDob(Date dob) {
        this.dob = dob;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
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
        training.setTrainee(this);
    }

    public void removeTraining(Training training) {
        trainings.remove(training);
        training.setTrainee(null);
    }

    public Set<Trainer> getTrainers() {
        return trainers;
    }

    public void setTrainers(Set<Trainer> trainers) {
        this.trainers = trainers;
    }

    @Override
    public String toString() {
        return "Trainee{" +
                "traineeId=" + traineeId +
                ", dob=" + dob +
                ", address='" + address + '\'' +
                ", user=" + user +
                ", trainings=" + trainings +
                '}';
    }

}
