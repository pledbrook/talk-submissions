package cacoethes.config
  
import org.pac4j.springframework.security.web.ClientAuthenticationFilter
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Configuration
import org.springframework.security.authentication.AuthenticationProvider
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter
  
@Configuration
@EnableWebSecurity
class SecurityConfiguration extends WebSecurityConfigurerAdapter {
    @Autowired AuthenticationProvider clientProvider
    @Autowired ClientAuthenticationFilter clientFilter

    protected void configure(HttpSecurity http) throws Exception {
        http
            .addFilterBefore(clientFilter, UsernamePasswordAuthenticationFilter)
            .authorizeRequests()
                .antMatchers('/admin/**').hasAuthority('ROLE_ADMIN')
//                .antMatchers('/mailTemplate/**').hasAuthority('ROLE_ADMIN')
//                .antMatchers('/talkAssignment/**').hasAuthority('ROLE_ADMIN')
//                .antMatchers('/timeSlot/**').hasAuthority('ROLE_ADMIN')
//                .antMatchers('/user/**').hasAuthority('ROLE_ADMIN')
                .antMatchers('/profile/list').hasAuthority('ROLE_ADMIN')
//                .antMatchers('/profile/acceptedEmails').hasAuthority('ROLE_ADMIN')
//                .antMatchers('/profile/submittedEmails').hasAuthority('ROLE_ADMIN')
                .antMatchers('/profile/acceptedExpenses').hasAuthority('ROLE_ADMIN')
                .antMatchers('/profile/delete').hasAuthority('ROLE_ADMIN')
                .antMatchers('/profile/**').hasAuthority('ROLE_USER')
                .antMatchers('/submission/conflicts').hasAuthority('ROLE_ADMIN')
                .antMatchers('/submission/delete').hasAuthority('ROLE_ADMIN')
                .antMatchers('/submission/sendStatusEmail').hasAuthority('ROLE_ADMIN')
                .antMatchers('/submission/**').hasAuthority('ROLE_USER')
                .antMatchers('/assets/**').permitAll()
                .antMatchers('/').hasAuthority('ROLE_USER')
                .antMatchers('/dbconsole/**').permitAll()
                .antMatchers('/login/auth**').permitAll()
                .antMatchers('/health').permitAll()
                .antMatchers('/shutdown').permitAll()
                .anyRequest().fullyAuthenticated()
            .and()
                .formLogin().loginPage("/login/auth").permitAll()
            .and()
                .logout().permitAll()
            .and()
                .rememberMe()

        // Only for early testing purposes
        http.headers().frameOptions().disable()
        http.csrf().disable()
    }
  
    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        auth.authenticationProvider(clientProvider)
    }
}
