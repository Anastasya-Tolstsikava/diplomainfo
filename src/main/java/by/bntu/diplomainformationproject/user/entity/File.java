package by.bntu.diplomainformationproject.user.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;

@Entity
@Data
public class File {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "USER_ID_SEQ")
    @SequenceGenerator(name = "USER_ID_SEQ", sequenceName = "USER_ID_SEQ")
    private Long id;

    private String path;
    private String category;
}
