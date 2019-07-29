package com.example.demo.entity;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Data;

import java.util.Date;

@Data
@Document(collection = "shedlock")
public class WeatherMongo {
  @Id
    private String name;
    private Date lock_until;
    private Date locked_at;
    private String locked_by;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getLock_until() {
        return lock_until;
    }

    public void setLock_until(Date lock_until) {
        this.lock_until = lock_until;
    }

    public Date getLocked_at() {
        return locked_at;
    }

    public void setLocked_at(Date locked_at) {
        this.locked_at = locked_at;
    }

    public String getLocked_by() {
        return locked_by;
    }

    public void setLocked_by(String locked_by) {
        this.locked_by = locked_by;
    }
}
