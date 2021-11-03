package com.example.superbank.service;

import java.util.List;

public interface PayloadService<REQ, RESP, ID> {

    RESP add(REQ requestDto);

    RESP get(ID entityId);

    List<RESP> getAll();

    boolean existsById(ID entityId);

    void delete(ID entityId);

}
