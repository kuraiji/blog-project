package com.kuraiji.blog.security;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ExtractedUriInfo {

    private String restType;

    private String endpoint;

    private String pathVariable;
}
