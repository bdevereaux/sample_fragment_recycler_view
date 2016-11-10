package com.blackboardtheory.qresume.objects;

/**
 * Created by bdevereaux3 on 11/7/16.
 */

public class Profile {



    private String name, school;

    public Profile(String name, String school) {
        this.name = name;
        this.school = school;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSchool() {
        return school;
    }

    public void setSchool(String school) {
        this.school = school;
    }

}
