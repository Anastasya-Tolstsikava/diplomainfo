package by.bntu.diplomainformationproject.user.dto;

import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class IdDto {

    @NotNull
    private Long id;
}
