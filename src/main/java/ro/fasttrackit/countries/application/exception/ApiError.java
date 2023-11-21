package ro.fasttrackit.countries.application.exception;

import lombok.Builder;

@Builder
public record ApiError(
        String message,
        long entityId,
        String internalErrorCode
) {
}
