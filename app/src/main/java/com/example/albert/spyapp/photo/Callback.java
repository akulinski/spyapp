package com.example.albert.spyapp.photo;

import java.util.LinkedList;

public interface Callback {
    void onSuccess(LinkedList<String> links);
    void onFail(String msg);
}
