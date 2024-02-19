package com.karakata.userservice.appuserservice.events.event;


import com.karakata.userservice.appuserservice.appuser.model.User;
import lombok.Getter;
import lombok.Setter;
import org.springframework.context.ApplicationEvent;

@Getter
@Setter
public class RegistrationEvent extends ApplicationEvent {
    private String applicationUrl;
    private User user;
    public RegistrationEvent(User user,String applicationUrl) {
        super(user);
        this.applicationUrl=applicationUrl;
        this.user=user;
    }
}
