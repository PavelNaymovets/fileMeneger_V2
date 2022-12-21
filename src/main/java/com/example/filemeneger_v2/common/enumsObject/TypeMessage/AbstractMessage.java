package com.example.filemeneger_v2.common.enumsObject.TypeMessage;

import com.example.filemeneger_v2.common.enumsObject.TypeMessage.TypeMessage;

import java.io.Serializable;

public interface AbstractMessage extends Serializable {
    TypeMessage getTypeMessage();
}
