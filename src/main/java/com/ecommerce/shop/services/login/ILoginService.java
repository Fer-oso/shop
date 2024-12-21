package com.ecommerce.shop.services.login;

import com.ecommerce.shop.models.DTO.users.CredentialsUser;
import com.ecommerce.shop.models.DTO.users.UserLoginResponseDTO;

public interface ILoginService {

    UserLoginResponseDTO loginWithUsernameAndPassword(CredentialsUser credentialsUser);
}
