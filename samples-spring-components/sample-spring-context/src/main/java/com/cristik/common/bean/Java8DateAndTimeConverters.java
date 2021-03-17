package com.cristik.common.bean;

import ma.glasnost.orika.MappingContext;
import ma.glasnost.orika.converter.BidirectionalConverter;
import ma.glasnost.orika.converter.ConverterFactory;
import ma.glasnost.orika.converter.builtin.PassThroughConverter;
import ma.glasnost.orika.metadata.Type;

import java.sql.Time;
import java.sql.Timestamp;
import java.time.*;
import java.util.Calendar;
import java.util.Date;

/**
 * Desc: java8日期类型相互转换
 *  包含
 *  1 java8日期类型相互转换
 *  2 java8日期类型与long相互转换
 *  3 java8与java8之前版本日期相互转换
 *
 * @author wei1.sun
 * @date 2020/7/20 19:46
 * @see
 **/
abstract class Java8DateAndTimeConverters {

    private static final ZoneId DEFAULT_ZONEID =  ZoneId.of("Asia/Shanghai");

    private static final ZoneOffset DEFAULT_ZONEOFFSET = ZoneOffset.ofHours(8);

    public static void register(ConverterFactory converterFactory) {
        //java8日期之间转换
        converterFactory.registerConverter(new LocalDateTimeToLocalDateConverter());
        converterFactory.registerConverter(new LocalDateTimeToLocalTimeConverter());

        converterFactory.registerConverter(new InstantToLocalDateTimeConverter());
        converterFactory.registerConverter(new InstantToLocalDateConverter());
        converterFactory.registerConverter(new InstantToLocalTimeConverter());

        converterFactory.registerConverter(new ZonedDateTimeToLocalDateTimeConverter());
        converterFactory.registerConverter(new ZonedDateTimeToLocalDateConverter());
        converterFactory.registerConverter(new ZonedDateTimeToLocalTimeConverter());

        converterFactory.registerConverter(new OffsetDateTimeToLocalDateTimeConverter());
        converterFactory.registerConverter(new OffsetDateTimeToLocalDateConverter());
        converterFactory.registerConverter(new OffsetDateTimeToLocalTimeConverter());

        converterFactory.registerConverter(new OffsetTimeToLocalTimeConverter());
        //long与java8常见日期类型转换
        converterFactory.registerConverter(new LongToInstantConverter());
        converterFactory.registerConverter(new LongToLocalTimeConverter());
        converterFactory.registerConverter(new LongToLocalDateConverter());
        converterFactory.registerConverter(new LongToLocalDateTimeConverter());
        converterFactory.registerConverter(new LongToZonedDateTimeConverter());
        converterFactory.registerConverter(new LongToOffsetDateTimeConverter());
        converterFactory.registerConverter(new LongToOffsetTimeConverter());

        //java8与java8以前日期转换
        converterFactory.registerConverter(new LocalDateTimeToDateConverter());
        converterFactory.registerConverter(new LocalDateTimeToSqlDateConverter());
        converterFactory.registerConverter(new LocalDateTimeToTimeConverter());
        converterFactory.registerConverter(new LocalDateTimeToTimestampConverter());
        converterFactory.registerConverter(new LocalDateTimeToCalendarConverter());
        converterFactory.registerConverter(new LocalDateToDateConverter());
        converterFactory.registerConverter(new LocalDateToSqlDateConverter());
        converterFactory.registerConverter(new LocalDateToTimestampConverter());
        converterFactory.registerConverter(new LocalDateToTimestampConverter());
        converterFactory.registerConverter(new LocalDateToCalendarConverter());
        converterFactory.registerConverter(new LocalTimeToTimeConverter());
        converterFactory.registerConverter(new ZonedDateTimeToDateConverter());
        converterFactory.registerConverter(new ZonedDateTimeToSqlDateConverter());
        converterFactory.registerConverter(new ZonedDateTimeToTimeConverter());
        converterFactory.registerConverter(new ZonedDateTimeToTimestampConverter());
        converterFactory.registerConverter(new ZonedDateTimeToCalendarConverter());
        converterFactory.registerConverter(new OffsetDateTimeToDateConverter());
        converterFactory.registerConverter(new OffsetDateTimeToSqlDateConverter());
        converterFactory.registerConverter(new OffsetDateTimeToTimeConverter());
        converterFactory.registerConverter(new OffsetDateTimeToTimestampConverter());
        converterFactory.registerConverter(new OffsetDateTimeToCalendarConverter());
        converterFactory.registerConverter(new OffsetTimeToTimeConverter());

        //java8日期相关类型自己转自己
        converterFactory.registerConverter(new PassThroughConverter(LocalDate.class,LocalDateTime.class,LocalTime.class,
                OffsetTime.class,OffsetDateTime.class,ZonedDateTime.class,Instant.class,DayOfWeek.class,Duration.class,
                Period.class,Year.class,YearMonth.class,MonthDay.class
                ));
    }


