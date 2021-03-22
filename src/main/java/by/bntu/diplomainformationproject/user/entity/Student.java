package by.bntu.diplomainformationproject.user.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;

@Entity
@EqualsAndHashCode(callSuper = true)
@Data
public class Student extends User {

    @ManyToOne
    private Teacher teacher;
}
