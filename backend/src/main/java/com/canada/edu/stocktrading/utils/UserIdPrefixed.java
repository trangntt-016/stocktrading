package com.canada.edu.stocktrading.service.utils;

import org.hibernate.HibernateException;
import org.hibernate.MappingException;
import org.hibernate.engine.spi.SharedSessionContractImplementor;
import org.hibernate.id.enhanced.SequenceStyleGenerator;
import org.hibernate.internal.util.config.ConfigurationHelper;
import org.hibernate.service.ServiceRegistry;
import org.hibernate.type.LongType;
import org.hibernate.type.Type;

import java.io.Serializable;
import java.util.Properties;

public class UserIdPrefixed extends SequenceStyleGenerator {
    public static final String CODE_NUMBER_SEPARATOR_PARAMETER = "codeNumberSeparator";

    public static final String CODE_NUMBER_SEPARATOR_DEFAULT = "_";

    public static final String NUMBER_FORMAT_PARAMETER = "numberFormat";

    public static final String NUMBER_FORMAT_DEFAULT = "%03d";

    private String format;

    @Override
    public Serializable generate(SharedSessionContractImplementor session, Object object) throws HibernateException {
        return String.format(format,'U',super.generate(session,object));
    }

    @Override
    public void configure(Type type, Properties params, ServiceRegistry serviceRegistry) throws MappingException {
        super.configure(LongType.INSTANCE, params, serviceRegistry);
        String codeNumberSeparator = ConfigurationHelper.getString(
                CODE_NUMBER_SEPARATOR_PARAMETER,params,CODE_NUMBER_SEPARATOR_DEFAULT);
        String numberFormat = ConfigurationHelper.getString(
                NUMBER_FORMAT_PARAMETER,params,NUMBER_FORMAT_DEFAULT).replace("%","%2$");
        this.format = "%1$s"+codeNumberSeparator + numberFormat;

    }
}
