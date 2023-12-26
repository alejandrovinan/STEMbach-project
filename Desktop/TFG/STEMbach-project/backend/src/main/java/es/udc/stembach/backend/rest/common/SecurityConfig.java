package es.udc.stembach.backend.rest.common;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Arrays;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {
	
	@Autowired
	private JwtGenerator jwtGenerator;
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		
		http.cors().and().csrf().disable()
			.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS).and()
			.addFilter(new JwtFilter(authenticationManager(), jwtGenerator))
			.authorizeRequests()
			.antMatchers(HttpMethod.POST, "/users/createAccount").hasRole("STEMCOORDINATOR")
			.antMatchers(HttpMethod.PUT, "/users/*").hasRole("STEMCOORDINATOR")
			.antMatchers(HttpMethod.GET, "/users/selectorTeachers").permitAll()
			.antMatchers(HttpMethod.POST, "/users/login").permitAll()
			.antMatchers(HttpMethod.POST, "/users/loginFromServiceToken").permitAll()
			.antMatchers(HttpMethod.GET, "/users/faculties").permitAll()
			.antMatchers(HttpMethod.GET, "/users/schools").permitAll()
			.antMatchers(HttpMethod.GET, "/projects/*").permitAll()
			.antMatchers(HttpMethod.GET, "/projects/projectDetails/*").permitAll()
			.antMatchers(HttpMethod.POST, "/users/*/changePassword").hasAnyRole("STEMCOORDINATOR","UDCTEACHER", "CENTERSTEMCOORDINATOR")
			.antMatchers(HttpMethod.POST, "/projects/createProject").hasRole("UDCTEACHER")
			.antMatchers(HttpMethod.POST, "/projects/createBiennium").hasRole("STEMCOORDINATOR")
			.antMatchers(HttpMethod.POST, "/projects/approveProject").hasRole("STEMCOORDINATOR")
			.antMatchers(HttpMethod.POST, "/projects/cancelProject").hasRole("STEMCOORDINATOR")
			.antMatchers(HttpMethod.POST, "/projects/editProject").hasRole("UDCTEACHER")
			.antMatchers(HttpMethod.POST, "/projects/requestProject").permitAll()
			.antMatchers(HttpMethod.GET, "/projects/getAllProjectRequests/*").permitAll()
			.antMatchers(HttpMethod.POST, "/projects/asignProjects").hasRole("STEMCOORDINATOR")
			.antMatchers(HttpMethod.POST, "/projects/createSchool_Faculty").hasRole("STEMCOORDINATOR")
			.antMatchers(HttpMethod.GET, "/projects/findProjectsInstancesSummary").hasAnyRole("STEMCOORDINATOR", "UDCTEACHER", "CENTERSTEMCOORDINATOR")
			.antMatchers(HttpMethod.GET, "/projects/projectInstanceDetails/*").permitAll()
			.antMatchers(HttpMethod.POST, "/defenses/create/*").hasAnyRole("STEMCOORDINATOR", "UDCTEACHER", "CENTERSTEMCOORDINATOR")
			.antMatchers(HttpMethod.POST, "/projects/editProjectInstance").hasAnyRole("STEMCOORDINATOR", "UDCTEACHER", "CENTERSTEMCOORDINATOR")
			.antMatchers(HttpMethod.GET, "/defenses/checkIfExistsDefense").hasAnyRole("STEMCOORDINATOR", "UDCTEACHER", "CENTERSTEMCOORDINATOR")
			.antMatchers(HttpMethod.GET, "/defenses/defenseDetails/*").hasAnyRole("STEMCOORDINATOR", "UDCTEACHER", "CENTERSTEMCOORDINATOR")
			.antMatchers(HttpMethod.POST, "/defenses/editDefense/*").hasAnyRole("STEMCOORDINATOR", "UDCTEACHER", "CENTERSTEMCOORDINATOR")
			.anyRequest().denyAll();

	}
	
	@Bean
	public CorsConfigurationSource corsConfigurationSource() {
		
		CorsConfiguration config = new CorsConfiguration();
	    UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
		
		config.setAllowCredentials(true);
	    config.setAllowedOriginPatterns(Arrays.asList("*"));
	    config.addAllowedHeader("*");
	    config.addAllowedMethod("*");
	    
	    source.registerCorsConfiguration("/**", config);
	    
	    return source;
	    
	 }

}
