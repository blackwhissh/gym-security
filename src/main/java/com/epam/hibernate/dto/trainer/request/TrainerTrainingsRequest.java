package com.epam.hibernate.dto.trainer.request;

import org.jetbrains.annotations.Nullable;

import java.util.Date;

public class TrainerTrainingsRequest {
    private Date from;
    private Date to;
    private String traineeName;

    public TrainerTrainingsRequest(@Nullable Date from,
                                   @Nullable Date to, @Nullable String traineeName) {
        this.from = from;
        this.to = to;
        this.traineeName = traineeName;
    }

    public Date getFrom() {
        return from;
    }

    public void setFrom(Date from) {
        this.from = from;
    }

    public Date getTo() {
        return to;
    }

    public void setTo(Date to) {
        this.to = to;
    }

    public String getTraineeName() {
        return traineeName;
    }

    public void setTraineeName(String traineeName) {
        this.traineeName = traineeName;
    }
}
