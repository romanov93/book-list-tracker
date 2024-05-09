package ru.romanov.booktracker.repository.utils;

import lombok.experimental.FieldDefaults;
import lombok.experimental.UtilityClass;

@UtilityClass
@FieldDefaults(makeFinal = true)
public class TableColumnAliasStorage {

    public static String BOOK_ID = "book_id";
    public static String BOOK_TITLE = "book_title";
    public static String BOOK_AUTHOR = "book_author";
    public static String BOOK_DESCRIPTION = "book_description";
    public static String BOOK_STATUS = "book_read_status";
    public static String BOOK_DATE_TO_READ = "book_date_to_read";
    public static String USER_ID = "user_id";
    public static String USER_NAME = "user_name";
    public static String USER_USERNAME = "user_username";
    public static String USER_PASSWORD = "user_password";
    public static String USER_ROLE = "user_role";

}