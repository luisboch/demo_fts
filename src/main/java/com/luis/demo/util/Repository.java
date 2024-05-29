package com.luis.demo.util;

import java.util.HashMap;

import static com.luis.demo.constants.Repository.LIMIT;
import static com.luis.demo.constants.Repository.OFFSET;

public class Repository {

    public static class Parameters extends HashMap<String, Object> {
    }

    public static class PaginationParameters extends Parameters {
        public PaginationParameters(
                Integer page,
                Integer size
        ) {

            if (size == null) {
                size = 10;
            }

            if (page == null) {
                page = 1;
            }

            put(LIMIT, size);
            put(OFFSET, (page * size) - size);
        }
    }
}
