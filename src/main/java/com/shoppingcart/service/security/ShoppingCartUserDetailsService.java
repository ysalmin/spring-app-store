package com.shoppingcart.service.security;

import com.shoppingcart.dao.ShopDao;
import com.shoppingcart.dao.entity.UserCredential;
import javassist.tools.rmi.ObjectNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collection;

/**
 * Custom {@link UserDetailsService} implementation.
 *
 * @author salm
 */
@Service
public class ShoppingCartUserDetailsService implements UserDetailsService {
    private final static String ROLE_PREFIX = "ROLE_";

    @Autowired
    ShopDao shopDao;

    private final static Logger logger = LoggerFactory.getLogger(ShoppingCartUserDetailsService.class);

    @Override
    public UserDetails loadUserByUsername(String name) throws UsernameNotFoundException {
        UserCredential userCredential = null;
        try {
            userCredential = shopDao.getUserByName(name);
        } catch (ObjectNotFoundException e) {
            logger.error("UserCredential object not found for login: " + name);
            e.printStackTrace();
        }
        if (userCredential == null) {
            throw new UsernameNotFoundException("User with username: " + name + " not found.");
        }

        Collection<? extends GrantedAuthority> authorities =
                AuthorityUtils.createAuthorityList(ROLE_PREFIX + userCredential.getRole());

        return new User(userCredential.getLogin(), userCredential.getPass(), authorities);
    }
}
