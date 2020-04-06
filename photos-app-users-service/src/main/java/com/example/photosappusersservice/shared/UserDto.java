package com.example.photosappusersservice.shared;

import com.example.photosappusersservice.ui.model.AlbumResponseModel;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class UserDto implements Serializable {

    private static final long serialVersionUID = -953297098295050686L;

    private String firstName;
    private String lastName;
    private String email;
    private String password;
    private String encryptedPassword;
    private String userId;
    private List<AlbumResponseModel> albums;
}
