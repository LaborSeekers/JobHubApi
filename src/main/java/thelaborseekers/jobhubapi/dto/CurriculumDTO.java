package thelaborseekers.jobhubapi.dto;

import lombok.Data;
import thelaborseekers.jobhubapi.model.entity.Education;
import thelaborseekers.jobhubapi.model.entity.Language;
import thelaborseekers.jobhubapi.model.entity.WorkExperience;

import java.util.List;

@Data
public class CurriculumDTO {
    private Integer id;
    private String content;
    private List<Language> languages;
    private List<WorkExperience> workExperience;
    private List<Education> education;
}