    public static class LocalDateTimeToLocalDateConverter extends BidirectionalConverter<LocalDateTime, LocalDate> {

        @Override
        public LocalDate convertTo(LocalDateTime source, Type<LocalDate> destinationType, MappingContext mappingContext) {
            return source.toLocalDate();
        }

        @Override
        public LocalDateTime convertFrom(LocalDate source, Type<LocalDateTime> destinationType, MappingContext mappingContext) {
            return source.atStartOfDay(DEFAULT_ZONEID).toLocalDateTime();
        }
    }

    public static class LocalDateTimeToLocalTimeConverter extends BidirectionalConverter<LocalDateTime, LocalTime> {
        @Override
        public LocalTime convertTo(LocalDateTime source, Type<LocalTime> destinationType, MappingContext mappingContext) {
            return source.toLocalTime();
        }

        @Override
        public LocalDateTime convertFrom(LocalTime source, Type<LocalDateTime> destinationType, MappingContext mappingContext) {
            return Instant.ofEpochMilli(Time.valueOf(source).getTime()).atZone(DEFAULT_ZONEID).toLocalDateTime() ;
        }
    }

    public static class InstantToLocalDateTimeConverter extends BidirectionalConverter<Instant,LocalDateTime> {
        @Override
        public LocalDateTime convertTo(Instant source, Type<LocalDateTime> destinationType, MappingContext mappingContext) {
            return LocalDateTime.ofInstant(source,DEFAULT_ZONEID);
        }

        @Override
        public Instant convertFrom(LocalDateTime source, Type<Instant> destinationType, MappingContext mappingContext) {
            return source.atZone(DEFAULT_ZONEID).toInstant();
        }
    }

    public static class InstantToLocalDateConverter extends BidirectionalConverter<Instant,LocalDate> {
        @Override
        public LocalDate convertTo(Instant source, Type<LocalDate> destinationType, MappingContext mappingContext) {
            return source.atZone(DEFAULT_ZONEID).toLocalDate();
        }

        @Override
        public Instant convertFrom(LocalDate source, Type<Instant> destinationType, MappingContext mappingContext) {
            return source.atStartOfDay(DEFAULT_ZONEID).toInstant();
        }
    }

    public static class InstantToLocalTimeConverter extends BidirectionalConverter<Instant,LocalTime> {
        @Override
        public LocalTime convertTo(Instant source, Type<LocalTime> destinationType, MappingContext mappingContext) {
            return source.atZone(DEFAULT_ZONEID).toLocalTime();
        }

        @Override
        public Instant convertFrom(LocalTime source, Type<Instant> destinationType, MappingContext mappingContext) {
            return Instant.ofEpochMilli(Time.valueOf(source).getTime());
        }
    }

