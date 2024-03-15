package com.karakata.userservice.appuserservice.events.event;

import com.karakata.userservice.appuserservice.admin.model.Admin;
import lombok.Getter;
import lombok.Setter;
import org.springframework.context.ApplicationEvent;
@Getter
@Setter
public class AdminApprovalEvent extends ApplicationEvent {
    private String applicationUrl;
    private Admin admin;
    public AdminApprovalEvent(String applicationUrl, Admin admin) {
        super(admin);
        this.applicationUrl = applicationUrl;
        this.admin = admin;
    }
}
