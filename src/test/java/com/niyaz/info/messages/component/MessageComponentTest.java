package com.niyaz.info.messages.component;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.AnnotationConfigContextLoader;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;

import java.util.Locale;


/**
 * Created by:
 *
 * @author Peyman Mostafai Ekhtiar
 * @project niyaz-info-messages
 * @date 2022-05-01
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(loader= AnnotationConfigContextLoader.class)
public class MessageComponentTest {

    @Configuration
    public static class ContextConfiguration {
        @Bean
        public ReloadableResourceBundleMessageSource messageSource() {
            ReloadableResourceBundleMessageSource source = new ReloadableResourceBundleMessageSource();
            source.setBasenames("classpath:messages");
            source.setDefaultEncoding("UTF-8");
            return source;
        }
        @Bean
        public LocalValidatorFactoryBean getValidator() {
            LocalValidatorFactoryBean bean = new LocalValidatorFactoryBean();
            bean.setValidationMessageSource(messageSource());
            return bean;
        }
        @Bean
        public MessageComponent messageComponent(MessageSource messageSource){
            return new MessageComponent(messageSource);
        }

    }

    @Autowired
    private MessageComponent messageComponent;

    private enum LOCALIZATION{
        FA ("fa", "IR"),
        US ("en", "US"),
        PL ("pl", "PL");

        private String language;
        private String region;

        LOCALIZATION (String _language, String _region){
            language = _language;
            region = _region;
        }

    }

    private Locale getLocale(LOCALIZATION localization){
        return new Locale(localization.language, localization.region);
    }


    @Test
    public void check_simple_message_EN() {
        String message = messageComponent.get("application.test.simple", getLocale(LOCALIZATION.US));
        Assert.assertEquals("This is simple", message);
    }
    @Test
    public void check_simple_message_IR() {
        String message = messageComponent.get("application.test.simple",getLocale(LOCALIZATION.FA));
        Assert.assertEquals("این ساده است", message);
    }
    @Test
    public void check_simple_message_PL() {
        String message = messageComponent.get("application.test.simple", getLocale(LOCALIZATION.PL));
        Assert.assertEquals("To jest proste", message);
    }

    @Test
    public void check_message_with_arg_message_EN() {
        Object[] args ={"an example"};
        String message = messageComponent.get("application.test.with.arg", args, getLocale(LOCALIZATION.US));
        Assert.assertEquals("Please let me an example", message);
    }
    @Test
    public void check_message_with_arg_message_PL() {
        Object[] args ={"przykład"};
        String message = messageComponent.get("application.test.with.arg", args, getLocale(LOCALIZATION.PL));
        Assert.assertEquals("Proszę dać mi przykład", message);
    }
    @Test
    public void check_message_with_arg_message_FA() {
        Object[] args ={"یک مثال"};
        String message = messageComponent.get("application.test.with.arg", args, getLocale(LOCALIZATION.FA));
        Assert.assertEquals("اجازه دهید یک مثال بزنم", message);
    }

}