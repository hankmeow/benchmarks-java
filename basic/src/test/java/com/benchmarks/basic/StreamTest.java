package com.benchmarks.basic;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class StreamTest {
    @Test
    public void testParallelStream() {
        ArrayList<MatchResultItem> list = new ArrayList<>();
        list.add(new MatchResultItem(1, 8, 1));
        list.add(new MatchResultItem(2, 7, 1));
        list.add(new MatchResultItem(3, 5, 1));
        list.add(new MatchResultItem(4, 2, 1));
        list.add(new MatchResultItem(5, 10, 1));
        list.add(new MatchResultItem(6, 1, 1));
        list.parallelStream().filter(item-> Objects.equals(1, item.getStatus())).sorted(Comparator.comparing(MatchResultItem::getMatch_id)).forEachOrdered(item -> System.out.println(item.getMatch_id()));
        System.out.println("----");
        List<MatchResultItem> collect = list.stream().filter(item -> Objects.equals(0, item.getId())).collect(Collectors.toList());
        if (collect==null) {
            System.out.println("null");
        }else {
            System.out.println(collect.size());
        }
    }

    @Data
    @AllArgsConstructor
    public static class MatchResultItem {
        private Integer id;
        private Integer match_id;
        private Integer status;
    }
}
