package com.benchmarks.basic;

import org.openjdk.jmh.annotations.*;

import java.util.concurrent.TimeUnit;

@BenchmarkMode(Mode.Throughput)
@State(Scope.Thread)
@Warmup(iterations = 1,time = 2)
@Threads(2)
@Fork(2)
@Measurement(iterations = 2,time = 10)
@OutputTimeUnit(TimeUnit.MICROSECONDS)
public class BenchmarkTest {

    @Benchmark
    public void testTs() {
        System.currentTimeMillis();
    }
}