    public static class ZonedDateTimeToLocalDateTimeConverter extends BidirectionalConverter<ZonedDateTime,LocalDateTime> {
        @Override
        public LocalDateTime convertTo(ZonedDateTime source, Type<LocalDateTime> destinationType, MappingContext mappingContext) {
            return source.toLocalDateTime();
        }

        @Override
        public ZonedDateTime convertFrom(LocalDateTime source, Type<ZonedDateTime> destinationType, MappingContext mappingContext) {
            return source.atZone(DEFAULT_ZONEID);
        }
    }

    public static class ZonedDateTimeToLocalDateConverter extends BidirectionalConverter<ZonedDateTime,LocalDate> {
        @Override
        public LocalDate convertTo(ZonedDateTime source, Type<LocalDate> destinationType, MappingContext mappingContext) {
            return source.toLocalDate();
        }

        @Override
        public ZonedDateTime convertFrom(LocalDate source, Type<ZonedDateTime> destinationType, MappingContext mappingContext) {
            return source.atStartOfDay(DEFAULT_ZONEID);
        }
    }


    public static class ZonedDateTimeToLocalTimeConverter extends BidirectionalConverter <ZonedDateTime,LocalTime> {
        @Override
        public LocalTime convertTo(ZonedDateTime source, Type<LocalTime> destinationType, MappingContext mappingContext) {
            return source.toLocalTime();
        }

        @Override
        public ZonedDateTime convertFrom(LocalTime source, Type<ZonedDateTime> destinationType, MappingContext mappingContext) {
            return Instant.ofEpochMilli(Time.valueOf(source).getTime()).atZone(DEFAULT_ZONEID);
        }
    }

    public static class OffsetDateTimeToLocalDateTimeConverter extends BidirectionalConverter<OffsetDateTime,LocalDateTime> {
        @Override
        public LocalDateTime convertTo(OffsetDateTime source, Type<LocalDateTime> destinationType, MappingContext mappingContext) {
            return source.toLocalDateTime();
        }

        @Override
        public OffsetDateTime convertFrom(LocalDateTime source, Type<OffsetDateTime> destinationType, MappingContext mappingContext) {
            return source.atOffset(DEFAULT_ZONEOFFSET);
        }
    }

    public static class OffsetDateTimeToLocalDateConverter extends BidirectionalConverter<OffsetDateTime,LocalDate> {
        @Override
        public LocalDate convertTo(OffsetDateTime source, Type<LocalDate> destinationType, MappingContext mappingContext) {
            return source.toLocalDate();
        }

        @Override
        public OffsetDateTime convertFrom(LocalDate source, Type<OffsetDateTime> destinationType, MappingContext mappingContext) {
            return source.atStartOfDay(DEFAULT_ZONEID).toInstant().atOffset(DEFAULT_ZONEOFFSET);
        }
    }

    public static class OffsetDateTimeToLocalTimeConverter extends BidirectionalConverter<OffsetDateTime,LocalTime> {
        @Override
        public LocalTime convertTo(OffsetDateTime source, Type<LocalTime> destinationType, MappingContext mappingContext) {
            return source.toLocalTime();
        }

        @Override
        public OffsetDateTime convertFrom(LocalTime source, Type<OffsetDateTime> destinationType, MappingContext mappingContext) {
            return Instant.ofEpochMilli(Time.valueOf(source).getTime()).atOffset(DEFAULT_ZONEOFFSET);
        }
    }

    public static class LongToLocalDateTimeConverter extends BidirectionalConverter<Long,LocalDateTime> {
        @Override
        public LocalDateTime convertTo(Long source, Type<LocalDateTime> destinationType, MappingContext mappingContext) {
            return LocalDateTime.ofInstant(Instant.ofEpochMilli(source),DEFAULT_ZONEID);
        }

        @Override
        public Long convertFrom(LocalDateTime source, Type<Long> destinationType, MappingContext mappingContext) {
            return source.atZone(DEFAULT_ZONEID).toInstant().toEpochMilli();
        }
    }

