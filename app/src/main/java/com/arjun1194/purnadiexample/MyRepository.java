package com.arjun1194.purnadiexample;

public class MyRepository {

    MyService myService;

    public MyRepository(MyService myService) {
        this.myService = myService;
    }

    public String getMessage() {
        return myService.getGreeting();
    }
}
