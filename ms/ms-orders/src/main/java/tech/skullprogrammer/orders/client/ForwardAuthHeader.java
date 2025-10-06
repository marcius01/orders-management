package tech.skullprogrammer.orders.client;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.ws.rs.core.MultivaluedMap;
import lombok.extern.slf4j.Slf4j;
import org.eclipse.microprofile.rest.client.ext.ClientHeadersFactory;

@Slf4j
@ApplicationScoped
public class ForwardAuthHeader implements ClientHeadersFactory {
    @Override
    public MultivaluedMap<String, String> update(
            MultivaluedMap<String, String> incoming,
            MultivaluedMap<String, String> outgoing) {
        log.trace("Start - Forward auth header");
        var auth = incoming.getFirst("Authorization");
        if (auth != null) outgoing.putSingle("Authorization", auth);
        log.trace("End - Forward auth header");
        return outgoing;
    }
}