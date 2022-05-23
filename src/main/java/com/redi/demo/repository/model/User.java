package com.redi.demo.repository.model;


import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.ArrayList;
import java.util.Collection;

@Entity
@Table(name = "users")
public class User implements UserDetails {

  @Id
  private String email;
  private String name;
  private String passwordHash;
  @ElementCollection(targetClass = String.class)
  private Collection<String> authorities;

  protected User() {}

  public User(String email, String name, String passwordHash, Collection<String> authorities) {
    this.email = email;
    this.name = name;
    this.passwordHash = passwordHash;
    this.authorities = authorities;
  }

  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public void setPasswordHash(String passwordHash) {
    this.passwordHash = passwordHash;
  }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        Collection<SimpleGrantedAuthority> authorities = new ArrayList<>();

        for (String grantedAuthority : this.authorities) {
            authorities.add(new SimpleGrantedAuthority(grantedAuthority));
        }

        return authorities;
    }

  @Override
  public String getPassword() {
    return passwordHash;
  }

  @Override
  public String getUsername() {
    return email;
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
}
