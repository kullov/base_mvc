package org.ttnga.common.utils;

import org.apache.commons.lang3.StringUtils;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.ttnga.common.http.PagingRequest;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Calendar;
import java.util.Date;

public class AppUtils {

    private static final String DATEFORMAT = "yyyy-MM-dd";

    public static PageRequest getPaging(PagingRequest request) {
        PageRequest pageable = null;
        if (StringUtils.isEmpty(request.getSortBy())) {
            pageable = PageRequest.of(request.getPage() == null ? 0 : request.getPage(),
                    request.getSize() == null ? 20 : request.getSize());
        } else {
            pageable = PageRequest.of(request.getPage() == null ? 0 : request.getPage(),
                    request.getSize() == null ? 20 : request.getSize(),
                    request.isSortDesc() ? Sort.Direction.DESC : Sort.Direction.ASC,
                            request.getSortBy());
        }
        return pageable;
    }

    /**
     * The function check isValid Format of inputValue by FORMAT
     * @param format {dd/MM/yyyy,yyyy-MM-dd,yyyyMMdd,vv..}
     * @param inputValue
     * @return True, if input match with format
     */
    public static boolean isValidFormat(String format, String inputValue) {
        LocalDateTime ldt = null;
        DateTimeFormatter fomatter = DateTimeFormatter.ofPattern(format);

        try {
            ldt = LocalDateTime.parse(inputValue, fomatter);
            String result = ldt.format(fomatter);
            return result.equals(inputValue);
        } catch (DateTimeParseException e) {
            try {
                LocalDate ld = LocalDate.parse(inputValue, fomatter);
                String result = ld.format(fomatter);
                return result.equals(inputValue);
            } catch (DateTimeParseException exp) {
                try {
                    LocalTime lt = LocalTime.parse(inputValue, fomatter);
                    String result = lt.format(fomatter);
                    return result.equals(inputValue);
                } catch (DateTimeParseException e2) {
                    // to do
                }
            }
        }

        return false;
    }

    /**
     * The function check Is valid date Range
     * @param dateFromValue
     * @param dateToValue
     * @return True, if dateFromValue <= dateToValue
     */
    public static boolean isValidDateRange(String dateFromValue, String dateToValue) {
        DateTimeFormatter formater = DateTimeFormatter.ofPattern(DATEFORMAT);
        LocalDate dateFrom = LocalDate.parse(dateFromValue, formater);
        LocalDate dateTo = LocalDate.parse(dateToValue, formater);
        return dateFrom.isBefore(dateTo) || dateFrom.isEqual(dateTo);
    }

    /**
     * The function check date must be before or equal TODAY
     * @param dateValue
     * @return True, if dateValue <= TODAY
     */
    public static boolean isValidDateRange(String dateValue) {
        DateTimeFormatter formater = DateTimeFormatter.ofPattern(DATEFORMAT);
        LocalDate date = LocalDate.parse(dateValue, formater);
        LocalDate today = LocalDate.now();
        return date.isBefore(today) || date.isEqual(today);
    }

    /**
     * The function check range date filter must be within one year
     * @param dateFromValue
     * @param dateToValue
     * @return True , if range date within one year
     * @return Else return False
     * @throws ParseException
     */
    public static boolean isValidDateRangeOneYear(String dateFromValue, String dateToValue) throws ParseException {
        DateFormat formater = new SimpleDateFormat(DATEFORMAT);
        Date dateFrom = formater.parse(dateFromValue);
        Date dateTo = formater.parse(dateToValue);

        Calendar instance = Calendar.getInstance();
        instance.setTime(dateTo);
        instance.add(Calendar.YEAR, -1);

        Date dateCheck = instance.getTime();
        return dateFrom.after(dateCheck) || dateFrom.equals(dateCheck);
    }

}
