package com.niyaz.info.messages.component;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.context.support.MessageSourceAccessor;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

/*
 * @author : Peyman Ekhtiar
 * @date : 4/11/2020
 * @project : niyaz.info.ui.front
 */
@Component
public class MessageComponent {

    @Autowired private MessageSource messageSource;

    private MessageSourceAccessor accessor;

    @PostConstruct
    private void init() {
        accessor = new MessageSourceAccessor(messageSource, LocaleContextHolder.getLocale());
    }

    public String get(String code, Locale locale) {
        return accessor.getMessage(code, locale);
    }
    public String get(String code, Object[] args, Locale locale) {
        return accessor.getMessage(code, args, locale);
    }
    public String get(String code, Locale locale, Object... codeArgs) {
        if (codeArgs.length == 0){
            return get(code, locale);
        }
        List<Object> objects = new ArrayList<>();
        for (Object codeArge : codeArgs) {
            objects.add(get((String)codeArge, locale));
        }
        if (objects.size()>0){
            return get(code, objects.toArray(new Object[0]), locale);
        }
        return code;
    }
}
