package com.kuraiji.blog.common.permissions;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum PermissionName {
    EDIT_USER("edit_user"),
    DELETE_USER("delete_user"),
    GET_USER("get_user"),
    EDIT_ROLE("edit_role"),
    DELETE_ROLE("delete_role"),
    GET_ROLE("get_role"),
    EDIT_PERMISSION("edit_permission"),
    DELETE_PERMISSION("delete_permission"),
    GET_PERMISSION("get_permission"),
    ADMIN_PANEL_ACCESS("admin_panel_access"),
    EDIT_POST("delete_post"),
    DELETE_POST("get_post"),
    EDIT_COMMENT("delete_comment"),
    DELETE_COMMENT("get_comment"),
    CREATE_USER("create_user"),
    CREATE_ROLE("create_role"),
    CREATE_PERMISSION("create_permission"),
    CREATE_POST("create_post"),
    CREATE_COMMENT("create_comment"),
    GET_POST("get_post"),
    GET_COMMENT("get_comment"),
    UNDEFINED("undefined");

    private final String name;
}
