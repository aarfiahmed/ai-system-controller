package com.app.nestyclaw.model;

import java.io.IOException;

public interface ModelExecutor {

    String callModel(String query) throws IOException, InterruptedException;

}


