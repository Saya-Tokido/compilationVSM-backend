package com.ljz.compilationVSM.common.utils;

import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

public class WebUtils
{
    public static String renderString(HttpServletResponse response, String string) {
        try
        {
            response.setStatus(200);
            response.setContentType("application/json");
            response.setCharacterEncoding("utf-8");
            response.getWriter().print(string);
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
        return null;
    }
}