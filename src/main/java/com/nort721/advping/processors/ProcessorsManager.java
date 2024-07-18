package com.nort721.advping.processors;

import com.nort721.advping.data.Profile;
import com.nort721.advping.processors.processors.ConnectionProcessor;
import com.nort721.advping.processors.processors.TickProcessor;
import lombok.Getter;

import java.util.LinkedList;
import java.util.Queue;

@Getter
public class ProcessorsManager {

    private final Queue<Processor> processors = new LinkedList<>();

    public ProcessorsManager(Profile profile) {
        processors.add(new TickProcessor(profile));
        processors.add(new ConnectionProcessor(profile));
    }
}
