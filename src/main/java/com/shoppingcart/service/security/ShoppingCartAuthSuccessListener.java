package com.shoppingcart.service.security;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationListener;
import org.springframework.security.authentication.event.InteractiveAuthenticationSuccessEvent;
import org.springframework.stereotype.Component;

/**
 * @author salm
 * Listener in case of success login.
 */
@Component
public class ShoppingCartAuthSuccessListener implements ApplicationListener<InteractiveAuthenticationSuccessEvent> {
    private final static Logger logger = LoggerFactory.getLogger(ShoppingCartAuthSuccessListener.class);

    @Override
    public void onApplicationEvent(InteractiveAuthenticationSuccessEvent event) {
        if(logger.isDebugEnabled()) {
            logger.debug("Login success");
        }
    }
}
