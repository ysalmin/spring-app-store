package com.shoppingcart.service.security;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationListener;
import org.springframework.security.authentication.event.AuthenticationFailureBadCredentialsEvent;
import org.springframework.stereotype.Component;

/**
 * @author salm
 * Listener in case of login fail.
 */
@Component
public class ShoppingCartAuthFailListener implements ApplicationListener<AuthenticationFailureBadCredentialsEvent> {

 private final static Logger logger = LoggerFactory.getLogger(ShoppingCartAuthFailListener.class);
    @Override
    public void onApplicationEvent(AuthenticationFailureBadCredentialsEvent event) {
        if(logger.isDebugEnabled()) {
            logger.debug("Login failed");
        }
    }
}
