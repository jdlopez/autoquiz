package es.jdl.autoquiz;

import es.jdl.auth.BasicAuthenticationFilter;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
@MapperScan("es.jdl.autoquiz.dao")
public class AutoquizApplication {

	public static void main(String[] args) {
		SpringApplication.run(AutoquizApplication.class, args);
	}

	@Bean
	public FilterRegistrationBean<BasicAuthenticationFilter> authFilter() {

		FilterRegistrationBean<BasicAuthenticationFilter > registrationBean
				= new FilterRegistrationBean<>();

		registrationBean.setFilter(new BasicAuthenticationFilter());
		registrationBean.addInitParameter("realm", "autoquiz");
		registrationBean.addInitParameter("user", "admin");
		registrationBean.addInitParameter("password", System.getenv("ADMIN_PASS"));
		registrationBean.addUrlPatterns("/admin/*");

		return registrationBean;
	}

}
