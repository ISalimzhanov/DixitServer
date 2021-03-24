package server

import com.fasterxml.jackson.databind.exc.MismatchedInputException
import com.fasterxml.jackson.module.kotlin.MissingKotlinParameterException
import org.springframework.http.converter.HttpMessageNotReadableException
import org.springframework.stereotype.Service
import server.api.jsonrpc.JsonRpcResponse
import server.api.jsonrpc.messages.response.InvalidJsonResponse
import server.api.jsonrpc.messages.response.InvalidParamsResponse
import server.api.jsonrpc.messages.response.InvalidRequestResponse

@Service
class ExceptionService {
    fun handleInvalidRequest(
        exception: HttpMessageNotReadableException,
    ): JsonRpcResponse {
        return when (val cause = exception.cause) {
            is MissingKotlinParameterException -> handleMissingParameter(cause)
            is MismatchedInputException -> handleMismatchedInput(cause)
            else -> InvalidJsonResponse()
        }
    }

    fun handleMismatchedInput(cause: MismatchedInputException): JsonRpcResponse {
        return when {
            cause.path.first().fieldName == "params" && cause.path.size > 1 -> InvalidParamsResponse()
            else -> InvalidRequestResponse()
        }
    }

    fun handleMissingParameter(cause: MissingKotlinParameterException): JsonRpcResponse {
        return when {
            cause.path.first().fieldName == "params" && cause.path.size > 1 -> InvalidParamsResponse()
            else -> InvalidRequestResponse()
        }
    }
}