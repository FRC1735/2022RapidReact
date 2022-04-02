// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.sensors;

import java.util.ArrayList;
import java.util.List;

/** Add your docs here. */
public class DistanceSensorGroup {
    List<DistanceSensor> sensors;

    double MIN_DETECTION_THRESHOLD_CM = 0.1;
    double MAX_DETECTION_THRESTHOLD_CM = 15;

    public DistanceSensorGroup(int frontSensorPort, int rearSensorPort) {
        sensors = new ArrayList<DistanceSensor>();
        sensors.add(new DistanceSensor(frontSensorPort));
        sensors.add(new DistanceSensor(rearSensorPort));
    }

    public List<Double> getDistances() {
        List<Double> distances = new ArrayList<>();

        for (int i = 0; i < sensors.size(); i++) {
            distances.add(sensors.get(i).getDistance());
        }

        return distances;
    }

    public boolean isBallDetected(int position) {
        if (position < 0 || position >= sensors.size()) {
            return false;
        }

        double distance = sensors.get(position).getDistance();
        boolean detected = (distance > MIN_DETECTION_THRESHOLD_CM && distance < MAX_DETECTION_THRESTHOLD_CM);

        return detected;
    }
}
