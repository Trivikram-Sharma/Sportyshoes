package com.sportyshoes.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.stereotype.Component;

@Component
public class DateTimeUtilities {
	public java.sql.Date getSQLDate(Date date) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		return java.sql.Date.valueOf(sdf.format(date));
	}

}
