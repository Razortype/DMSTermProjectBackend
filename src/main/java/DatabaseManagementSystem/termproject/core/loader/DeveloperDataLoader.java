package DatabaseManagementSystem.termproject.core.loader;

import DatabaseManagementSystem.termproject.auth.AuthenticationService;
import DatabaseManagementSystem.termproject.business.abstracts.ProfessionService;
import DatabaseManagementSystem.termproject.business.abstracts.ThesisLanguageService;
import DatabaseManagementSystem.termproject.business.abstracts.ThesisTypeService;
import DatabaseManagementSystem.termproject.business.abstracts.UserService;
import DatabaseManagementSystem.termproject.core.enums.Gender;
import DatabaseManagementSystem.termproject.core.enums.Role;
import DatabaseManagementSystem.termproject.entities.Profession;
import DatabaseManagementSystem.termproject.entities.ThesisLanguage;
import DatabaseManagementSystem.termproject.entities.ThesisType;
import DatabaseManagementSystem.termproject.user.User;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class DeveloperDataLoader implements ApplicationRunner {

    private final AuthenticationService authService;
    private final UserService userService;
    private final ProfessionService professionService;
    private final ThesisLanguageService thesisLanguageService;
    private final ThesisTypeService thesisTypeService;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    @Value("${spring.jpa.hibernate.ddl-auto}")
    private String appStatus;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        if (appStatus.equals("create") || appStatus.equals("create-drop")) {
            log.warn("Developer mod activated : adding required substances");
            addProfessions();
            addThesisLanguages();
            addThesisTypes();
        }

        if (appStatus.equals("create-drop")) {
            createAdminUser("test.user@gmail.com", "1234");
        }
    }

    public void addProfessions() {
        String[][] professionData = {
                {"Not Defined", "No profession or not defined."},
                {"STUDENT", "A student pursuing academic studies."},
                {"PROFESSOR", "A university or college professor who teaches and conducts research."},
                {"RESEARCHER", "A person engaged in research activities."},
                {"TUTOR", "An individual providing one-on-one or small-group teaching and assistance."},
                {"TEACHING_ASSISTANT", "An assistant to a professor, often responsible for helping with teaching duties."},
                {"SPECIALIST", "An expert or professional with specialized knowledge."}
        };
        for (String[] profession : professionData) {
            professionService.saveProfession(
                    Profession.builder()
                            .professionName(profession[0])
                            .professionDescription(profession[1])
                            .build()
            );
            log.info("Profession added: " + profession[0]);
        }
    }

    public void addThesisLanguages() {
        String[] languages = {"Turkish", "English", "French"};
        for (String language: languages) {
            thesisLanguageService.saveThesisLanguage(
                    ThesisLanguage.builder().thesisLanguage(language).build());
            log.info("Thesis Language added:" + language);
        }
    }

    public void addThesisTypes() {
        String[][] thesisTypeData = {
                {"Master's Thesis", "An academic research project completed as part of a Master's degree program."},
                {"Doctorate Thesis", "The culminating research work required for the completion of a doctoral degree."},
                {"Specialization in Medicine Thesis", "A research project focused on a specialized area of medicine."},
                {"Proficiency in Art Thesis", "A creative or research project demonstrating mastery in the field of art."}
        };
        for (String[] thesisType: thesisTypeData) {
            thesisTypeService.saveThesisType(
                    ThesisType.builder()
                            .thesisTypeName(thesisType[0])
                            .thesisTypeDescription(thesisType[1])
                            .build()
            );
            log.info("Thesis Type added: " + thesisType[0]);
        }
    }

    public void createAdminUser(String email, String password) {

        userService.saveUser(
                User.builder()
                        .firstname("Test")
                        .lastname("User")
                        .email("test.user@gmail.com")
                        .password(bCryptPasswordEncoder.encode(password))
                        .gender(Gender.OTHER)
                        .birthYear(2000)
                        .profession(professionService.getById(1).getData())
                        .role(Role.ADMIN)
                        .build());
        log.info("Admin User created: " + email);
    }

}
