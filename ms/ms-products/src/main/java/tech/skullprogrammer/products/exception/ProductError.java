package tech.skullprogrammer.products.exception;

import io.netty.handler.codec.http.HttpResponseStatus;
import jakarta.annotation.Nonnull;
import lombok.RequiredArgsConstructor;
import tech.skullprogrammer.framework.core.exception.ISkullError;

@RequiredArgsConstructor
public enum ProductError implements ISkullError.App
{
    VALIDATION_ERROR(HttpResponseStatus.UNPROCESSABLE_ENTITY.code()),
    BAD_REQUEST(HttpResponseStatus.BAD_REQUEST.code()),
    PRODUCT_ALREADY_EXISTS(HttpResponseStatus.CONFLICT.code());

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
