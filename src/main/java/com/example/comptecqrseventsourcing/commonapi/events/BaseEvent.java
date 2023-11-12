package com.example.comptecqrseventsourcing.commonapi.events;

import com.example.comptecqrseventsourcing.commonapi.commands.BaseCommand;
import lombok.Getter;

public abstract class BaseEvent<T> {

    @Getter private T id;

   public  BaseEvent(T id){
        this.id=id;
    }

}
