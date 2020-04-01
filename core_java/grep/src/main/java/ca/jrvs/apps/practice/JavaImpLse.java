package ca.jrvs.apps.practice;

import java.util.Arrays;
import java.util.List;
import java.util.function.Consumer;
import java.util.stream.Collectors;
import java.util.stream.DoubleStream;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class JavaImpLse implements LambdaStreamExc {

    public Stream<String> createStrStream(String ... strings){
        return Arrays.stream(strings);
    }

    public Stream<String> toUpperCase(String ... strings){
        return createStrStream(strings).map(i->i.toString().toUpperCase());
    }

    public Stream<String> filter(Stream<String> stringStream, String pattern){
        return stringStream.filter(i->!i.contains(pattern));
    }
    public IntStream createIntStream(int[] arr){
        return Arrays.stream(arr);
    }

    public  <E> List<E> toList(Stream<E> stream){
        return stream.collect(Collectors.toList());
    }

    public List<Integer> toList(IntStream intStream){
        return intStream.boxed().collect(Collectors.toList());
    }
    public IntStream createIntStream(int start, int end){
        return IntStream.rangeClosed(start,end);
    }

   public DoubleStream squareRootIntStream(IntStream intStream){
        return intStream.asDoubleStream().map(i->Math.sqrt(i));

    }

    public IntStream getOdd(IntStream intStream){
        return intStream.filter(i->i%2==1);
    }
    public Consumer<String> getLambdaPrinter(String prefix, String suffix){
        Consumer<String> cons_str = str -> System.out.println(prefix+str+suffix);
        return cons_str;
    }
    public void printMessages(String[] messages, Consumer<String> printer){
        Arrays.stream(messages).forEach(printer);
    }
    public void printOdd(IntStream intStream, Consumer<String> printer){
        intStream.filter(i->i%2==1).mapToObj(i->i+"").forEach(printer);
    }

    public Stream<Integer> flatNestedInt(Stream<List<Integer>> ints){
        return ints.flatMap(i->i.stream()).map(i->i*i);
    }
    
}
