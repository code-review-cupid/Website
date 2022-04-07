package com.jannetta.controller;

import com.jannetta.util.ViewUtil;
import spark.Request;
import spark.Response;
import spark.Route;

import java.util.HashMap;


public class IndexController {
    /**
     *
     */
    public static Route serveIndexPage = (Request request, Response response) -> {
        HashMap<String, Object> model = new HashMap<>();
        return ViewUtil.render(request, model, "/velocity/index.vm");

    };


}
