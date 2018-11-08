Feature: Simple

    Background:
        Given an empty announcement deployed on localhost:8080
        And an announcement empty is created

    Scenario: Create an annoucement
        Given an announcement is created by Lucas
            #And the object is a bike TODO: add field object to announcement
            And the id of Lucas is 38
            And the departure is at Sophia
            And started 2018-11-12
            And the arrival is at Paris
            And finished at 2018-12-24
            And the announcement type is GOOD
        Given an announcement is created by Hope
            And the id of Hope is 53
            And the departure is at Sophia
            And started 2018-11-12
            And the arrival is at Paris
            And finished at 2018-12-24
            And the announcement type is COURSE
        When the system finds a matching route between Lucas and Hope
        Then the announcement of Hope (with id 53) is changed to course
            And the course reffer to the announcement of Lucas (with id 38)

