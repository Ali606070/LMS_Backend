package com.library.task.pagenation;

import org.springframework.data.domain.Page;

import java.util.List;

public class PaginationUtils {
    public static <T> List<T> convertPageToList(Page<T> page) {
        return page.getContent();
    }
}