    public static class LongToLocalDateConverter extends BidirectionalConverter<Long,LocalDate> {
        @Override
        public LocalDate convertTo(Long source, Type<LocalDate> destinationType, MappingContext mappingContext) {
            return LocalDate.ofEpochDay(source);
        }

        @Override
        public Long convertFrom(LocalDate source, Type<Long> destinationType, MappingContext mappingContext) {
            return source.atStartOfDay(DEFAULT_ZONEID).toInstant().toEpochMilli();
        }
    }

    public static class LongToInstantConverter extends BidirectionalConverter<Long,Instant> {
        @Override
        public Instant convertTo(Long source, Type<Instant> destinationType, MappingContext mappingContext) {
            return Instant.ofEpochMilli(source);
        }

        @Override
        public Long convertFrom(Instant source, Type<Long> destinationType, MappingContext mappingContext) {
            return source.toEpochMilli();
        }
    }

    public static class LongToZonedDateTimeConverter extends BidirectionalConverter<Long,ZonedDateTime> {
        @Override
        public ZonedDateTime convertTo(Long source, Type<ZonedDateTime> destinationType, MappingContext mappingContext) {
            return Instant.ofEpochMilli(source).atZone(DEFAULT_ZONEID);
        }

        @Override
        public Long convertFrom(ZonedDateTime source, Type<Long> destinationType, MappingContext mappingContext) {
            return source.toInstant().toEpochMilli();
        }
    }

    public static class LongToOffsetTimeConverter extends BidirectionalConverter<Long,OffsetTime> {
        @Override
        public OffsetTime convertTo(Long source, Type<OffsetTime> destinationType, MappingContext mappingContext) {
            return Instant.ofEpochMilli(source).atZone(DEFAULT_ZONEID).toLocalTime().atOffset(DEFAULT_ZONEOFFSET);
        }

        @Override
        public Long convertFrom(OffsetTime source, Type<Long> destinationType, MappingContext mappingContext) {
            return Time.valueOf(source.toLocalTime()).getTime();
        }
    }

    public static class LongToOffsetDateTimeConverter extends BidirectionalConverter<Long,OffsetDateTime> {
        @Override
        public OffsetDateTime convertTo(Long source, Type<OffsetDateTime> destinationType, MappingContext mappingContext) {
            return Instant.ofEpochMilli(source).atOffset(DEFAULT_ZONEOFFSET);
        }

        @Override
        public Long convertFrom(OffsetDateTime source, Type<Long> destinationType, MappingContext mappingContext) {
            return source.toInstant().toEpochMilli();
        }
    }

    public static class LongToLocalTimeConverter extends  BidirectionalConverter<Long,LocalTime> {
        @Override
        public LocalTime convertTo(Long source, Type<LocalTime> destinationType, MappingContext mappingContext) {
            return LocalDateTime.ofInstant(Instant.ofEpochMilli(source),DEFAULT_ZONEID).toLocalTime();
        }

        @Override
        public Long convertFrom(LocalTime source, Type<Long> destinationType, MappingContext mappingContext) {
            return Time.valueOf(source).getTime();
        }
    }

    public static class LocalDateTimeToDateConverter extends BidirectionalConverter<LocalDateTime,Date> {
        @Override
        public Date convertTo(LocalDateTime source, Type<Date> destinationType, MappingContext mappingContext) {
            return Date.from(source.atZone(DEFAULT_ZONEID).toInstant());
        }

        @Override
        public LocalDateTime convertFrom(Date source, Type<LocalDateTime> destinationType, MappingContext mappingContext) {
            return LocalDateTime.ofInstant(source.toInstant(),DEFAULT_ZONEID);
        }
    }

    public static class LocalDateTimeToSqlDateConverter extends BidirectionalConverter<LocalDateTime, java.sql.Date> {
        @Override
        public java.sql.Date convertTo(LocalDateTime source, Type<java.sql.Date> destinationType, MappingContext mappingContext) {
            return new java.sql.Date(source.atZone(DEFAULT_ZONEID).toInstant().toEpochMilli());
        }

