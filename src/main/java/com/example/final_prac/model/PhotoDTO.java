package com.example.final_prac.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PhotoDTO {
    private Long id;
    private String photoUrl;
    private String description;
}
