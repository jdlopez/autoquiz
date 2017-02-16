package es.jdl.autoquiz.config;

import java.util.logging.Logger;

import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import com.googlecode.objectify.ObjectifyService;

import es.jdl.autoquiz.domain.Answer;
import es.jdl.autoquiz.domain.Question;
import es.jdl.autoquiz.domain.Topic;
import es.jdl.autoquiz.domain.Trial;

@Configuration
@EnableWebMvc
@ComponentScan (basePackages={"es.jdl.autoquiz"})
public class AppConfig implements ApplicationListener<ContextRefreshedEvent> {

	private static final Logger log = Logger.getLogger(AppConfig.class.getName());
	
	@Override
	public void onApplicationEvent(ContextRefreshedEvent event) {
		log.info("Inicializando/refrescando contexto: " + event.toString());
		registerObjectifyClasses();
		// do something else
	}

	private void registerObjectifyClasses() {
		ObjectifyService.register(Trial.class);
		ObjectifyService.register(Topic.class);
		ObjectifyService.register(Question.class);
		ObjectifyService.register(Answer.class);
	}

}
