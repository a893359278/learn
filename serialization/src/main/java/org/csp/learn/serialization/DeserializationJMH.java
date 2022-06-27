package org.csp.learn.serialization;

import com.alibaba.fastjson.JSON;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.openjdk.jmh.annotations.*;
import org.openjdk.jmh.results.format.ResultFormatType;
import org.openjdk.jmh.runner.Runner;
import org.openjdk.jmh.runner.RunnerException;
import org.openjdk.jmh.runner.options.Options;
import org.openjdk.jmh.runner.options.OptionsBuilder;
import org.openjdk.jmh.runner.options.TimeValue;

import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * fastjson、jackson 反序列化基准测试
 * @author shaoping.chen
 * @version 1.0.0
 * @date: Created in 2022/6/27 下午3:17
 */
@State(Scope.Benchmark)
@OutputTimeUnit(TimeUnit.MILLISECONDS)
public class DeserializationJMH {

    public static String listString = User.getListString(5000);
    public static ObjectMapper objectMapper = new ObjectMapper();
    public static JavaType javaType = objectMapper.getTypeFactory().constructCollectionType(List.class, User.class);

    @Benchmark
    public List<User> fastJson() {
        return JSON.parseArray(listString, User.class);
    }

    @Benchmark
    public List<User> jackson() throws JsonProcessingException {
        return objectMapper.readValue(listString, javaType);
    }

    public static void main(String[] args) throws RunnerException {
        String home = System.getProperty("user.home");
        Options options = new OptionsBuilder()
                .include(DeserializationJMH.class.getSimpleName())
                .resultFormat(ResultFormatType.JSON)
                .result(home + "/deserialization.json")
                .mode(Mode.AverageTime)
                .measurementIterations(3)
                .measurementTime(TimeValue.seconds(30L))
                .threads(1)
                .forks(1)
                .build();
        new Runner(options).run();
    }

}
