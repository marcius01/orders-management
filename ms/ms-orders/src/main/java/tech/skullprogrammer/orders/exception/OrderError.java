package tech.skullprogrammer.orders.exception;

import io.netty.handler.codec.http.HttpResponseStatus;
import jakarta.annotation.Nonnull;
import lombok.RequiredArgsConstructor;
import tech.skullprogrammer.framework.core.exception.ISkullError;

@RequiredArgsConstructor
public enum OrderError implements ISkullError.App
{
    VALIDATION_ERROR(HttpResponseStatus.UNPROCESSABLE_ENTITY.code()),
    BAD_REQUEST(HttpResponseStatus.BAD_REQUEST.code()),
    UNAUTHORIZED(HttpResponseStatus.UNAUTHORIZED.code()),
    ORDER_NOT_FOUND(HttpResponseStatus.NOT_FOUND.code()),
    ORDER_ALREADY_DELETED(HttpResponseStatus.GONE.code()),
    PRODUCT_NOT_FOUND(HttpResponseStatus.NOT_FOUND.code());

    private final int status;

    @Override
    public int getStatus() {
        return status;
    }

    @Nonnull
    @Override
    public String getCode() {
        return name();
    }

}