        @Override
        public LocalDateTime convertFrom(java.sql.Date source, Type<LocalDateTime> destinationType, MappingContext mappingContext) {
            return source.toLocalDate().atStartOfDay(DEFAULT_ZONEID).toLocalDateTime();
        }
    }

    public static class LocalDateTimeToTimeConverter extends BidirectionalConverter<LocalDateTime,Time> {
        @Override
        public Time convertTo(LocalDateTime source, Type<Time> destinationType, MappingContext mappingContext) {
            return Time.valueOf(source.toLocalTime());
        }

        @Override
        public LocalDateTime convertFrom(Time source, Type<LocalDateTime> destinationType, MappingContext mappingContext) {
            return Instant.ofEpochMilli(source.getTime()).atZone(DEFAULT_ZONEID).toLocalDateTime();
        }
    }

    public static class LocalDateTimeToTimestampConverter extends BidirectionalConverter<LocalDateTime,Timestamp> {
        @Override
        public Timestamp convertTo(LocalDateTime source, Type<Timestamp> destinationType, MappingContext mappingContext) {
            return Timestamp.from(source.atZone(DEFAULT_ZONEID).toInstant());
        }

        @Override
        public LocalDateTime convertFrom(Timestamp source, Type<LocalDateTime> destinationType, MappingContext mappingContext) {
            return source.toLocalDateTime();
        }
    }

    public static class LocalDateTimeToCalendarConverter extends BidirectionalConverter<LocalDateTime,Calendar> {
        @Override
        public Calendar convertTo(LocalDateTime source, Type<Calendar> destinationType, MappingContext mappingContext) {
            Calendar instance = Calendar.getInstance();
            instance.setTimeInMillis(source.atZone(DEFAULT_ZONEID).toInstant().toEpochMilli());
            return instance;
        }

        @Override
        public LocalDateTime convertFrom(Calendar source, Type<LocalDateTime> destinationType, MappingContext mappingContext) {
            long timeInMillis = source.getTimeInMillis();
            return Instant.ofEpochMilli(timeInMillis).atZone(DEFAULT_ZONEID).toLocalDateTime();
        }
    }

    public static class LocalDateToDateConverter extends BidirectionalConverter<LocalDate,Date> {
        @Override
        public Date convertTo(LocalDate source, Type<Date> destinationType, MappingContext mappingContext) {
            return Date.from(source.atStartOfDay(DEFAULT_ZONEID).toInstant());
        }

        @Override
        public LocalDate convertFrom(Date source, Type<LocalDate> destinationType, MappingContext mappingContext) {
            return source.toInstant().atZone(DEFAULT_ZONEID).toLocalDate();
        }
    }

    public static class LocalDateToSqlDateConverter extends BidirectionalConverter<LocalDate, java.sql.Date> {
        @Override
        public java.sql.Date convertTo(LocalDate source, Type<java.sql.Date> destinationType, MappingContext mappingContext) {
            return java.sql.Date.valueOf(source);
        }

        @Override
        public LocalDate convertFrom(java.sql.Date source, Type<LocalDate> destinationType, MappingContext mappingContext) {
            return source.toLocalDate();
        }
    }

    public static class LocalDateToTimestampConverter extends BidirectionalConverter<LocalDate,Timestamp> {
        @Override
        public Timestamp convertTo(LocalDate source, Type<Timestamp> destinationType, MappingContext mappingContext) {
            return Timestamp.from(source.atStartOfDay(DEFAULT_ZONEID).toInstant());
        }

        @Override
        public LocalDate convertFrom(Timestamp source, Type<LocalDate> destinationType, MappingContext mappingContext) {
            return source.toLocalDateTime().toLocalDate();
        }
    }

