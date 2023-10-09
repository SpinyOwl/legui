package com.spinyowl.legui.system.event;

public record SystemWindowScaleEvent(
    long window, float scaleX, float scaleY, float previousScaleX, float previousScaleY)
    implements SystemEvent {}
