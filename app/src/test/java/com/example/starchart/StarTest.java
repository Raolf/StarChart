package com.example.starchart;

import com.example.starchart.Model.ApiResponse.Star;

import org.junit.Test;
import static org.junit.Assert.*;

public class StarTest {
    //Implementation of tests for the Star class, as an example.
    @Test
    public void starGetName(){

        String name = "test";
        Star star = new Star("test",(float) 1.0,2);
        assertEquals(name,star.getName());
    }
    @Test
    public void starSetName(){

        String name = "test";
        Star star = new Star("" , (float) 1.0,2);
        star.setName(name);
        assertEquals(name,star.getName());
    }
    @Test
    public void starGetId(){

        int id = 2;
        Star star = new Star("test",(float) 1.0,2);
        assertEquals(id,star.getPageId());
    }
    @Test
    public void starSetId(){

        int id = 0;
        Star star = new Star("" ,(float) 1.0,2);
        star.setPageId(id);
        assertEquals(id,star.getPageId());
    }
    @Test
    public void starGetColor(){

        float color = (float) 1.0;
        Star star = new Star("test",(float) 1.0,2);
        assertEquals(color,(float) star.getColor(),(float) 0);
    }
    @Test
    public void starSetColor(){

        float color = (float) 1.0;
        Star star = new Star("" ,(float) 1.0,2);
        star.setColor(color);
        assertEquals(color,(float) star.getColor(),(float) 0);
    }
}
