package de.dashup.application;

import de.dashup.model.db.Database;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

import java.util.Objects;

@SpringBootApplication
public class DashupApplication extends SpringBootServletInitializer {

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
            } else if (arg.contains("--database=")) {
                if (Database.DatabaseName.getByShortCut(arg.substring(11)) != null) {
                    databaseName = Database.DatabaseName.getByShortCut(arg.substring(11));
                }
            }
        }

        Database.setDbName(Objects.requireNonNullElse(databaseName, Database.DatabaseName.DEV));

        // run spring application
        SpringApplication.run(DashupApplication.class, args);
    }
}
