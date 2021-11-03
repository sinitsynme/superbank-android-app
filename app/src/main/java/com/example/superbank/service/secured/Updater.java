package com.example.superbank.service.secured;

public interface Updater <REQ, RESP, ID>{

    RESP update(REQ requestDto, ID entityId);

}
