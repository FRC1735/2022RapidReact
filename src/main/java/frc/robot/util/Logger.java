// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.util;

public class Logger {
    private boolean enabled;

    public Logger(final boolean enabled) {
        this.enabled = enabled;
    }

    public void log(final String message) {
        if (enabled) {
            System.out.println(message);
        }
    }
}
