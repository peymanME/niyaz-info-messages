package com.niyaz.info.messages.component;

import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.context.support.MessageSourceAccessor;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;

/**
 * @author : Peyman Ekhtiar
 * @date : 4/11/2020
 */
@Component
public class MessageComponent {

    private final MessageSource messageSource;

    private MessageSourceAccessor accessor;

    public MessageComponent(MessageSource messageSource) {
        this.messageSource = messageSource;
    }

    @PostConstruct
    private void init() {
        accessor = new MessageSourceAccessor(messageSource, LocaleContextHolder.getLocale());
    }

    /**
     * @param code
     * @param locale
     * @return String
     */
    public String get(String code, Locale locale) {
        return accessor.getMessage(code, locale);
    }

    /**
     *
     * @param code
     * @param args
     * @param locale
     * @return String
     */
    public String get(String code, Object[] args, Locale locale) {
        return accessor.getMessage(code, args, locale);
    }

    /**
     *
     * @param code
     * @param locale
     * @param codeArgs
     * @return String
     */
    public String get(String code, Locale locale, Object... codeArgs) {
        if (codeArgs.length == 0){
            return get(code, locale);
        }
        List<Object> objects = new ArrayList<>();
        Arrays.stream(codeArgs).forEach(codeArg -> objects.add(get((String)codeArg, locale)));

        if (objects.size()>0){
            return get(code, objects.toArray(new Object[0]), locale);
        }
        return code;
    }
}
