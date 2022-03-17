// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.util;

import java.util.logging.Logger;

public class Log {
    private boolean enabled;
    private Logger logger;

    public Log(final boolean enabled) {
        this.enabled = enabled;
        this.logger = Logger.getLogger("frc-1735");
    }

    public void log(final String message) {
        if (enabled) {
            System.out.println(message);
            
        }
    }
}
