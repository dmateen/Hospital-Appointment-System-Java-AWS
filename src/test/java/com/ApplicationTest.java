package com;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ApplicationTest {
    @Test
    void TestApplicaion()
    {
        Application app = new Application();
        assertNotNull(app.getResources());

    }
}