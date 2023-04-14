package com.example.whitecommunity.service;

import static org.junit.Assert.assertThat;

import java.util.List;

import static org.hamcrest.Matchers.is;

import org.junit.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class UserRoleServiceTest {

    private UserRoleService userRoleService = new UserRoleService();

    @Test
    public void getRolesTest() {
        List<String> list = userRoleService.getRoles(1);
        assertThat(list.size(), is(1));
        assertThat(list.get(0), is("admin"));
    }

}
