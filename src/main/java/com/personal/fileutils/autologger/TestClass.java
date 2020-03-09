package com.personal.fileutils.autologger;

public class TestClass
{
    public static void main(String[] args)
    {
        System.out.println("This needs to be replaced with a logger INFO.");
        System.err.println("This needs to be replaced with a logger ERROR.");
        try
        {

        }
        catch (Exception e)
        {
            //This needs to be replaced with logger ERROR
            e.printStackTrace();
        }
    }
}
