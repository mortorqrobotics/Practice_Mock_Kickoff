package frc.BotchoCheese.Constants;

import edu.wpi.first.apriltag.AprilTagFieldLayout;
import edu.wpi.first.apriltag.AprilTagFields;

public class RobotMap {
    public static final double kPositionStdDevX = 0.1;
    public static final double kPositionStdDevY = 0.1;
    public static final double kPositionStdDevTheta = 10;

    public static final double kVisionStdDevX = 1;
    public static final double kVisionStdDevY = 1;
    public static final double kVisionStdDevTheta = 99999;

    public static final String LIMELIGHT_NAME = "limelight";

    public static final AprilTagFieldLayout ANDYMARK_FIELD2025 = AprilTagFieldLayout.loadField(AprilTagFields.k2025ReefscapeAndyMark);
    public static final AprilTagFieldLayout WELDED_FIELD2025 = AprilTagFieldLayout.loadField(AprilTagFields.k2025ReefscapeWelded);

    public static final int ELEVATOR_MOTOR = 20;
    public static final int ELEVATOR_BOTTOM_LIMIT_DIO = 0;

    public static final double ELEVATOR_UP_SPEED = 0.5;
    public static final double ELEVATOR_DOWN_SPEED = -0.3;
    public static final double ELEVATOR_MAX_ROTATIONS = 30.0;
    public static final double ELEVATOR_MIN_ROTATIONS = 0.0;
    public static final double ELEVATOR_RAMP_SECONDS = 0.25;
    public static final double ELEVATOR_PEAK_VOLTAGE = 10.0;
    public static final double ELEVATOR_STATOR_LIMIT = 60.0;
    public static final double ELEVATOR_SUPPLY_LIMIT = 40.0;
    public static final boolean ELEVATOR_BOTTOM_LIMIT_INVERTED = false;

    public static final int PIGEON_ID = 30;
}