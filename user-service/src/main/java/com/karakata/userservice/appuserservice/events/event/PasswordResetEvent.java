package com.karakata.userservice.appuserservice.events.event;


import com.karakata.userservice.appuserservice.appuser.model.User;
import lombok.Getter;
import lombok.Setter;
import org.springframework.context.ApplicationEvent;

@Getter
@Setter
public class PasswordResetEvent extends ApplicationEvent {
    private String applicationUrl;
    private User user;
    public PasswordResetEvent(String applicationUrl,User user) {
        super(user);
        this.applicationUrl=applicationUrl;
        this.user=user;
    }
}
