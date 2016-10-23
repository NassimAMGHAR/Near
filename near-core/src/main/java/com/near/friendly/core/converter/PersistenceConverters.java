package com.near.friendly.core.converter;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;
import java.sql.Date;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZonedDateTime;

/**
 *
 */
public final class PersistenceConverters {

    private PersistenceConverters(){}

    @Converter(autoApply = true)
    public static class LocalDateConverter implements AttributeConverter<LocalDate, Date> {

        @Override
        public java.sql.Date convertToDatabaseColumn(LocalDate date) {
            return date == null ? null : java.sql.Date.valueOf(date);
        }

        @Override
        public LocalDate convertToEntityAttribute(java.sql.Date date) {
            return date == null ? null : date.toLocalDate();
        }
    }

    @Converter(autoApply = true)
    public static class ZonedDateTimeConverter implements AttributeConverter<ZonedDateTime, java.util.Date> {

        @Override
        public java.util.Date convertToDatabaseColumn(ZonedDateTime zonedDateTime) {
            return DateConverters.ZonedDateTimeToDateConverter.INSTANCE.convert(zonedDateTime);
        }

        @Override
        public ZonedDateTime convertToEntityAttribute(java.util.Date date) {
            return DateConverters.DateToZonedDateTimeConverter.INSTANCE.convert(date);
        }
    }

    @Converter(autoApply = true)
    public static class LocalDateTimeConverter implements AttributeConverter<LocalDateTime, java.util.Date> {

        @Override
        public java.util.Date convertToDatabaseColumn(LocalDateTime localDateTime) {
            return DateConverters.LocalDateTimeToDateConverter.INSTANCE.convert(localDateTime);
        }

        @Override
        public LocalDateTime convertToEntityAttribute(java.util.Date date) {
            return DateConverters.DateToLocalDateTimeConverter.INSTANCE.convert(date);
        }
    }

}
