package es.udc.stembach.backend.rest.dtos;

import es.udc.stembach.backend.model.entities.Request;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.temporal.ChronoUnit;

public class RequestConversor {

    private final static long toMillis(LocalDateTime date) {
        return date.truncatedTo(ChronoUnit.MINUTES).atZone(ZoneOffset.systemDefault()).toInstant().toEpochMilli();
    }

    public final static RequestDto toRequestDto(Request request){
        return new RequestDto(request.getId(), toMillis(request.getRequestDate()), request.getStatus(), request.getStudentGroup().getId(),
                request.getProject().getId(), request.getStudentGroup().getSchool().getName(), null);
    }
}
