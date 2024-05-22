package com.cloud.bank.clients.configurations;

import com.cloud.bank.clients.model.ClientEntity;
import org.reactivestreams.Publisher;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.r2dbc.mapping.OutboundRow;
import org.springframework.data.r2dbc.mapping.event.BeforeConvertCallback;
import org.springframework.data.r2dbc.mapping.event.BeforeSaveCallback;
import org.springframework.data.relational.core.sql.SqlIdentifier;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;

@Configuration
public class R2dbcConfig implements BeforeSaveCallback<ClientEntity>, BeforeConvertCallback<ClientEntity> {

    public Publisher<ClientEntity> onBeforeConvert(ClientEntity entity, SqlIdentifier table) {
        if (entity.getCreated() == null) {
            entity.setCreated(LocalDateTime.now());
        }
        entity.setLastUpdate(LocalDateTime.now());
        return Mono.just(entity);
    }

    @Override
    public Publisher<ClientEntity> onBeforeSave(ClientEntity entity, OutboundRow row, SqlIdentifier table) {
        if (entity.getCreated() == null) {
            entity.setCreated(LocalDateTime.now());
        }
        entity.setLastUpdate(LocalDateTime.now());
        return Mono.just(entity);
    }
}
