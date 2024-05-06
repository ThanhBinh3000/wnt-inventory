package vn.com.gsoft.inventory.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.i18n.LocaleContextHolder;

@Configuration
public class MessageTemplate {
    @Autowired
    private MessageSource messageSource;

    public String message(String key, String... value) {
        return messageSource.getMessage(key, value, LocaleContextHolder.getLocale());
    }
}
