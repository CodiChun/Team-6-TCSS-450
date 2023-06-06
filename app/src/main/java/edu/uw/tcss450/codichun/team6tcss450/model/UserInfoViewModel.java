package edu.uw.tcss450.codichun.team6tcss450.model;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;
import com.auth0.jwt.JWT;
import com.auth0.jwt.interfaces.DecodedJWT;

public class UserInfoViewModel extends ViewModel {

    private final String mEmail;
    private final String mJwt;

    private int incomingRequests;

    private UserInfoViewModel(String email, String jwt) {
        mEmail = email;
        mJwt = jwt;
    }

    public String getEmail() {
        return mEmail;
    }

    public String getmJwt() {
        return mJwt;
    }

    public static class UserInfoViewModelFactory implements ViewModelProvider.Factory {
        private final String email;
        private final String jwt;

        public UserInfoViewModelFactory(String email, String jwt) {
            this.email = email;
            this.jwt = jwt;
        }

        @NonNull
        @Override
        public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
            if (modelClass == UserInfoViewModel.class) {
                return (T) new UserInfoViewModel(email, jwt);
            }
            throw new IllegalArgumentException(
                    "Argument must be: " + UserInfoViewModel.class);
        }
    }


    public int getUserId(){
        DecodedJWT djwt = JWT.decode(mJwt);
        int memberId = djwt.getClaim("memberid").asInt();
        return memberId;
    }

    public void setIncomingRequests(int incomingRequests) {
        this.incomingRequests = incomingRequests;
    }

    public int getIncomingRequests() {
        return this.incomingRequests;
    }


}


