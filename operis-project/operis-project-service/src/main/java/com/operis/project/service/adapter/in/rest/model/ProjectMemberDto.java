package com.operis.project.service.adapter.in.rest.model;

import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
public record ProjectMemberDto(
        String userEmail,
        String firstName,
        String lastName
) {

}
