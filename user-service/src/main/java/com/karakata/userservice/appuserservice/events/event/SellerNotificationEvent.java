package com.karakata.userservice.appuserservice.events.event;

import com.karakata.userservice.appuserservice.admin.model.Admin;
import com.karakata.userservice.appuserservice.seller.model.Seller;
import lombok.Getter;
import lombok.Setter;
import org.springframework.context.ApplicationEvent;

@Getter
@Setter
public class SellerNotificationEvent extends ApplicationEvent {
    private String applicationUrl;
    private Seller seller;
    public SellerNotificationEvent(String applicationUrl, Seller seller) {
        super(seller);
        this.applicationUrl = applicationUrl;
        this.seller = seller;
    }
}
