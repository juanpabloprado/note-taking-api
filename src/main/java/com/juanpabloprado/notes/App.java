package com.juanpabloprado.notes;

import com.hubspot.jackson.jaxrs.PropertyFilteringMessageBodyWriter;
import com.hubspot.rosetta.jdbi.RosettaMapperFactory;
import com.juanpabloprado.notes.auth.NotesAuthenticator;
import com.juanpabloprado.notes.representations.User;
import com.juanpabloprado.notes.resources.NoteResource;
import com.juanpabloprado.notes.resources.TokenResource;
import com.juanpabloprado.notes.resources.UserResource;
import io.dropwizard.Application;
import io.dropwizard.auth.AuthFactory;
import io.dropwizard.auth.oauth.OAuthFactory;
import io.dropwizard.db.DataSourceFactory;
import io.dropwizard.flyway.FlywayBundle;
import io.dropwizard.jdbi.DBIFactory;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;
import org.eclipse.jetty.servlets.CrossOriginFilter;
import org.skife.jdbi.v2.DBI;

import javax.servlet.DispatcherType;
import javax.servlet.FilterRegistration;
import java.util.EnumSet;

/**
 * Hello world!
 *
 */
public class App extends Application<NotesConfiguration>
{
    public static void main( String[] args ) throws Exception {
        new App().run(args);
    }

    @Override
    public void run(NotesConfiguration configuration, Environment environment) throws Exception {
        configureCors(environment);

        environment.jersey().register(new PropertyFilteringMessageBodyWriter());

        final DBIFactory factory = new DBIFactory();
        final DBI jdbi = factory.build(environment, configuration.getDataSourceFactory(), "mysql");
        jdbi.registerMapper(new RosettaMapperFactory());

        environment.jersey().register(AuthFactory.binder(new OAuthFactory<User>(new NotesAuthenticator(jdbi),
                "SUPER SECRET STUFF",
                User.class)));
        environment.jersey().register(new NoteResource(jdbi));
        environment.jersey().register(new UserResource(jdbi));
        environment.jersey().register(new TokenResource(jdbi));
    }

    @Override
    public void initialize(Bootstrap<NotesConfiguration> bootstrap) {
        bootstrap.addBundle(new FlywayBundle<NotesConfiguration>() {
            public DataSourceFactory getDataSourceFactory(NotesConfiguration configuration) {
                return configuration.getDataSourceFactory();
            }
        });
    }

    private void configureCors(Environment environment) {
        FilterRegistration.Dynamic filter = environment.servlets().addFilter("CORS", CrossOriginFilter.class);
        filter.addMappingForUrlPatterns(EnumSet.allOf(DispatcherType.class), true, "/*");
        filter.setInitParameter(CrossOriginFilter.ALLOWED_METHODS_PARAM, "GET,PUT,POST,DELETE,OPTIONS");
        filter.setInitParameter(CrossOriginFilter.ALLOWED_ORIGINS_PARAM, "*");
        filter.setInitParameter(CrossOriginFilter.ACCESS_CONTROL_ALLOW_ORIGIN_HEADER, "*");
        filter.setInitParameter("allowedHeaders", "Content-Type,Authorization,X-Requested-With,Content-Length,Accept,Origin");
        filter.setInitParameter("allowCredentials", "true");
    }
}