    public static class LocalDateToCalendarConverter extends BidirectionalConverter<LocalDate,Calendar> {
        @Override
        public Calendar convertTo(LocalDate source, Type<Calendar> destinationType, MappingContext mappingContext) {
            Calendar calendar  = Calendar.getInstance();
            calendar.setTimeInMillis(source.atStartOfDay(DEFAULT_ZONEID).toInstant().toEpochMilli());
            return calendar;
        }

        @Override
        public LocalDate convertFrom(Calendar source, Type<LocalDate> destinationType, MappingContext mappingContext) {
            Long times = source.getTimeInMillis();
            return LocalDate.ofEpochDay(times);
        }
    }

    public static class LocalTimeToTimeConverter extends BidirectionalConverter<LocalTime,Time> {
        @Override
        public Time convertTo(LocalTime source, Type<Time> destinationType, MappingContext mappingContext) {
            return Time.valueOf(source);
        }

        @Override
        public LocalTime convertFrom(Time source, Type<LocalTime> destinationType, MappingContext mappingContext) {
            return source.toLocalTime();
        }
    }

    public static class ZonedDateTimeToDateConverter extends BidirectionalConverter<ZonedDateTime,Date> {
        @Override
        public Date convertTo(ZonedDateTime source, Type<Date> destinationType, MappingContext mappingContext) {
            return Date.from(source.toInstant());
        }

        @Override
        public ZonedDateTime convertFrom(Date source, Type<ZonedDateTime> destinationType, MappingContext mappingContext) {
            return source.toInstant().atZone(DEFAULT_ZONEID);
        }
    }

    public static class ZonedDateTimeToSqlDateConverter extends BidirectionalConverter<ZonedDateTime, java.sql.Date> {
        @Override
        public java.sql.Date convertTo(ZonedDateTime source, Type<java.sql.Date> destinationType, MappingContext mappingContext) {
            return new java.sql.Date(source.toInstant().toEpochMilli());
        }

        @Override
        public ZonedDateTime convertFrom(java.sql.Date source, Type<ZonedDateTime> destinationType, MappingContext mappingContext) {
            return source.toInstant().atZone(DEFAULT_ZONEID);
        }
    }

    public static class ZonedDateTimeToTimeConverter extends BidirectionalConverter<ZonedDateTime,Time> {
        @Override
        public Time convertTo(ZonedDateTime source, Type<Time> destinationType, MappingContext mappingContext) {
            return Time.valueOf(source.toLocalTime());
        }

        @Override
        public ZonedDateTime convertFrom(Time source, Type<ZonedDateTime> destinationType, MappingContext mappingContext) {
            return source.toInstant().atZone(DEFAULT_ZONEID);
        }
    }

    public static class ZonedDateTimeToTimestampConverter extends BidirectionalConverter<ZonedDateTime,Timestamp> {
        @Override
        public Timestamp convertTo(ZonedDateTime source, Type<Timestamp> destinationType, MappingContext mappingContext) {
            return Timestamp.from(source.toInstant());
        }

        @Override
        public ZonedDateTime convertFrom(Timestamp source, Type<ZonedDateTime> destinationType, MappingContext mappingContext) {
            return ZonedDateTime.ofInstant(source.toInstant(),DEFAULT_ZONEID);
        }
    }

    public static class ZonedDateTimeToCalendarConverter extends BidirectionalConverter<ZonedDateTime,Calendar> {
        @Override
        public Calendar convertTo(ZonedDateTime source, Type<Calendar> destinationType, MappingContext mappingContext) {
            Calendar calendar  = Calendar.getInstance();
            calendar.setTimeInMillis(source.toInstant().toEpochMilli());
            return calendar;
        }

        @Override
        public ZonedDateTime convertFrom(Calendar source, Type<ZonedDateTime> destinationType, MappingContext mappingContext) {
            return ZonedDateTime.ofInstant(source.toInstant(),DEFAULT_ZONEID);
        }
    }

