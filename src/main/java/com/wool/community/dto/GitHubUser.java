package com.wool.community.dto;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class GitHubUser {
    private Long id;
    private String name;
    private String bio;
    private String avatar_url;
}
