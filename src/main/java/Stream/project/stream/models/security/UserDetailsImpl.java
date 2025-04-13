package Stream.project.stream.models.security;

import Stream.project.stream.models.User;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;
import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

@Data
public class UserDetailsImpl implements UserDetails {

  /**
   *
   */
  private static final long serialVersionUID = 1L;
  private Long id;
  private String username;
  private String email;
  private String password;
  private Collection<? extends GrantedAuthority> authorities;
  private User user;

  public UserDetailsImpl(Long id, String username, String email, String password,
      Collection<? extends GrantedAuthority> authorities, User user) {
    super();
    this.id = id;
    this.username = username;
    this.email = email;
    this.password = password;
    this.authorities = authorities;
    this.user = user;
  }

  public UserDetailsImpl() {
    // TODO Auto-generated constructor stub
  }

  public static UserDetailsImpl build(User user) {
    List<GrantedAuthority> authorities = user.getRole().stream()
        .map(role -> new SimpleGrantedAuthority(role.getRoleName().name()))
        .collect(Collectors.toList());
    return new UserDetailsImpl(user.getId(), user.getUserName(), user.getEmail(),
        user.getPassword(), authorities, user);
  }

  public static long getSerialversionuid() {
    return serialVersionUID;
  }

  @Override
  public Collection<? extends GrantedAuthority> getAuthorities() {
    return this.authorities;
  }

  public void setAuthorities(Collection<? extends GrantedAuthority> authorities) {
    this.authorities = authorities;
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  @Override
  public String getPassword() {
    // TODO Auto-generated method stub
    return this.password;
  }

  public void setPassword(String password) {
    this.password = password;
  }

  @Override
  public String getUsername() {
    // TODO Auto-generated method stub
    return this.username;
  }

  public void setUsername(String username) {
    this.username = username;
  }

  @Override
  public boolean isAccountNonExpired() {
    // TODO Auto-generated method stub
    return true;
  }

  @Override
  public boolean isAccountNonLocked() {
    // TODO Auto-generated method stub
    return true;
  }

  @Override
  public boolean isCredentialsNonExpired() {
    // TODO Auto-generated method stub
    return true;
  }

  @Override
  public boolean isEnabled() {
    // TODO Auto-generated method stub
    return true;
  }

}
