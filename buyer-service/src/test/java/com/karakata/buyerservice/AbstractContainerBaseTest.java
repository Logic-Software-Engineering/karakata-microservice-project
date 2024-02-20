package com.logicgate.buyerservice;

import org.testcontainers.containers.PostgreSQLContainer;

public abstract class AbstractContainerBaseTest {
    private static final PostgreSQLContainer POSTGRES_SQL_CONTAINER;

    static {
        POSTGRES_SQL_CONTAINER=new PostgreSQLContainer<>("postgres:latest");
        POSTGRES_SQL_CONTAINER.start();
    }
}
