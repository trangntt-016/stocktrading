package com.canada.edu.stocktrading.factory;

import com.canada.edu.stocktrading.constant.ResponseStatusCodeEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;

import java.io.Serializable;

@Controller
public class ResponseFactory implements Serializable {
    private static final long serialVersionUID = 1L;

    private GeneralResponse responseObject;

    private ResponseStatus status;

    public ResponseFactory(){
        this.responseObject = new GeneralResponse();
        this.status = new ResponseStatus();
    }

    public ResponseEntity<?>success(Object data) {
        reset();
        this.status.setCode(ResponseStatusCodeEnum.SUCCESS.getCode());
        this.status.setMessage(ResponseStatusCodeEnum.SUCCESS.getMessage());
        this.responseObject.setStatus(this.status);
        this.responseObject.setData(data);
        return ResponseEntity.ok(this.responseObject);
    }

    public ResponseEntity<?>created(Object data){
        reset();
        this.status.setCode(ResponseStatusCodeEnum.CREATED.getCode());
        this.status.setMessage(ResponseStatusCodeEnum.CREATED.getMessage());
        this.responseObject.setStatus(this.status);
        this.responseObject.setData(data);
        return new ResponseEntity<Object>(this.responseObject, HttpStatus.CREATED);
    }

    public ResponseEntity<?>updated() {
        reset();
        this.status.setCode(ResponseStatusCodeEnum.UPDATED.getCode());
        this.status.setMessage(ResponseStatusCodeEnum.UPDATED.getMessage());
        this.responseObject.setStatus(this.status);
        return new ResponseEntity<Object>(this.responseObject, HttpStatus.OK);
    }

    public ResponseEntity<?>deleted() {
        reset();
        return new ResponseEntity(this.responseObject, HttpStatus.NO_CONTENT);
    }

    public ResponseEntity<?>badRequest(String message) {
        reset();
        this.status.setCode(ResponseStatusCodeEnum.INVALID_PARAMETER.getCode());
        this.status.setMessage(message);
        this.responseObject.setStatus(this.status);
        return new ResponseEntity<Object>(this.responseObject, HttpStatus.BAD_REQUEST);
    }

    public ResponseEntity<?>internalServerError(String message) {
        reset();
        this.status.setCode(ResponseStatusCodeEnum.INTERNAL_SERVER_ERROR.getCode());
        this.status.setMessage(message);
        this.responseObject.setStatus(this.status);
        return new ResponseEntity<Object>(this.responseObject, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    public ResponseEntity<?>duplicateData(String message) {
        reset();
        this.status.setCode(ResponseStatusCodeEnum.DUPLICATE_RECORD.getCode());
        this.status.setMessage(message);
        this.responseObject.setStatus(this.status);
        return new ResponseEntity<Object>(this.responseObject, HttpStatus.CONFLICT);
    }

    private void reset(){
        this.responseObject.setData(null);
        this.status.setCode(null);
        this.status.setMessage(null);
    }

}