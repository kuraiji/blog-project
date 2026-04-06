package com.kuraiji.blog.common.constants;

public final class ValidationConstants {
    private ValidationConstants() {}

    public static final int MAX_GENERIC_STRING_LENGTH = 255;
    public static final int MAX_HANDLE_LENGTH = 15;
    public static final int MAX_EMAIL_LENGTH = 254;
    public static final int MAX_PHASH_LENGTH = 72;
    public static final int MAX_PASSWORD_LENGTH = 30;
    public static final int MIN_PASSWORD_LENGTH = 8;
    public static final int MAX_BODY_LENGTH = 280;
    public static final int MAX_TITLE_LENGTH = 60;
    public static final int MAX_CONTENT_LENGTH = 140;
    public static final String PASSWORD_REGEX = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{8,}$";
}
