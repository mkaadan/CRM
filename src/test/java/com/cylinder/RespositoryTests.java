package com.cylinder;

import org.springframework.boot.test.autoconfigure.orm.jpa.AutoConfigureTestDatabase;
import org.junit.runner.RunWith;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.autoconfigure.jdbc.EmbeddedDatabaseConnection;
import org.springframework.test.context.junit4.SpringRunner;
import org.junit.Ignore;


@RunWith(SpringRunner.class)
@DataJpaTest
@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.H2)
public abstract class RespositoryTests {}
