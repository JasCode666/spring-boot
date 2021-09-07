package com.jas.form;

public interface FormConvert<S, T> {
    T convert(S s);

    T convert(S s, T t);
}
