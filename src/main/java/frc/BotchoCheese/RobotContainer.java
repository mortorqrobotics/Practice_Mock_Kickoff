
package frc.BotchoCheese;

import com.ctre.phoenix.led.CANdle;
import com.ctre.phoenix.led.CANdle.LEDStripType;
import com.ctre.phoenix.led.CANdleConfiguration;
import com.ctre.phoenix6.hardware.Pigeon2;
import com.ctre.phoenix6.swerve.SwerveModule.DriveRequestType;
import com.ctre.phoenix6.swerve.SwerveRequest;
import com.pathplanner.lib.auto.AutoBuilder;

import edu.wpi.first.math.VecBuilder;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.util.Units;
import static edu.wpi.first.units.Units.MetersPerSecond;
import static edu.wpi.first.units.Units.RadiansPerSecond;
import static edu.wpi.first.units.Units.RotationsPerSecond;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.button.CommandXboxController;
import frc.BotchoCheese.Constants.RobotMap;
import frc.BotchoCheese.Constants.TunerConstants;
import frc.BotchoCheese.Subsystems.CommandSwerveDrivetrain;
import frc.BotchoCheese.Subsystems.Elevator;

public class RobotContainer {
    public CANdle candle;
    public static double MaxSpeed = TunerConstants.kSpeedAt12Volts.in(MetersPerSecond);
    public static double MaxAngularRate = RotationsPerSecond.of(0.75).in(RadiansPerSecond);

    public static final SwerveRequest.FieldCentric drive = new SwerveRequest.FieldCentric()
            .withDeadband(MaxSpeed * 0.1)
            .withRotationalDeadband(MaxAngularRate * 0.1)
            .withDriveRequestType(DriveRequestType.OpenLoopVoltage);

    private final SwerveRequest.SwerveDriveBrake brake = new SwerveRequest.SwerveDriveBrake();
    private final SwerveRequest.PointWheelsAt point = new SwerveRequest.PointWheelsAt();
    private final SwerveRequest.RobotCentric forwardStraight = new SwerveRequest.RobotCentric()
            .withDriveRequestType(DriveRequestType.OpenLoopVoltage);

    private final Telemetry logger = new Telemetry(MaxSpeed);

    private final static CommandXboxController joystick = new CommandXboxController(0);
    private final static CommandXboxController joystickTwo = new CommandXboxController(1);

    public final static CommandSwerveDrivetrain drivetrain = createDrivetrain();
    public static Pigeon2 gyro = new Pigeon2(RobotMap.PIGEON_ID);

    public final Elevator elevator = new Elevator();
    public final Arm arm = new Arm();

    private final SendableChooser<Command> autoChooser;

    public RobotContainer() {
        autoChooser = AutoBuilder.buildAutoChooser("New Auto");
        SmartDashboard.putData("Auto Mode", autoChooser);

        candle = new CANdle(0);

        CANdleConfiguration config = new CANdleConfiguration();
        config.statusLedOffWhenActive = true;
        config.stripType = LEDStripType.GRB;
        config.v5Enabled = true;
        config.vBatOutputMode = CANdle.VBatOutputMode.Modulated;
        config.brightnessScalar = 1;
        candle.configAllSettings(config, 100);
        candle.configLEDType(LEDStripType.GRB);

        configureBindings();
    }

    private void configureBindings() {
        drivetrain.setDefaultCommand(
            drivetrain.applyRequest(() ->
                drive.withVelocityX(-joystick.getLeftY() * MaxSpeed * getRobotSpeed())
                    .withVelocityY(-joystick.getLeftX() * MaxSpeed * getRobotSpeed())
                    .withRotationalRate(-joystick.getRightX() * MaxAngularRate * getRobotYawSpeed())
            )
        );

        joystick.a().whileTrue(drivetrain.applyRequest(() -> brake));

        joystick.b().whileTrue(drivetrain.applyRequest(() ->
            point.withModuleDirection(new Rotation2d(-joystick.getLeftY(), -joystick.getLeftX()))
        ));

        joystick.pov(0).whileTrue(drivetrain.applyRequest(() ->
            forwardStraight.withVelocityX(0.5).withVelocityY(0))
        );

        joystick.pov(180).whileTrue(drivetrain.applyRequest(() ->
            forwardStraight.withVelocityX(-0.5).withVelocityY(0))
        );

        joystick.leftBumper().onTrue(drivetrain.runOnce(() -> drivetrain.seedFieldCentric()));

        joystick.y().whileTrue(elevator.moveUpCommand());
        joystick.b().whileTrue(elevator.moveDownCommand());

        drivetrain.registerTelemetry(logger::telemeterize);
    }

    private void ConfigureOperatorBindings() {
        joystickTwo.leftTrigger().whileTrue(arm.retract());
        joystickTwo.rightTrigger().whileTrue(arm.extend());
        joystickTwo.leftBumper().onTrue(arm.stop());
        joystickTwo.rightBumper().onTrue(arm.stop());
    }

    public static double getRobotSpeed() {
        return joystick.getLeftTriggerAxis() >= 0.25 ? 0.1 : 1.0;
    }

    public static double getRobotYawSpeed() {
        return joystick.getLeftTriggerAxis() >= 0.25 ? 0.1 : 0.7 * (1.0 / 0.9);
    }

    public Command getAutonomousCommand() {
        System.out.println(autoChooser.getSelected().getName());
        return autoChooser.getSelected();
    }

    public static CommandSwerveDrivetrain createDrivetrain() {
        return new CommandSwerveDrivetrain(
            TunerConstants.DrivetrainConstants, 0,
            VecBuilder.fill(
                RobotMap.kPositionStdDevX,
                RobotMap.kPositionStdDevY,
                Units.degreesToRadians(RobotMap.kPositionStdDevTheta)
            ),
            VecBuilder.fill(
                RobotMap.kVisionStdDevX,
                RobotMap.kVisionStdDevY,
                Units.degreesToRadians(RobotMap.kVisionStdDevTheta)
            ),
            TunerConstants.FrontLeft,
            TunerConstants.FrontRight,
            TunerConstants.BackLeft,
            TunerConstants.BackRight
        );
    }
}
