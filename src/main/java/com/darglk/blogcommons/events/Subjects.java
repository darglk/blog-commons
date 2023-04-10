package com.darglk.blogcommons.events;

public final class Subjects {
    private Subjects() { }
    public static final String USER_CREATED_QUEUE = "user_created_queue";
    public static final String USER_DELETED_QUEUE = "user_deleted_queue";
    public static final String USER_PASSWORD_CHANGED_QUEUE = "user_changed_password_queue";
    public static final String USER_PASSWORD_RESET_QUEUE = "user_reset_password_queue";
    public static final String USER_EMAIL_CHANGED_QUEUE = "user_changed_password_queue";
    public static final String USER_ACCOUNT_ACTIVATION_TOKEN_QUEUE = "user_account_activation_token_queue";
    public static final String USER_ACCOUNT_ACTIVATION_DONE_QUEUE = "user_account_activation_done_queue";
    public static final String POST_CREATED_QUEUE = "post_created_queue";
    public static final String COMMENT_CREATED_QUEUE = "comment_created_queue";
    public static final String USER_ADDED_TO_FAV_QUEUE = "user_added_to_fav_queue";
}
