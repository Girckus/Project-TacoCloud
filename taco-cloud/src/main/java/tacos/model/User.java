package tacos.model;

import java.io.IOException;
import java.io.Serializable;
import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.Id;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

@Entity
public class User implements Serializable, UserDetails {

	private static final long serialVersionUID = 1L;

	@Id
	private Long id;

	private String username;
	private String password;
	private String fullname;
	private String street;
	private String city;
	private String state;
	private String zip;
	private String phoneNumber;
	
	public User() {
		super();
	}
	
	public User(String username, String password, String fullname, String street, String city, String state, String zip, String phoneNumber) {
		this.username = username;
		this.password = password;
		this.fullname = fullname;
		this.street = street;
		this.city = city;
		this.state = state;
		this.zip = zip;
		this.phoneNumber = phoneNumber;
	}

	@JsonDeserialize(using = CustomAuthorityDeserializer.class)
	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		if(username.equals("admin")) {
			return List.of(new SimpleGrantedAuthority("ROLE_ADMIN"), new SimpleGrantedAuthority("ROLE_USER"));
		} else {
			return List.of(new SimpleGrantedAuthority("ROLE_USER"));
		}
	}
	
	private static class CustomAuthorityDeserializer extends JsonDeserializer<List<SimpleGrantedAuthority>> {
	    @Override
	    public List<SimpleGrantedAuthority> deserialize(JsonParser jp, DeserializationContext ctxt) throws IOException {
	        ObjectMapper mapper = (ObjectMapper) jp.getCodec();
	        JsonNode jsonNode = mapper.readTree(jp);
	        List<SimpleGrantedAuthority> grantedAuthorities = new LinkedList<>();

	        Iterator<JsonNode> elements = jsonNode.elements();
	        while (elements.hasNext()) {
	            JsonNode next = elements.next();
	            JsonNode authority = next.get("authority");
	            grantedAuthorities.add(new SimpleGrantedAuthority(authority.asText()));
	        }
	        return grantedAuthorities;
	    }
	}
	
	@Override
	public boolean isAccountNonExpired() {
		return true;
	}
	
	@Override
	public boolean isAccountNonLocked() {
		return true;
	}
	
	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}
	
	@Override
	public boolean isEnabled() {
		return true;
	}
	
	public String getUsername() {
		return username;
	}

	public String getPassword() {
		return password;
	}

	public String getFullname() {
		return fullname;
	}

	public String getStreet() {
		return street;
	}

	public String getCity() {
		return city;
	}

	public String getState() {
		return state;
	}

	public String getZip() {
		return zip;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getId() {
		return id;
	}

}