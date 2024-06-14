package com.operis.dto;

import lombok.Data;

import java.util.List;

@Data
public class ProjectDto {
    private Long id;
    private String name;
    private String description;
    private String ownerId;
    private List<String> memberIds;
    private boolean archived;
    private List<TaskDto> tasks; // Ajout de la liste des TaskDto
}
