package com.canada.edu.stocktrading.factory;

import com.canada.edu.stocktrading.constant.ResponseStatusCodeEnum;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;


import java.io.Serializable;


@Component
public class ResponseFactory implements Serializable {
    private static final long serialVersionUID = 1L;

    public ResponseEntity<?>success(Object data) {
        GeneralResponse responseObject = new GeneralResponse();
        ResponseStatus status = new ResponseStatus();

        status.setCode(ResponseStatusCodeEnum.SUCCESS.getCode());
        status.setMessage(ResponseStatusCodeEnum.SUCCESS.getMessage());
        responseObject.setStatus(status);
        responseObject.setData(data);
        return ResponseEntity.ok(responseObject);
    }

    public ResponseEntity<?>created(Object data){
        GeneralResponse responseObject = new GeneralResponse();
        ResponseStatus status = new ResponseStatus();

        status.setCode(ResponseStatusCodeEnum.CREATED.getCode());
        status.setMessage(ResponseStatusCodeEnum.CREATED.getMessage());
        responseObject.setStatus(status);
        responseObject.setData(data);
        return new ResponseEntity<Object>(responseObject, HttpStatus.CREATED);
    }

    public ResponseEntity<?>updated() {
        GeneralResponse responseObject = new GeneralResponse();
        ResponseStatus status = new ResponseStatus();

        status.setCode(ResponseStatusCodeEnum.UPDATED.getCode());
        status.setMessage(ResponseStatusCodeEnum.UPDATED.getMessage());
        responseObject.setStatus(status);
        return new ResponseEntity<Object>(responseObject, HttpStatus.OK);
    }

    public ResponseEntity<?>deleted() {
        return new ResponseEntity(null, HttpStatus.NO_CONTENT);
    }

    public ResponseEntity<?>badRequest(String message) {
        GeneralResponse responseObject = new GeneralResponse();
        ResponseStatus status = new ResponseStatus();

        status.setCode(ResponseStatusCodeEnum.INVALID_PARAMETER.getCode());
        status.setMessage(message);
        responseObject.setStatus(status);
        return new ResponseEntity<Object>(responseObject, HttpStatus.BAD_REQUEST);
    }

    public ResponseEntity<?>internalServerError(String message) {
        GeneralResponse responseObject = new GeneralResponse();
        ResponseStatus status = new ResponseStatus();

        status.setCode(ResponseStatusCodeEnum.INTERNAL_SERVER_ERROR.getCode());
        status.setMessage(message);
        responseObject.setStatus(status);
        return new ResponseEntity<Object>(responseObject, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    public ResponseEntity<?>duplicateData(String message) {
        GeneralResponse responseObject = new GeneralResponse();
        ResponseStatus status = new ResponseStatus();

        status.setCode(ResponseStatusCodeEnum.DUPLICATE_RECORD.getCode());
        status.setMessage(message);
        responseObject.setStatus(status);
        return new ResponseEntity<Object>(responseObject, HttpStatus.CONFLICT);
    }


}