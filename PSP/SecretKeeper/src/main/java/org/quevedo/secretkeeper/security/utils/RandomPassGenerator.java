package org.quevedo.secretkeeper.security.utils;

import javax.enterprise.inject.Produces;
import javax.inject.Named;
import java.security.SecureRandom;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class RandomPassGenerator {
    public String generateSecureRandomPassword() {
        Stream<Character> pwdStream = Stream.concat(
                getRandomSpecialChars(8),
                getRandomAlphabets(8)
        );
        List<Character> charList = pwdStream.collect(Collectors.toList());
        Collections.shuffle(charList);
        return charList.stream()
                .collect(StringBuilder::new, StringBuilder::append, StringBuilder::append)
                .toString();
    }

    private Stream<Character> getRandomSpecialChars(int count) {
        Random random = new SecureRandom();
        IntStream specialChars = random.ints(count, 33, 45);
        return specialChars.mapToObj(data -> (char) data);
    }

    private Stream<Character> getRandomAlphabets(int count) {
        Random random = new SecureRandom();
        IntStream chars = random.ints(count, 65, 90);
        return chars.mapToObj(data -> (char) data);
    }

    @Produces
    @Named(SecurityConsts.RANDOM_PASS_GENERATOR)
    public RandomPassGenerator randomPassGenerator() {
        return new RandomPassGenerator();
    }
}
