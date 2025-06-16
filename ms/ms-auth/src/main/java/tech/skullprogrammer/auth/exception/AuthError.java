package tech.skullprogrammer.auth.exception;

import io.netty.handler.codec.http.HttpResponseStatus;
import jakarta.annotation.Nonnull;
import lombok.RequiredArgsConstructor;
import tech.skullprogrammer.framework.core.exception.ISkullError;

@RequiredArgsConstructor
public enum AuthError implements ISkullError.App
{
    VALIDATION_ERROR(HttpResponseStatus.UNPROCESSABLE_ENTITY.code()),
    BAD_REQUEST(HttpResponseStatus.BAD_REQUEST.code()),
    EMAIL_ALREADY_EXISTS(HttpResponseStatus.CONFLICT.code()),
    UNAUTHORIZED(HttpResponseStatus.UNAUTHORIZED.code());

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
