package smartchat.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
	
    @Override
    protected void configure(HttpSecurity http) throws Exception {

        http
            .authorizeRequests()
                .anyRequest().authenticated()
                .and()
            .formLogin()
                .loginPage("/login")
                .permitAll()
                .and()
            .logout()
                .permitAll()
                .and()
            .csrf()
                .ignoringAntMatchers("/login","/logout");
    }

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        auth
            .inMemoryAuthentication()
                .withUser("franciscoperez").password("123456").roles("USER").and()
                .withUser("joseduran").password("123456").roles("USER").and()
                .withUser("silviapanadero").password("123456").roles("USER").and()
                .withUser("ramiroiglesias").password("123456").roles("USER").and()
                .withUser("julianarnau").password("123456").roles("USER").and()
                .withUser("rafaelsegura").password("123456").roles("USER").and()
                .withUser("isaacgarcia").password("123456").roles("USER").and()
                .withUser("unusuarioconunnombremuylargoojuuu").password("123456").roles("USER").and()
                .withUser("danielrodriguez").password("123456").roles("USER");
    }
    
}