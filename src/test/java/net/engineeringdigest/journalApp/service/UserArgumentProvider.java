package net.engineeringdigest.journalApp.service;

import net.engineeringdigest.journalApp.entity.User;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.ArgumentsProvider;

import java.util.stream.Stream;

public class UserArgumentProvider implements ArgumentsProvider {

    @Override
    public Stream<? extends Arguments> provideArguments(ExtensionContext context) throws Exception {
        return Stream.of(
                Arguments.of(
                        User.builder()
                                .username("Ishita")
                                .password("ishita@123")
                                .build()
                ),

                Arguments.of(
                        User.builder()
                                .username("Sachin")
                                .password("sachin@123")
                                .build()
                ),

                Arguments.of(
                        User.builder()
                                .username("Nitish")
                                .password("nitish@123")
                                .build()
                ),

                Arguments.of(
                        User.builder()
                                .username("Nikita")
                                .password("nikita@123")
                                .build()
                )
        );
    }
}
