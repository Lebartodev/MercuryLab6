package com.lebartodev.mercurylab6.util;

import com.google.gson.Gson;
import com.lebartodev.mercurylab6.TaskModel;

/**
 * Created by Александр on 28.11.2016.
 */

public class SharedPrefer extends BaseSharedPrefer {
    public static void setTaskModel(TaskModel model) {
        Gson gson = new Gson();
        String res = gson.toJson(model);
        get().put("account", res);
    }

    public static TaskModel getTaskModel() {
        Gson gson = new Gson();

        TaskModel acc = gson.fromJson(get().get("account", ""), TaskModel.class);
        return acc;
    }

}
