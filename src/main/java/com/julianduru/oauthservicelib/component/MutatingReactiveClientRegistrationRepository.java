package com.julianduru.oauthservicelib.component;

import org.springframework.security.oauth2.client.registration.ClientRegistration;
import org.springframework.security.oauth2.client.registration.ReactiveClientRegistrationRepository;
import org.springframework.util.Assert;
import reactor.core.publisher.Mono;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

/**
 * created by julian on 12/09/2022
 */
public class MutatingReactiveClientRegistrationRepository
    implements ReactiveClientRegistrationRepository, Iterable<ClientRegistration> {


    private final Map<String, ClientRegistration> clientIdToClientRegistration;


    public MutatingReactiveClientRegistrationRepository(ClientRegistration... registrations) {
        this(toList(registrations));
    }

    private static List<ClientRegistration> toList(ClientRegistration... registrations) {
        Assert.notEmpty(registrations, "registrations cannot be null or empty");
        return Arrays.asList(registrations);
    }

    public MutatingReactiveClientRegistrationRepository(List<ClientRegistration> registrations) {
        this.clientIdToClientRegistration = toConcurrentMap(registrations);
    }

    @Override
    public Mono<ClientRegistration> findByRegistrationId(String registrationId) {
        return Mono.justOrEmpty(this.clientIdToClientRegistration.get(registrationId));
    }


    @Override
    public Iterator<ClientRegistration> iterator() {
        return this.clientIdToClientRegistration.values().iterator();
    }

    private static Map<String, ClientRegistration> toConcurrentMap(List<ClientRegistration> registrations) {
        Assert.notEmpty(registrations, "registrations cannot be null or empty");
        ConcurrentHashMap<String, ClientRegistration> result = new ConcurrentHashMap<>();

        for (ClientRegistration registration : registrations) {
            Assert.notNull(registration, "no registration can be null");
            if (result.containsKey(registration.getRegistrationId())) {
                throw new IllegalStateException(String.format("Duplicate key %s", registration.getRegistrationId()));
            }
            result.put(registration.getRegistrationId(), registration);
        }

        return result;
    }


    public void addClientRegistration(ClientRegistration clientRegistration) {
        clientIdToClientRegistration.put(clientRegistration.getRegistrationId(), clientRegistration);
    }


}
