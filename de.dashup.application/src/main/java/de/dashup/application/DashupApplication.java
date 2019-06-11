package de.dashup.application;

import de.dashup.model.db.Database;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.Bean;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.i18n.CookieLocaleResolver;
import org.springframework.web.servlet.i18n.LocaleChangeInterceptor;

import java.util.Locale;
import java.util.Objects;

@SpringBootApplication
public class DashupApplication extends SpringBootServletInitializer implements WebMvcConfigurer {

    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
        return builder.sources(DashupApplication.class);
    }

    public static void main(String[] args) {
        // setting up configurations
        Database.DatabaseName databaseName = null;
        Database.setHost(false);
        for (String arg : args) {
            if (arg.contains("--db-host=")) {
                Database.setHost(arg.substring(10).equals("local"));
            } else if (arg.contains("--database=") && Database.DatabaseName.getByShortCut(arg.substring(11)) != null) {
                databaseName = Database.DatabaseName.getByShortCut(arg.substring(11));
            }
        }

        Database.setDatabaseName(Objects.requireNonNullElse(databaseName, Database.DatabaseName.DEV));

        // run spring application
        SpringApplication.run(DashupApplication.class, args);
    }

    @Bean
    public LocaleResolver localeResolver() {
        CookieLocaleResolver cookieLocaleResolver = new CookieLocaleResolver();
        cookieLocaleResolver.setDefaultLocale(Locale.US);
        return cookieLocaleResolver;
    }

    @Bean
    public LocaleChangeInterceptor localeChangeInterceptor() {
        LocaleChangeInterceptor lci = new LocaleChangeInterceptor();
        lci.setParamName("lang");
        return lci;
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(localeChangeInterceptor());
    }
}
