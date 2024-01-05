package DatabaseManagementSystem.termproject.core.loader;

import DatabaseManagementSystem.termproject.business.abstracts.*;
import DatabaseManagementSystem.termproject.entities.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Component
@RequiredArgsConstructor
@Slf4j
public class InitialDataLoader implements ApplicationRunner {

    @Value("${spring.jpa.hibernate.ddl-auto}")
    private String appStatus;

    private final ProfessionService professionService;
    private final ThesisLanguageService languageService;
    private final ThesisTypeService typeService;
    private final UniversityService universityService;
    private final InstituteService instituteService;

    @Override
    public void run(ApplicationArguments args) throws Exception {

        String[][] professionData = {
                {"Not Defined", "No profession or not defined."},
                {"STUDENT", "A student pursuing academic studies."},
                {"PROFESSOR", "A university or college professor who teaches and conducts research."},
                {"RESEARCHER", "A person engaged in research activities."},
                {"TUTOR", "An individual providing one-on-one or small-group teaching and assistance."},
                {"TEACHING_ASSISTANT", "An assistant to a professor, often responsible for helping with teaching duties."},
                {"SPECIALIST", "An expert or professional with specialized knowledge."}
        };

        String[][] thesisTypeData = {
                {"Master's Thesis", "An academic research project completed as part of a Master's degree program."},
                {"Doctorate Thesis", "The culminating research work required for the completion of a doctoral degree."},
                {"Specialization in Medicine Thesis", "A research project focused on a specialized area of medicine."},
                {"Proficiency in Art Thesis", "A creative or research project demonstrating mastery in the field of art."}
        };

        String[] languageData = {"Turkish", "English", "French"};

        String[] universityData = {
                "Maltepe University",
                "Marmara University",
                "Middle East Technical University",
                "Istanbul Technical University",
                "Yeditepe University"
        };

        String[] instituteData = {
                "Institute of Science and Technology",
                "Institute of Disaster Management",
                "Institute of Informatics",
                "Institute of Energy",
                "Institute of Aviation"
        };

        if (appStatus.equals("update")) {
            loadProfessionData(professionData);
            loadThesisTypeData(thesisTypeData);
            loadLanguageData(languageData);
            loadUniversityData(universityData);
            loadInstituteData(instituteData);
        }
    }

    public void loadProfessionData(String[][] professionData) {

        Arrays.stream(professionData)
                .filter(obj -> !professionService
                        .getByProfessionName(obj[0])
                        .isSuccess())
                .map(obj -> {
                    log.info("Profession added: " + obj[0]);
                    return Profession.builder()
                            .professionName(obj[0])
                            .professionDescription(obj[1])
                            .build();
                    }
                ).forEach(professionService::saveProfession);
    }

    public void loadThesisTypeData(String[][] thesisTypeData) {

        Arrays.stream(thesisTypeData)
                .filter(obj -> !typeService
                        .getByThesisTypeName(obj[0])
                        .isSuccess())
                .map(obj -> {
                    log.info("Thesis Type added: " + obj[0]);
                    return ThesisType.builder()
                            .thesisTypeName(obj[0])
                            .thesisTypeDescription(obj[1])
                            .build();
                    }
                ).forEach(typeService::saveThesisType);
    }

    public void loadLanguageData(String[] languageData) {

        Arrays.stream(languageData)
                .filter(language -> !languageService
                        .getByLanguage(language)
                        .isSuccess())
                .map(language -> {
                    log.info("Language added: " + language);
                    return ThesisLanguage.builder()
                            .thesisLanguage(language)
                            .build();
                    }
                ).forEach(languageService::saveThesisLanguage);

    }

    public void loadUniversityData(String[] universityData) {

        Arrays.stream(universityData)
                .filter(university -> !universityService
                        .getUniversityByUniversityName(university)
                        .isSuccess())
                .map(university -> {
                    log.info("University added: " + university);
                    return University.builder()
                            .universityName(university)
                            .build();
                    }
                ).forEach(universityService::saveUniversity);
    }

    public void loadInstituteData(String[] instituteData) {

        Arrays.stream(instituteData)
                .filter(institute -> !instituteService
                        .getInstituteByInstituteName(institute)
                        .isSuccess())
                .map(institute -> {
                    log.info("Institute added: " + institute);
                    return Institute.builder()
                            .instituteName(institute)
                            .build();
                    }
                ).forEach(instituteService::saveInstitute);

    }

}