    public static class OffsetDateTimeToDateConverter extends BidirectionalConverter<OffsetDateTime,Date>{
        @Override
        public Date convertTo(OffsetDateTime source, Type<Date> destinationType, MappingContext mappingContext) {
            return Date.from(source.toInstant());
        }

        @Override
        public OffsetDateTime convertFrom(Date source, Type<OffsetDateTime> destinationType, MappingContext mappingContext) {
            return source.toInstant().atOffset(DEFAULT_ZONEOFFSET);
        }
    }

    public static class OffsetDateTimeToSqlDateConverter extends BidirectionalConverter<OffsetDateTime, java.sql.Date>{
        @Override
        public java.sql.Date convertTo(OffsetDateTime source, Type<java.sql.Date> destinationType, MappingContext mappingContext) {
            return java.sql.Date.valueOf(source.toLocalDate());
        }

        @Override
        public OffsetDateTime convertFrom(java.sql.Date source, Type<OffsetDateTime> destinationType, MappingContext mappingContext) {
            return source.toInstant().atOffset(DEFAULT_ZONEOFFSET);
        }
    }

    public static class OffsetDateTimeToTimeConverter extends BidirectionalConverter<OffsetDateTime,Time> {

        @Override
        public Time convertTo(OffsetDateTime source, Type<Time> destinationType, MappingContext mappingContext) {
            return Time.valueOf(source.toLocalTime());
        }

        @Override
        public OffsetDateTime convertFrom(Time source, Type<OffsetDateTime> destinationType, MappingContext mappingContext) {
            return source.toInstant().atOffset(DEFAULT_ZONEOFFSET);
        }
    }

    public static class OffsetDateTimeToCalendarConverter extends BidirectionalConverter<OffsetDateTime,Calendar>{
        @Override
        public Calendar convertTo(OffsetDateTime source, Type<Calendar> destinationType, MappingContext mappingContext) {
            Calendar calendar  = Calendar.getInstance();
            calendar.setTimeInMillis(source.toInstant().toEpochMilli());
            return calendar;
        }

        @Override
        public OffsetDateTime convertFrom(Calendar source, Type<OffsetDateTime> destinationType, MappingContext mappingContext) {
            return source.getTime().toInstant().atOffset(DEFAULT_ZONEOFFSET);
        }
    }

    public static class OffsetDateTimeToTimestampConverter extends BidirectionalConverter<OffsetDateTime,Timestamp> {
        @Override
        public Timestamp convertTo(OffsetDateTime source, Type<Timestamp> destinationType, MappingContext mappingContext) {
            return Timestamp.from(source.toInstant());
        }

        @Override
        public OffsetDateTime convertFrom(Timestamp source, Type<OffsetDateTime> destinationType, MappingContext mappingContext) {
            return source.toInstant().atOffset(DEFAULT_ZONEOFFSET);
        }

    }

    public static class OffsetTimeToLocalTimeConverter extends BidirectionalConverter<OffsetTime,LocalTime> {
        @Override
        public LocalTime convertTo(OffsetTime source, Type<LocalTime> destinationType, MappingContext mappingContext) {
            return source.toLocalTime();
        }

        @Override
        public OffsetTime convertFrom(LocalTime source, Type<OffsetTime> destinationType, MappingContext mappingContext) {
            return source.atOffset(DEFAULT_ZONEOFFSET);
        }
    }

    public static class OffsetTimeToTimeConverter extends BidirectionalConverter<OffsetTime,Time> {
        @Override
        public Time convertTo(OffsetTime source, Type<Time> destinationType, MappingContext mappingContext) {
            return Time.valueOf(source.toLocalTime());
        }

        @Override
        public OffsetTime convertFrom(Time source, Type<OffsetTime> destinationType, MappingContext mappingContext) {
            return source.toLocalTime().atOffset(DEFAULT_ZONEOFFSET);
        }
    }

}