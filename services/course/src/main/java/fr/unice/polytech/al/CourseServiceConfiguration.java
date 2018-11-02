package fr.unice.polytech.al;

import fr.unice.polytech.al.model.Course;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.rest.core.config.RepositoryRestConfiguration;
import org.springframework.data.rest.webmvc.config.RepositoryRestConfigurerAdapter;

@Configuration
public class CourseServiceConfiguration extends RepositoryRestConfigurerAdapter{

    //Met l'id de ma course dans le json de retour
    @Override
    public void configureRepositoryRestConfiguration(RepositoryRestConfiguration config){
        config.exposeIdsFor(Course.class);
    }
}
