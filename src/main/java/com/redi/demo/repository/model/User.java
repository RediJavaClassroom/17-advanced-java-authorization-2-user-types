package com.redi.demo.repository.model;


import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Entity
@Table(name = "users")
public class User implements UserDetails {

  @Id
  private String email;
  private String name;
  private String passwordHash;
  @ElementCollection(targetClass = String.class)
  private Collection<String> authorities;
  private String userType;

//  @OneToMany(mappedBy = "email", fetch = FetchType.LAZY)
//  private List<ShortLinkEntity> shortlinks;

  protected User() {}

  public User(
          String email,
          String name,
          String passwordHash,
          Collection<String> authorities,
          String userType
  ) {
    this.email = email;
    this.name = name;
    this.passwordHash = passwordHash;
    this.authorities = authorities;
    this.userType = userType;
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
