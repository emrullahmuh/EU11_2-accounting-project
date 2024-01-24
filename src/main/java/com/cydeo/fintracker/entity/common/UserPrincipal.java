package com.cydeo.fintracker.entity.common;

import com.cydeo.fintracker.entity.User;
import com.cydeo.fintracker.enums.CompanyStatus;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class UserPrincipal implements UserDetails {

    private User user;

    public UserPrincipal(User user) {
        this.user = user;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        List<GrantedAuthority> authorityList = new ArrayList<>();

        GrantedAuthority authority = new SimpleGrantedAuthority(this.user.getRole().getDescription());

        authorityList.add(authority);

        return authorityList;
    }

    @Override
    public String getPassword() {
        return this.user.getPassword();
    }

    @Override
    public String getUsername() {
        return this.user.getUsername();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return CompanyStatus.ACTIVE.equals(user.getCompany().getCompanyStatus());
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    public Long getId() {
        return this.user.getId();
    }

    /**
     * to show logged-in user firstname and lastname in UI dropdown menu
     */
    public String getFullNameForProfile() {
        return this.user.getFirstname() + " " + this.user.getLastname();
    }

    /**
     * This method is defined to show logged-in user's company title for simplicity
     *
     * @return The title of logged-in user's Company in String
     */
    public String getCompanyTitleForProfile() {

        return this.user.getCompany().getTitle().toUpperCase();
    }


}
